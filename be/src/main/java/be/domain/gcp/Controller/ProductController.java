package be.domain.gcp.Controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import be.domain.gcp.Service.ProductService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
	private final ProductService productService;

	@PostMapping("/set/add")
	public void createProductSet() throws IOException {
		productService.createProductSet();
	}

	@PostMapping("/init")
	public void createAndInitProduct() throws IOException {
		productService.createAndInitProduct();
	}

	@GetMapping("/set/get")
	public void getProduct() throws IOException {
		productService.getProduct();
	}

	@GetMapping("/set/list/get")
	public void getProductSet() throws IOException {
		productService.getProductSet();
	}

	@DeleteMapping("/delete")
	public void deleteProduct(@RequestParam(name = "product") String productId) throws IOException {
		productService.deleteProduct(productId);
	}
}
