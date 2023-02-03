package be.global.image;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImageUploadController {
	private final S3UploadService s3UploadService;

	public ImageUploadController(S3UploadService s3UploadService) {
		this.s3UploadService = s3UploadService;
	}

	@PostMapping("/upload")
	@ResponseBody
	public String upload(@RequestParam("image") MultipartFile multipartFile) throws IOException {

		return s3UploadService.upload(multipartFile);
	}
}
