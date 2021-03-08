package com.karmanchik.wsr.db.service;

import com.karmanchik.wsr.db.repository.JpaManufacturerRepository;
import com.karmanchik.wsr.entity.Manufacturer;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IManufacturerService implements ManufacturerService {
    private final JpaManufacturerRepository manufacturerRepository;

    public IManufacturerService(JpaManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public Manufacturer save(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

    @Override
    public Manufacturer getOne(Integer id) {
        return manufacturerRepository.getOne(id);
    }

    @Override
    public List<Manufacturer> findAll() {
        return manufacturerRepository.findAll();
    }

    @Override
    public List<Manufacturer> findAll(Sort sort) {
        return manufacturerRepository.findAll(sort);
    }

    @Override
    public void delete(Manufacturer manufacturer) {
        manufacturerRepository.delete(manufacturer);
    }
}
