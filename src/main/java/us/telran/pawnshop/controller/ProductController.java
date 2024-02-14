package us.telran.pawnshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import us.telran.pawnshop.dto.ProductCreationRequest;
import us.telran.pawnshop.entity.Product;
import us.telran.pawnshop.entity.enums.ProductName;
import us.telran.pawnshop.entity.enums.ProductStatus;
import us.telran.pawnshop.service.ProductService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping(value = "add")
    public void createNewProduct(@RequestBody ProductCreationRequest productCreationRequest) {
        productService.addNewProduct(productCreationRequest);
    }

    @GetMapping
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @DeleteMapping(path = "remove/{productId}")
    public void deleteProduct(@PathVariable("productId") Long productId) {
        productService.deleteProduct(productId);
    }

    @PutMapping(path = "change/{productId}")
    public void updateProductById(@PathVariable("productId") Long productId,
                           @RequestParam(required = false) String productName,
                           @RequestParam(required = false) ProductStatus productStatus,
                           @RequestParam(required = false) BigDecimal interestRate) {

            productService.updateProduct(productId, productName, productStatus, interestRate);
    }
}
