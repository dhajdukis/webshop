package hu.dhajdukis.webshop.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import hu.dhajdukis.webshop.dto.ProductDto;
import hu.dhajdukis.webshop.entity.Product;
import hu.dhajdukis.webshop.repository.ProductRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> retrieveProductList() {
        return productRepository.findAll().stream().map(ProductDto::new).collect(Collectors.toList());
    }

    public ProductDto retrieveProduct(final Long id) {
        final Optional<Product> product = productRepository.findById(id);
        return product.isEmpty() ? null : new ProductDto(product.get());
    }

    public ProductDto updateProduct(final Long id, final ProductDto productDto) {
        checkIfProductIdIsNotGiven(productDto);
        checkIfGivenIdIsDifferent(id, productDto);
        try {
            updateProductEntity(id, productDto);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException(String.format("Product with the given name: %s is exists with a different id!",
                    productDto.getName()));
        }
        return productDto;
    }

    @Transactional
    void updateProductEntity(final Long id, final ProductDto productDto) {
        final Optional<Product> productOpt = productRepository.findById(id);
        final Product product = productOpt.orElseThrow(() -> new IllegalArgumentException(String.format("Product with the given id: %s is not present!", id)));
        product.updateProduct(productDto);
        productDto.setLastUpdate(product.getLastUpdate());
        productRepository.save(product);
    }

    @Transactional
    public ProductDto createProduct(final ProductDto productDto) {

        final Product product = new Product(productDto);
        try {
            productRepository.save(product);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException(String.format("Product with the given name: %s is exists with a different id!",
                    productDto.getName()));
        }
        productDto.setId(product.getId());
        productDto.setLastUpdate(product.getLastUpdate());

        return productDto;
    }

    private void checkIfGivenIdIsDifferent(final Long id, final ProductDto productDto) {
        if (!id.equals(productDto.getId())) {
            throw new IllegalArgumentException(String.format("Given id: %s is different than the product id %s !",
                    id,
                    productDto.getId()));
        }
    }

    private void checkIfProductIdIsNotGiven(final ProductDto productDto) {
        if (productDto.getId() == null) {
            throw new IllegalArgumentException("Missing product id!");
        }
    }
}
