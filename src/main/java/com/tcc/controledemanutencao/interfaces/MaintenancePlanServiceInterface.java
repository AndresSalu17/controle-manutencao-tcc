package com.tcc.controledemanutencao.interfaces;

import java.time.LocalDate;
import java.util.List;

import com.tcc.controledemanutencao.dto.MaintenancePlanRequestDTO;
import com.tcc.controledemanutencao.models.MaintenancePlanModel;

public interface MaintenancePlanServiceInterface {

    MaintenancePlanModel save(MaintenancePlanRequestDTO dto);

    MaintenancePlanModel findById(Long id);

    MaintenancePlanModel findByEquipmentId(Long equipmentId);

    MaintenancePlanModel update(Long id, MaintenancePlanRequestDTO dto);

    void delete(Long id);

    MaintenancePlanModel save(MaintenancePlanModel maintenancePlan);

    List<MaintenancePlanModel>
    findByNextMaintenanceDateLessThanEqualOrderByNextMaintenanceDateAsc(
            LocalDate date
    );
}
