package com.karmanchik.wsr.db.repository;

import com.karmanchik.wsr.entity.Manufacturer;
import com.karmanchik.wsr.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface JpaProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByManufacturer(@NotNull Manufacturer manufacturer);
}
