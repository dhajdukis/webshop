import java.math.BigDecimal;
import java.util.Optional;

import hu.dhajdukis.webshop.dto.ProductDto;
import hu.dhajdukis.webshop.entity.Product;
import hu.dhajdukis.webshop.repository.ProductRepository;
import hu.dhajdukis.webshop.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {
    private ProductService productService;

    @Mock
    ProductRepository productRepository;

    @BeforeEach
    void setup() {
        productService = new ProductService(productRepository);
    }

    @Test
    void update_product_missingProductId() {
        final ProductDto productDto = new ProductDto(null, "first", BigDecimal.valueOf(110L), null);

        final Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> productService.updateProduct(1L, productDto));
        Assertions.assertEquals("Missing product id!", exception.getMessage());
    }

    @Test
    void update_product_given_id_different() {
        final ProductDto productDto = new ProductDto(2L, "first", BigDecimal.valueOf(110L), null);

        final Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> productService.updateProduct(1L, productDto));
        Assertions.assertEquals("Given id: 1 is different than the product id 2 !", exception.getMessage());
    }

    @Test
    void update_product_other_product_exists_with_the_given_name() {
        final ProductDto productDto = new ProductDto(2L, "second", BigDecimal.valueOf(110L), null);

        final Optional<Product> productOpt = Optional.of(new Product());

        when(productRepository.findById(2L)).thenReturn(productOpt);

        when(productRepository.save(Mockito.any(Product.class)))
                .thenThrow(new DataIntegrityViolationException("Unique constraint violation!"));

        final Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> productService.updateProduct(2L, productDto));
        Assertions.assertEquals("Product with the given name: second is exists with a different id!",
                exception.getMessage());
    }

    @Test
    void update_product_product_missing_from_database() {
        final ProductDto productDto = new ProductDto(1L, "first", BigDecimal.valueOf(110L), null);

        final Optional<Product> productOpt = Optional.empty();

        when(productRepository.findById(1L)).thenReturn(productOpt);

        final Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> productService.updateProduct(1L, productDto));
        Assertions.assertEquals("Product with the given id: 1 is not present!", exception.getMessage());
    }

    @Test
    void update_product() {
        final ProductDto productDto = new ProductDto(1L, "first", BigDecimal.valueOf(120L), null);

        final Product product = new Product();
        product.setId(1L);
        product.setName("second");
        product.setCurrentPrice(BigDecimal.valueOf(999L));

        final Optional<Product> productOpt = Optional.of(product);

        when(productRepository.findById(1L)).thenReturn(productOpt);

        final ProductDto resultDto = productService.updateProduct(1L, productDto);
        Assertions.assertEquals(productDto.getId(), resultDto.getId());
        Assertions.assertEquals(productDto.getName(), resultDto.getName());
        Assertions.assertEquals(productDto.getCurrentPrice(), resultDto.getCurrentPrice());
    }

    @Test
    void create_product_name_exists_in_the_database() {
        final ProductDto productDto = new ProductDto(null, "first", BigDecimal.valueOf(120L), null);

        when(productRepository.save(Mockito.any(Product.class)))
                .thenThrow(new DataIntegrityViolationException("Unique constraint violation!"));

        final Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> productService.createProduct(productDto));
        Assertions.assertEquals("Product with the given name: first is exists with a different id!",
                exception.getMessage());
    }

    @Test
    void create_product() {
        final ProductDto productDto = new ProductDto(null, "first", BigDecimal.valueOf(120L), null);

        final ProductDto resultDto = productService.createProduct(productDto);
        Assertions.assertEquals(productDto.getName(), resultDto.getName());
        Assertions.assertEquals(productDto.getCurrentPrice(), resultDto.getCurrentPrice());
        Assertions.assertNotNull(resultDto.getLastUpdate());
    }
}
