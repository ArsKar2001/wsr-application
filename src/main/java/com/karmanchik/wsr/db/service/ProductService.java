package com.karmanchik.wsr.db.service;

import com.karmanchik.wsr.entity.Product;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ProductService {
    Product save(Product product);

    Product getOne(Integer id);

    List<Product> findAll();
    List<Product> findAll(Sort sort);

    void delete(Product product);
}
