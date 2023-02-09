package be.global.image;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;

import be.domain.beer.entity.Beer;
import be.domain.pairing.entity.Pairing;
import be.domain.pairing.entity.PairingImage;
import be.domain.pairing.repository.PairingImageRepository;
import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;

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
	public String createUserImage(MultipartFile file) throws IOException {
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
			}}

		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-SSS");
		String fileName = "profileImage/" + LocalDateTime.now().format(format)+ "/" + originalName + ext;

		ObjectMetadata objMeta = new ObjectMetadata();
		objMeta.setContentLength(file.getInputStream().available());
		objMeta.setContentType(contentType);

		s3Client.putObject(bucket, fileName, file.getInputStream(), objMeta);

		return s3Client.getUrl(bucket, fileName).toString();
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
}