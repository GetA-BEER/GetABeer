package be.domain.search.service;

import static be.domain.gcp.Constant.GcpConstant.*;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import be.domain.beer.entity.Beer;
import be.domain.beer.repository.BeerQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class VisionService {
	private final BeerQueryRepository beerQueryRepository;

	public List<String> getSimilarProductsFile(MultipartFile multipartFile) throws IOException {

		try (ImageAnnotatorClient queryImageClient = ImageAnnotatorClient.create()) {

			// Get the full path of the product set.
			String productSetPath = ProductSetName.format(PROJECT_ID, COMPUTE_REGION, PRODUCT_SET_ID);
			// ===========================================================
			byte[] content = multipartFile.getBytes();
			// ===========================================================

			Feature featuresElement = Feature.newBuilder().setType(Type.PRODUCT_SEARCH).build();

			Image image = Image.newBuilder().setContent(ByteString.copyFrom(content)).build();
			ImageContext imageContext =
				ImageContext.newBuilder()
					.setProductSearchParams(
						ProductSearchParams.newBuilder()
							.setProductSet(productSetPath)
							.addProductCategories(PRODUCT_CATEGORY)
							// .setFilter(filter))
							.setFilter(" "))
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

			List<String> engNameList = new ArrayList<>();

			System.out.println("Similar Products: ");
			for (Result product : similarProducts) {
				engNameList.add(product.getProduct().getName()
					.substring(product.getProduct().getName().lastIndexOf('/') + 1));
			}
			return engNameList;
		}
	}

	public List<Beer> findBeersListByImage(List<String> engNameList) {
		return beerQueryRepository.findBeersListByImage(engNameList);
	}

}
