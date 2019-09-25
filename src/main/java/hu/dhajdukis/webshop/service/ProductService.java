package hu.dhajdukis.webshop.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import hu.dhajdukis.webshop.dto.ProductDto;
import hu.dhajdukis.webshop.entity.Product;
import hu.dhajdukis.webshop.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {this.productRepository = productRepository;}

    public List<ProductDto> retrieveProductList() {
        return productRepository.findAll().stream().map(ProductDto::new).collect(Collectors.toList());
    }

    public ProductDto retrieveProduct(final Long id) {
        final Optional<Product> product = productRepository.findById(id);
        return product.isEmpty() ? null : new ProductDto(product.get());
    }

    @Transactional(rollbackFor = Exception.class)
    public ProductDto updateProduct(final Long id, final ProductDto productDto) {

        checkIfProductIdIsNotGiven(productDto);

        checkIfGivenIdIsDifferent(id, productDto);

        checkIfOtherProductWithThisNameExists(productDto);

        final Optional<Product> productOpt = productRepository.findById(id);

        checkIfProductIsNotPresent(id, productOpt);

        final Product product = productOpt.get();
        product.updateProduct(productDto);

        productDto.setLastUpdate(product.getLastUpdate());

        return productDto;
    }

    @Transactional(rollbackFor = Exception.class)
    public ProductDto createProduct(final ProductDto productDto) {

        checkIfOtherProductWithThisNameExists(productDto);

        final Product product = new Product(productDto);
        productRepository.save(product);

        productDto.setId(product.getId());
        productDto.setLastUpdate(product.getLastUpdate());

        return productDto;
    }

    private void checkIfProductIsNotPresent(final Long id, final Optional<Product> productOpt) {
        if (productOpt.isEmpty()) {
            throw new IllegalArgumentException(String.format("Product with the given id: %s is not present!", id));
        }
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

    private void checkIfOtherProductWithThisNameExists(final ProductDto productDto) {
        final List<Product> existingProducts = productRepository.findByName(productDto.getName());
        if (!existingProducts.isEmpty()
            && existingProducts.stream().anyMatch(p -> !p.getId().equals(productDto.getId()))) {
            throw new IllegalArgumentException(String.format("Product with the given name: %s is exists with a different id!",
                                                             productDto.getName()));
        }
    }
}
