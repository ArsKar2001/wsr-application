package com.karmanchik.wsr.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "manufacturer")
@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Manufacturer extends BaseEntity {
    @Column(name = "Name")
    @NotNull
    private String name;
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
}
