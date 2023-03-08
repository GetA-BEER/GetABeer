package be.global.image;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import be.domain.beer.entity.Beer;
import be.domain.pairing.entity.Pairing;
import be.domain.pairing.entity.PairingImage;
import be.domain.pairing.repository.image.PairingImageRepository;
import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImageHandlerImpl implements ImageHandler {
	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	@Value("${cloud.aws.s3.dir}")
	private String dir;

	private final AmazonS3Client s3Client;
	private final PairingImageRepository pairingImageRepository;

	// private static final long limit = 1024 * 1024 * 4;

	@Override
	public HashMap createProfileImage(MultipartFile file, String folderSrc) throws IOException {
		if (file.isEmpty()) {
			return null;
		}

		// if (file.getSize() > limit) {
		// 	throw new BusinessLogicException(ExceptionCode.TOO_BIG_SIZE);
		// }

		String originalName = Arrays.toString(Objects.requireNonNull(file.getOriginalFilename())
			.substring(0, file.getOriginalFilename().lastIndexOf("."))
			.replaceAll(" ", "")
			.getBytes(StandardCharsets.UTF_8));

		String ext;
		String contentType = file.getContentType();
		if (ObjectUtils.isEmpty(contentType)) {
			throw new BusinessLogicException(ExceptionCode.NOT_IMAGE_EXTENSION);
		} else {
			if (contentType.contains("image/jpeg")) {
				ext = ".jpg";
			} else if (contentType.contains("image/jpg")) {
				ext = ".jpg";
			} else if (contentType.contains("image/png")) {
				ext = ".png";
			} else if (contentType.contains("image/heic")) {
				ext = ".heic";
			} else {
				throw new BusinessLogicException(ExceptionCode.NOT_IMAGE_EXTENSION);
			}
		}

		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-SSS");
		String fileName = "profileImage/" + LocalDateTime.now().format(format) + ext;
		String fileKey = UUID.randomUUID().toString().substring(0, 15) + "_" + fileName;

		try {
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType(file.getContentType());

			PutObjectRequest request = new PutObjectRequest(
				bucket + folderSrc,
				fileKey,
				file.getInputStream(),
				metadata);
			s3Client.putObject(request); // Load

			URL url = s3Client.getUrl(bucket + folderSrc, fileKey);
			HashMap map = new HashMap<>();
			map.put("url", url);
			map.put("fileKey", fileKey);

			return map;
		} catch (AmazonServiceException e) {
			log.error("Upload To S3 AmazonServiceException filePath={}, yyyymm={}, error={}", e.getMessage());
		} catch (SdkClientException e) {
			log.error("Upload To S3 SdkClientException filePath={}, error={}", e.getMessage());
		} catch (Exception e) {
			log.error("Upload To S3 SdkClientException filePath={}, error={}", e.getMessage());
		}

		return new HashMap<>();
	}

	@Override
	public List<PairingImage> createPairingImage(Pairing pairing, List<MultipartFile> files,
		Long userId, Beer beer) throws IOException {

		List<PairingImage> result = new ArrayList<>();
		if (files.isEmpty()) {
			return result;
		}

		for (int i = 0; i < files.size(); i++) {
			MultipartFile image = files.get(i);
			PairingImage pairingImage = savePairingImage(pairing, image);

			if (pairingImage != null) {
				pairingImage.setImagesOrder(i + 1);
				pairingImageRepository.save(pairingImage);
				result.add(pairingImage);
			}
		}
		return result;
	}

	@Override
	public List<PairingImage> updatePairingImage(Pairing pairing, List<String> type, List<Long> url,
		List<MultipartFile> files) throws IOException {
		log.info("#### 페어링 입력 확인 : " + pairing.getId());
		log.info("#### 페어링 겟 리스트 : " + pairing.getPairingImageList());
		List<PairingImage> originImage = pairing.getPairingImageList();
		List<PairingImage> result = new ArrayList<>();

		boolean[] visited = new boolean[originImage.size()];
		ArrayList<Long> originImageId = new ArrayList<>(originImage.size());

		for (PairingImage image : originImage) {
			originImageId.add(image.getId());
		}

		int urlIdx = 0;
		int fileIdx = 0;
		for (int i = 0; i < type.size(); i++) {
			String typeName = type.get(i);

			if (typeName.equals("url")) {
				PairingImage pairingImage = findPairingImage(url.get(urlIdx));

				int idx = originImageId.indexOf(pairingImage.getId());
				visited[idx] = true;

				result.add(pairingImage);

				pairingImage.setImagesOrder(i + 1);
				pairingImageRepository.save(pairingImage);

				urlIdx++;
			} else if (typeName.equals("file")) {
				PairingImage pairingImage = savePairingImage(pairing, files.get(fileIdx));

				if (pairingImage != null) {
					pairingImage.setImagesOrder(i + 1);
					pairingImageRepository.save(pairingImage);
					result.add(pairingImage);
				}

				fileIdx++;
			} else {
				throw new RuntimeException("요청을 확인하십시오.");
			}
		}

		for (int i = 0; i < originImageId.size(); i++) {
			if (!visited[i]) {
				PairingImage pairingImage = findPairingImage(originImageId.get(i));
				deletePairingImage(pairingImage.getFileName());
				pairingImageRepository.delete(pairingImage);
			}
		}

		return result;
	}

	private PairingImage savePairingImage(Pairing pairing, MultipartFile file) throws IOException {

		// if (file.getSize() > limit) {
		// 	throw new BusinessLogicException(ExceptionCode.TOO_BIG_SIZE);
		// }

		String ext;
		String contentType = file.getContentType();
		if (ObjectUtils.isEmpty(contentType)) {
			return null;
		} else {
			if (contentType.contains("image/jpeg")) {
				ext = ".jpg";
			} else if (contentType.contains("image/jpg")) {
				ext = ".jpg";
			} else if (contentType.contains("image/png")) {
				ext = ".png";
			} else if (contentType.contains("image/heic")) {
				ext = ".heic";
			} else {
				throw new BusinessLogicException(ExceptionCode.NOT_IMAGE_EXTENSION);
			}
		}

		/* 이름 수정 */
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-SSS");
		String fileName = dir + "/" + LocalDateTime.now().format(format) + "_pairing_images" + ext;

		ObjectMetadata objMeta = new ObjectMetadata();
		objMeta.setContentLength(file.getInputStream().available());
		objMeta.setContentType(contentType);

		s3Client.putObject(bucket, fileName, file.getInputStream(), objMeta);

		String s3Url = s3Client.getUrl(bucket, fileName).toString();

		return PairingImage.builder()
			.imageUrl(s3Url)
			.fileName(fileName)
			.pairing(pairing)
			.build();
	}

	private void deletePairingImage(String fileName) {
		try {
			s3Client.deleteObject(bucket, fileName);
		} catch (AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
		}
	}

	private PairingImage findPairingImage(Long pairingImageId) {

		return pairingImageRepository.findById(pairingImageId)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 이미지"));
	}

	@Override
	public void deleteProfileImage(String fileKey, String folderSrc) {
		s3Client.deleteObject(bucket + folderSrc, fileKey);
	}

	@Override
	public HashMap updateProfileImage(MultipartFile file, String folderSrc, String oldFileKey) throws IOException {
		deleteProfileImage(oldFileKey, folderSrc);
		return createProfileImage(file, folderSrc);
	}
}
