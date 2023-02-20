package be.domain.gcp.Service;

import static be.domain.gcp.Constant.GcpConstant.*;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.google.cloud.vision.v1.CreateProductSetRequest;
import com.google.cloud.vision.v1.LocationName;
import com.google.cloud.vision.v1.Product;
import com.google.cloud.vision.v1.ProductName;
import com.google.cloud.vision.v1.ProductSearchClient;
import com.google.cloud.vision.v1.ProductSet;
import com.google.cloud.vision.v1.ProductSetName;
import com.google.cloud.vision.v1.ReferenceImage;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	public void createProductSet() throws IOException {

		try (ProductSearchClient client = ProductSearchClient.create()) {

			// A resource that represents Google Cloud Platform location.
			String formattedParent = LocationName.format(PROJECT_ID, COMPUTE_REGION);

			// Create a product set with the product set specification in the region.
			ProductSet myProductSet =
				ProductSet.newBuilder().setDisplayName(PRODUCT_SET_DISPLAY_NAME).build();

			CreateProductSetRequest request =
				CreateProductSetRequest.newBuilder()
					.setParent(formattedParent)
					.setProductSet(myProductSet)
					.setProductSetId(PRODUCT_SET_ID)
					.build();

			ProductSet productSet = client.createProductSet(request);
			// Display the product set information
			System.out.println(String.format("Product set name: %s", productSet.getName()));
		}
	}

	public void createAndInitProduct() throws IOException {

		for (int i = 0; i < 179; i++) {

			/*
			 * 제품 생성
			 */

			try (ProductSearchClient client = ProductSearchClient.create()) {

				String formattedParent = LocationName.format(PROJECT_ID, COMPUTE_REGION);

				Product myProduct =
					Product.newBuilder()
						.setName(PRODUCT_ENGNAME[i])
						.setDisplayName(PRODUCT_KORNAME[i])
						.setProductCategory(PRODUCT_CATEGORY)
						.build();
				Product product = client.createProduct(formattedParent, myProduct, PRODUCT_ENGNAME[i]);

				System.out.println(String.format("Product name: %s", product.getName()));

				/*
				 * 세트에 추가
				 */
				String formattedName = ProductSetName.format(PROJECT_ID, COMPUTE_REGION, PRODUCT_SET_ID);

				String productPath = ProductName.of(PROJECT_ID, COMPUTE_REGION, PRODUCT_ENGNAME[i]).toString();

				client.addProductToProductSet(formattedName, productPath);

				System.out.println(String.format("Product added to product set."));

				/*
				 * 참조 이미지 연결 및 색인
				 */
				String formattedParentForImage = ProductName.format(PROJECT_ID, COMPUTE_REGION, PRODUCT_ENGNAME[i]);

				ReferenceImage referenceImage = ReferenceImage.newBuilder()
					.setUri("gs://getabeer_bucket/GetABeer_Products/" + (i + 1) + ".jpeg")
					.build();

				String referenceImageId = (i + 1) + UUID.randomUUID().toString();

				ReferenceImage image =
					client.createReferenceImage(formattedParentForImage, referenceImage, referenceImageId);
				// Display the reference image information.
				System.out.println(String.format("Reference image name: %s", image.getName()));
				System.out.println(String.format("Reference image uri: %s", image.getUri()));

			}
		}
	}

	public void deleteProduct(String productId)
		throws IOException {
		try (ProductSearchClient client = ProductSearchClient.create()) {

			// Get the full path of the product.
			String formattedName = ProductName.format(PROJECT_ID, COMPUTE_REGION, productId);

			// Delete a product.
			client.deleteProduct(formattedName);
			System.out.println("Product deleted.");
		}
	}
}
