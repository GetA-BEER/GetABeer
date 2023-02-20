package be.domain.beer.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
public class TempController {
	@Value("${cloud.aws.s3.bucket}")
	private String bucket;
	@Value("${cloud.aws.s3.dir}")
	private String dir;
	private final AmazonS3Client amazonS3Client;

	@PostMapping("/product")
	public String createProduct(@RequestParam(value = "image") MultipartFile multipartFile) throws IOException {

		if (multipartFile.isEmpty()) {
			return null;
		}

		String originalImageName = multipartFile.getOriginalFilename(); // 원래 파일 이름

		String uuid = UUID.randomUUID().toString(); // 파일 이름으로 사용할 UUID 생성

		String extension = multipartFile.getContentType()
			.substring(multipartFile.getContentType().lastIndexOf("/") + 1); // 확장자 추출

		String[] extensions = {"png"};

		if (!Arrays.asList(extensions).contains(extension)) {
			throw new IllegalArgumentException("지원하지 않는 포맷입니다.");
		}

		String storedImageName = uuid + '.' + extension; // 파일 이름 + 확장자

		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentLength(multipartFile.getSize());
		objectMetadata.setContentType(multipartFile.getContentType());

		InputStream inputStream = multipartFile.getInputStream();

		amazonS3Client.putObject(
			new PutObjectRequest(bucket, "pairingImages/" + storedImageName, inputStream, objectMetadata)
				.withCannedAcl(CannedAccessControlList.PublicRead));

		return amazonS3Client.getUrl(bucket, "pairingImages/" + storedImageName).toString();
	}

}
