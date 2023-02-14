package be.domain.search.service;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.ImageContext;
import com.google.cloud.vision.v1.ImageSource;
import com.google.cloud.vision.v1.ProductName;
import com.google.cloud.vision.v1.ProductSearchClient;
import com.google.cloud.vision.v1.ProductSearchParams;
import com.google.cloud.vision.v1.ProductSearchResults.Result;
import com.google.cloud.vision.v1.ProductSetName;
import com.google.protobuf.ByteString;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class VisionService {

	public static void getSimilarProductsFile(
		String projectId,
		String computeRegion,
		String productSetId,
		String productCategory,
		String filePath,
		String filter)

		throws IOException {

		try (ImageAnnotatorClient queryImageClient = ImageAnnotatorClient.create()) {

			// Get the full path of the product set.
			String productSetPath = ProductSetName.format(projectId, computeRegion, productSetId);

			// Read the image as a stream of bytes.
			File imgPath = new File(filePath);
			byte[] content = Files.readAllBytes(imgPath.toPath());

			// Create annotate image request along with product search feature.
			Feature featuresElement = Feature.newBuilder().setType(Type.PRODUCT_SEARCH).build();
			// The input image can be a HTTPS link or Raw image bytes.
			// Example:
			// To use HTTP link replace with below code
			//  ImageSource source = ImageSource.newBuilder().setImageUri(imageUri).build();
			//  Image image = Image.newBuilder().setSource(source).build();
			Image image = Image.newBuilder().setContent(ByteString.copyFrom(content)).build();
			ImageContext imageContext =
				ImageContext.newBuilder()
					.setProductSearchParams(
						ProductSearchParams.newBuilder()
							.setProductSet(productSetPath)
							.addProductCategories(productCategory)
							.setFilter(filter))
					.build();

			AnnotateImageRequest annotateImageRequest =
				AnnotateImageRequest.newBuilder()
					.addFeatures(featuresElement)
					.setImage(image)
					.setImageContext(imageContext)
					.build();
			List<AnnotateImageRequest> requests = Arrays.asList(annotateImageRequest);

			// Search products similar to the image.
			BatchAnnotateImagesResponse response = queryImageClient.batchAnnotateImages(requests);

			List<Result> similarProducts =
				response.getResponses(0).getProductSearchResults().getResultsList();
			System.out.println("Similar Products: ");
			for (Result product : similarProducts) {
				System.out.println(String.format("\nProduct name: %s", product.getProduct().getName()));
				System.out.println(
					String.format("Product display name: %s", product.getProduct().getDisplayName()));
				System.out.println(
					String.format("Product description: %s", product.getProduct().getDescription()));
				System.out.println(String.format("Score(Confidence): %s", product.getScore()));
				System.out.println(String.format("Image name: %s", product.getImage()));
			}
		}
	}

}
