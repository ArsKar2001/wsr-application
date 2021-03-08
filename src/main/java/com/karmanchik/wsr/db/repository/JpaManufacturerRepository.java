package com.karmanchik.wsr.db.repository;

import com.karmanchik.wsr.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaManufacturerRepository extends JpaRepository<Manufacturer, Integer> {

}
