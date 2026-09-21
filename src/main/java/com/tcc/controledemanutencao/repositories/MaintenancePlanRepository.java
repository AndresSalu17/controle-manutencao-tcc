package com.tcc.controledemanutencao.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcc.controledemanutencao.models.MaintenancePlanModel;

public interface MaintenancePlanRepository extends JpaRepository<MaintenancePlanModel, Long> {

    Optional<MaintenancePlanModel> findByEquipmentId(Long equipmentId);

    long countByNextMaintenanceDateBefore(LocalDate date);

    long countByNextMaintenanceDateBetween(
            LocalDate startDate,
            LocalDate endDate);

    List<MaintenancePlanModel> findByNextMaintenanceDateLessThanEqualOrderByNextMaintenanceDateAsc(LocalDate date);

}
