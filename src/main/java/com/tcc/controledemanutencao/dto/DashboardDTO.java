package com.tcc.controledemanutencao.dto;

import java.util.List;

import com.tcc.controledemanutencao.models.MaintenanceModel;
import com.tcc.controledemanutencao.models.MaintenancePlanModel;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DashboardDTO {

    private long totalEquipment;

    private long maintenanceInDay;

    private long maintenanceUpcoming;

    private long maintenanceOverdue;

    private List<MaintenancePlanModel> attentionPlans;

    private List<MaintenanceModel> recentMaintenances;
}
