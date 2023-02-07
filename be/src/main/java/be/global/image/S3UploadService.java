package be.global.image;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface S3UploadService {
	String upload(MultipartFile multipartFile) throws IOException;
}
