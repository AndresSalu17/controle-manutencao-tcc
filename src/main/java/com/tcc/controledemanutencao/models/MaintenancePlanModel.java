package com.tcc.controledemanutencao.models;

import java.time.LocalDate;

import com.tcc.controledemanutencao.enums.FrequencyUnit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "maintenance_plan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaintenancePlanModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id", nullable = false, unique = true)
    private EquipmentModel equipment;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer frequency;

    @Enumerated(EnumType.STRING)
    @Column(name = "frequency_unit", nullable = false, length = 20)
    private FrequencyUnit frequencyUnit;

    @Column(name = "last_maintenance_date")
    private LocalDate lastMaintenanceDate;

    @Column(name = "next_maintenance_date", nullable = false)
    private LocalDate nextMaintenanceDate;
}