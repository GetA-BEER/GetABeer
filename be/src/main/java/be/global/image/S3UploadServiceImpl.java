package be.global.image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;

import be.global.exception.BusinessLogicException;
import be.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3UploadServiceImpl implements S3UploadService {

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	@Value("${cloud.aws.s3.dir}")
	private String dir;

	private final AmazonS3Client s3Client;

	private static final long limit = 1024 * 1024 * 3;

	@Override
	public String upload(MultipartFile multipartFile) throws IOException {

		String originalName = multipartFile.getOriginalFilename();
		if (originalName == null || originalName.equals(" ") || originalName.equals("")) {
			throw new BusinessLogicException(ExceptionCode.CHECK_IMAGE_NAME);
		}

		String ext = originalName.substring(originalName.lastIndexOf(".")); // 확장자

		if (!ext.equalsIgnoreCase(".jpg") && !ext.equalsIgnoreCase(".png")
			&& !ext.equalsIgnoreCase("jpeg")) {
			throw new BusinessLogicException(ExceptionCode.NOT_IMAGE_EXTENSION);
		}

		/* 이름 수정 */
		originalName = new String(originalName.replaceAll(" ", "").getBytes(StandardCharsets.UTF_8));
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-SSS");
		String s3FileName = dir + "/" + LocalDateTime.now().format(format) + "-" + originalName;

		if (multipartFile.getSize() > limit) {
			throw new BusinessLogicException(ExceptionCode.TOO_BIG_SIZE);

			// /* 이미지 리사이징*/
			// multipartFile = resizing(multipartFile, originalName, ext);
		}

		ObjectMetadata objMeta = new ObjectMetadata();
		objMeta.setContentLength(multipartFile.getInputStream().available());

		s3Client.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);

		return s3Client.getUrl(bucket, s3FileName).toString();
	}

	private static MultipartFile resizing(MultipartFile multipartFile, String originalName, String ext)
		throws IOException {

		/* BufferedImage 로 변환되지 않고 imputImager 가 NPE 발생 (81번째 줄에서 길이를 가져올 수 없음 )*/
		BufferedImage inputImage = ImageIO.read(multipartFile.getInputStream());
		int originWidth = inputImage.getWidth();
		int originHeight = inputImage.getHeight();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		if (originWidth > 1280) {
			int newHeight = (originHeight * 1280) / originWidth;

			BufferedImage newImage = new BufferedImage(1280, newHeight, BufferedImage.TYPE_INT_RGB);

			Graphics2D graphics = newImage.createGraphics();
			graphics.drawImage(inputImage, 0, 0, null);
			graphics.dispose();

			ImageIO.write(newImage, ext, baos);
			baos.flush();
		} else if (originHeight > 720) {
			int newWidth = (originWidth * 720) / originHeight;

			BufferedImage newImage = new BufferedImage(newWidth, 720, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics = newImage.createGraphics();
			graphics.drawImage(inputImage, 0, 0, null);
			graphics.dispose();

			ImageIO.write(newImage, ext, baos);
			baos.flush();
		}

		return new MockMultipartFile(originalName, baos.toByteArray());
	}
}
