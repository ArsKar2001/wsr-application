package com.karmanchik.wsr.db.service;

import com.karmanchik.wsr.db.repository.JpaProductRepository;
import com.karmanchik.wsr.entity.Product;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IProductService implements ProductService {
    private final JpaProductRepository productRepository;

    public IProductService(JpaProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getOne(Integer id) {
        return productRepository.getOne(id);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findAll(Sort sort) {
        return productRepository.findAll(sort);
    }

    @Override
    public void delete(Product product) {
        productRepository.delete(product);
    }
}
