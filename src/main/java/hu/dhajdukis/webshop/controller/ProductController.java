package hu.dhajdukis.webshop.controller;

import java.util.List;

import hu.dhajdukis.webshop.dto.ProductDto;
import hu.dhajdukis.webshop.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductDto> productList() {
        return productService.retrieveProductList();
    }

    @GetMapping(value = "/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDto product(@PathVariable("id") final Long id) {
        return productService.retrieveProduct(id);
    }

    @PutMapping(value = "/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProductDto updateProduct(@PathVariable("id") final Long id, @RequestBody ProductDto productDto) {
        return productService.updateProduct(id, productDto);
    }

    @PostMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        return productService.createProduct(productDto);
    }
}
