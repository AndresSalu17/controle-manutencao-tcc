package com.tcc.controledemanutencao.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tcc.controledemanutencao.dto.DashboardDTO;
import com.tcc.controledemanutencao.interfaces.DashboardServiceInterface;
import com.tcc.controledemanutencao.models.MaintenancePlanModel;
import com.tcc.controledemanutencao.repositories.EquipmentRepository;
import com.tcc.controledemanutencao.repositories.MaintenancePlanRepository;
import com.tcc.controledemanutencao.repositories.MaintenanceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService implements DashboardServiceInterface {

        private static final int UPCOMING_DAYS = 7;

        private final EquipmentRepository equipmentRepository;
        private final MaintenancePlanRepository maintenancePlanRepository;
        private final MaintenanceRepository maintenanceRepository;

        @Override
        public DashboardDTO getDashboard() {

                LocalDate today = LocalDate.now();

                LocalDate upcomingLimit = today.plusDays(UPCOMING_DAYS);

                long totalEquipment = equipmentRepository.count();

                long maintenanceOverdue = maintenancePlanRepository
                                .countByNextMaintenanceDateBefore(today);

                long maintenanceUpcoming = maintenancePlanRepository
                                .countByNextMaintenanceDateBetween(
                                                today,
                                                upcomingLimit);

                long totalPlans = maintenancePlanRepository.count();

                long maintenanceInDay = totalPlans
                                - maintenanceOverdue
                                - maintenanceUpcoming;

                List<MaintenancePlanModel> attentionPlans = maintenancePlanRepository
                                .findByNextMaintenanceDateLessThanEqualOrderByNextMaintenanceDateAsc(
                                                upcomingLimit)
                                .stream()
                                .limit(5)
                                .toList();

                return DashboardDTO.builder()

                                .totalEquipment(totalEquipment)

                                .maintenanceInDay(maintenanceInDay)

                                .maintenanceUpcoming(maintenanceUpcoming)

                                .maintenanceOverdue(maintenanceOverdue)

                                .attentionPlans(attentionPlans)

                                .recentMaintenances(
                                                maintenanceRepository
                                                                .findTop5ByOrderByDateDesc())

                                .build();
        }
}
