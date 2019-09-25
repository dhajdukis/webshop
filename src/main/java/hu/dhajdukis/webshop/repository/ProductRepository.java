package hu.dhajdukis.webshop.repository;

import java.util.List;

import hu.dhajdukis.webshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByName(final String name);
}
