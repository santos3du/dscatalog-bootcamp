package br.com.eduardo.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.eduardo.dscatalog.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
