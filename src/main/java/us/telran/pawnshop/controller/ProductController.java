package us.telran.pawnshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import us.telran.pawnshop.entity.Product;
import us.telran.pawnshop.entity.enums.ProductName;
import us.telran.pawnshop.entity.enums.ProductStatus;
import us.telran.pawnshop.service.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping(value = "add")
    public void createNewProduct(@RequestBody Product product) {
        productService.addNewProduct(product);
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
                           @RequestParam(required = false) ProductName productName,
                           @RequestParam(required = false) ProductStatus productStatus,
                           @RequestParam(required = false) double interestRate) {
            productService.updateProduct(productId, productName, productStatus, interestRate);
    }
}
