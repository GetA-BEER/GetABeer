package be.global.image;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;

import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class S3UploadServiceImpl implements S3UploadService {

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	@Value("${cloud.aws.s3.dir}")
	private String dir;

	private final AmazonS3Client s3Client;

	private static final int CAPACITY_LIMIT_BYTE = 1024 * 1024 * 5;

	@Override
	public String upload(MultipartFile multipartFile) throws IOException {
		String originalName = multipartFile.getOriginalFilename();
		if (originalName == null || originalName.equals(" ") || originalName.equals("")) {
			throw new BusinessLogicException(ExceptionCode.CHECK_IMAGE_NAME);
		}

		String ext = originalName.substring(originalName.lastIndexOf(".")); // 확장자
		if (!ext.equals(".jpg") && !ext.equals(".png") && !ext.equals("jpeg")) {
			throw new BusinessLogicException(ExceptionCode.NOT_IMAGE_EXTENSION);
		}

		/* 이름 수정 */
		originalName = new String(originalName.replaceAll(" ", "").getBytes(StandardCharsets.UTF_8));
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-SSS");
		String s3FileName = dir + "/" + LocalDateTime.now().format(format) + "-" + originalName;

		ObjectMetadata objMeta = new ObjectMetadata();
		objMeta.setContentLength(multipartFile.getInputStream().available());

		s3Client.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);

		return s3Client.getUrl(bucket, s3FileName).toString();
	}
}
