package us.telran.pawnshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import us.telran.pawnshop.dto.ProductCreationRequest;
import us.telran.pawnshop.entity.Product;
import us.telran.pawnshop.entity.enums.ProductStatus;
import us.telran.pawnshop.service.ProductService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "pawn-shop/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping(value = "add")
    @Operation(summary = "NEW PRODUCT", description = "Create and save product to the DB")
    public void createNewProduct(@RequestBody ProductCreationRequest productCreationRequest) {
        productService.addNewProduct(productCreationRequest);
    }

    @GetMapping(value = "show")
    @Operation(summary = "ALL PRODUCTS", description = "Show all products in DB")
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @PutMapping(path = "change/{productId}")
    @Operation(summary = "EDIT PRODUCT", description = "Change product with specified id")
    public void updateProductById(@PathVariable("productId") Long productId,
                           @RequestParam(required = false) String productName,
                           @RequestParam(required = false) ProductStatus productStatus,
                           @RequestParam(required = false) BigDecimal interestRate) {

            productService.updateProduct(productId, productName, productStatus, interestRate);
    }

    @DeleteMapping(path = "remove/{productId}")
    @Operation(summary = "DELETE PRODUCT", description = "Remove product with specified id from the DB")
    public void deleteProduct(@PathVariable("productId") Long productId) {
        productService.deleteProduct(productId);
    }
}
