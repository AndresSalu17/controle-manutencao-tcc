package com.tcc.controledemanutencao.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcc.controledemanutencao.models.EquipmentModel;

public interface EquipmentRepository extends JpaRepository<EquipmentModel, Long> {

    Optional<EquipmentModel> findByCode(String code);

    List<EquipmentModel> findAllByOrderByNameAsc();
}