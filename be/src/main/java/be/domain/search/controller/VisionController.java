package be.domain.search.controller;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.spring.vision.CloudVisionTemplate;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class VisionController {

	private final ResourceLoader resourceLoader;
	private final CloudVisionTemplate cloudVisionTemplate;
	private final ImageAnnotatorClient imageAnnotatorClient;

	@GetMapping("/test")
	public String extractText(String imageUrl) {
		String textFromImage =
			this.cloudVisionTemplate.extractTextFromImage(this.resourceLoader.getResource(imageUrl));
		return "Text from image: " + textFromImage;
	}

	@GetMapping("/test2")
	public String extractText2(@RequestParam(value = "image") MultipartFile multipartFile) throws Exception {

		byte[] imageBytes = StreamUtils.copyToByteArray(multipartFile.getInputStream());
		Image image = Image.newBuilder().setContent(ByteString.copyFrom(imageBytes)).build();

		Feature feature = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
		AnnotateImageRequest request = AnnotateImageRequest.newBuilder().setImage(image).addFeatures(feature).build();
		BatchAnnotateImagesResponse responses = this.imageAnnotatorClient
			.batchAnnotateImages(Collections.singletonList(request));

		AnnotateImageResponse response = responses.getResponses(0);
		StringBuilder responseBuilder = new StringBuilder();

		response.getTextAnnotationsList().stream()
			.map(EntityAnnotation::getDescription)
			.map(s -> s.split("\n"))
			.flatMap(Arrays::stream)
			.distinct()
			.filter(s -> s.length() < 30)
			.forEach(str -> responseBuilder.append(str).append("\n"));

		return responseBuilder.toString();
	}

}
