package com.tcc.controledemanutencao.interfaces;

import java.util.List;

import com.tcc.controledemanutencao.dto.EquipmentRequestDTO;
import com.tcc.controledemanutencao.models.EquipmentModel;

public interface EquipmentServiceInterface {

    EquipmentModel save(EquipmentRequestDTO dto);

    EquipmentModel findById(Long id);

    List<EquipmentModel> findAll();

    EquipmentModel update(Long id, EquipmentRequestDTO dto);

    void delete(Long id);

    EquipmentModel findByCode(String code);
}