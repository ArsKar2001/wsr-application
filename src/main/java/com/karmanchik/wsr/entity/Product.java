package com.karmanchik.wsr.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "product")
@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {
    @Column(name = "Title")
    @NotNull
    private String title;
    @Column(name = "Cost")
    @NotNull
    private Double cost;
    @Column(name = "Description", nullable = false)
    private String description;
    @Column(name = "main_image_path", nullable = false)
    private String mainImagePath;
    @Column(name = "is_active")
    @NotNull
    private Boolean isActive;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "manufacturer_id", referencedColumnName = "ID", updatable = false, insertable = false)
    @NotNull
    private Manufacturer manufacturer;
}
