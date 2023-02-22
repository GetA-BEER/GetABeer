package be.domain.search.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

import be.domain.beer.dto.BeerDto;
import be.domain.beer.entity.Beer;
import be.domain.beer.mapper.BeerMapper;
import be.domain.search.service.VisionService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class VisionController {
	private final BeerMapper beerMapper;
	private final VisionService visionService;
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

	@GetMapping("/image")
	public ResponseEntity imageSearch(@RequestParam(value = "image") MultipartFile multipartFile) throws IOException {
		List<String> engNameList = visionService.getSimilarProductsFile(multipartFile);
		List<Beer> beerList = visionService.findBeersListByImage(engNameList);
		List<BeerDto.SearchResponse> responseList = beerMapper.beersListToSearchResponse(beerList);

		return ResponseEntity.ok(responseList);
	}
}
