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
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import be.domain.beer.entity.Beer;
import be.domain.pairing.entity.Pairing;
import be.domain.pairing.entity.PairingImage;
import be.domain.pairing.repository.PairingImageRepository;
import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PairingImageHandler implements ImageHandler {
	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	@Value("${cloud.aws.s3.dir}")
	private String dir;

	private final AmazonS3Client s3Client;
	private final PairingImageRepository pairingImageRepository;

	private static final long limit = 1024 * 1024 * 4;

	@Override
	public HashMap createProfileImage(MultipartFile file, String folderSrc) throws IOException {
		if (file.isEmpty()) {
			return null;
		}

		if (file.getSize() > limit) {
			throw new BusinessLogicException(ExceptionCode.TOO_BIG_SIZE);
		}

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

		// ObjectMetadata objMeta = new ObjectMetadata();
		// objMeta.setContentLength(file.getInputStream().available());
		// objMeta.setContentType(contentType);
		//
		// s3Client.putObject(bucket, fileName, file.getInputStream(), objMeta);

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

		// return s3Client.getUrl(bucket, fileName).toString();
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

			if (image.getSize() > limit) {
				throw new BusinessLogicException(ExceptionCode.TOO_BIG_SIZE);
			}

			String originalName = Arrays.toString(Objects.requireNonNull(image.getOriginalFilename())
				.replaceAll(" ", "")
				.getBytes(StandardCharsets.UTF_8));
			if (originalName.equals("")) {
				throw new BusinessLogicException(ExceptionCode.CHECK_IMAGE_NAME);
			}

			String ext;
			String contentType = image.getContentType();
			if (ObjectUtils.isEmpty(contentType)) {
				// throw new BusinessLogicException(ExceptionCode.NOT_IMAGE_EXTENSION);

				/* 빈 파일이어도 1인 사이즈의 리스트가 들어오고 있기 때문에 예외 대신 result를 리턴, 만약에 이유를 찾으면 수정 예정 */
				return result;
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
				}}

				/* 이름 수정 */
				String fileName = "pairing_images_" + beer.getId() + "_" + userId;
				DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-SSS");
				String s3FileName = dir + "/" + LocalDateTime.now().format(format) + "/" + fileName + ext;

				ObjectMetadata objMeta = new ObjectMetadata();
				objMeta.setContentLength(image.getInputStream().available());
				objMeta.setContentType(contentType);

				s3Client.putObject(bucket, s3FileName, image.getInputStream(), objMeta);

				String url = s3Client.getUrl(bucket, s3FileName).toString();

				PairingImage pairingImage = PairingImage.builder()
					.imageUrl(url)
					.fileName(s3FileName)
					.pairing(pairing)
					.build();
				result.add(pairingImage);
			}
		return result;
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
