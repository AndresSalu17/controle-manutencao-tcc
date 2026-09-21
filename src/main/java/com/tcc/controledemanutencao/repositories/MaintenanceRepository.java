package com.tcc.controledemanutencao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcc.controledemanutencao.models.MaintenanceModel;

public interface MaintenanceRepository extends JpaRepository<MaintenanceModel, Long> {

    List<MaintenanceModel> findByEquipmentIdOrderByDateDesc(Long equipmentId);

    List<MaintenanceModel> findAllByOrderByDateDesc();

    List<MaintenanceModel> findTop5ByOrderByDateDesc();
}
