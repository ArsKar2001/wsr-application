package com.karmanchik.wsr.db.service;

import com.karmanchik.wsr.entity.Manufacturer;
import com.karmanchik.wsr.entity.Product;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ManufacturerService {
    Manufacturer save(Manufacturer manufacturer);

    Manufacturer getOne(Integer id);

    List<Manufacturer> findAll();

    List<Manufacturer> findAll(Sort sort);

    void delete(Manufacturer manufacturer);
}
