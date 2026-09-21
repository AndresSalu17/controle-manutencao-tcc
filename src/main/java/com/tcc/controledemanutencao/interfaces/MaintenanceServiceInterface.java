package com.tcc.controledemanutencao.interfaces;

import java.util.List;

import com.tcc.controledemanutencao.dto.MaintenanceRequestDTO;
import com.tcc.controledemanutencao.models.MaintenanceModel;

public interface MaintenanceServiceInterface {

    MaintenanceModel save(MaintenanceRequestDTO dto);

    MaintenanceModel findById(Long id);

    List<MaintenanceModel> findAll();

    List<MaintenanceModel> findByEquipmentId(Long equipmentId);

    MaintenanceModel update(Long id, MaintenanceRequestDTO dto);

    void delete(Long id);

    MaintenanceModel registerMaintenance(MaintenanceRequestDTO dto);

}
