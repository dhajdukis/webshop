package hu.dhajdukis.webshop.controller;

import java.util.List;

import hu.dhajdukis.webshop.dto.ProductDto;
import hu.dhajdukis.webshop.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {this.productService = productService;}

    @GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<ProductDto> productList() {
        return productService.retrieveProductList();
    }

    @GetMapping(value = "/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ProductDto product(@PathVariable("id") final Long id) {
        return productService.retrieveProduct(id);
    }

    @PutMapping(value = "/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ProductDto updateProduct(@PathVariable("id") final Long id, @RequestBody ProductDto productDto) {
        return productService.updateProduct(id, productDto);
    }

    @PostMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        return productService.createProduct(productDto);
    }
}
