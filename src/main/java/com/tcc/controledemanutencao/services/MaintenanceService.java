package com.tcc.controledemanutencao.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tcc.controledemanutencao.dto.MaintenanceRequestDTO;
import com.tcc.controledemanutencao.enums.MaintenanceType;
import com.tcc.controledemanutencao.interfaces.EquipmentServiceInterface;
import com.tcc.controledemanutencao.interfaces.MaintenancePlanServiceInterface;
import com.tcc.controledemanutencao.interfaces.MaintenanceServiceInterface;
import com.tcc.controledemanutencao.models.EquipmentModel;
import com.tcc.controledemanutencao.models.MaintenanceModel;
import com.tcc.controledemanutencao.models.MaintenancePlanModel;
import com.tcc.controledemanutencao.repositories.MaintenanceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MaintenanceService implements MaintenanceServiceInterface {

    private final MaintenanceRepository maintenanceRepository;
    private final EquipmentServiceInterface equipmentService;
    private final MaintenancePlanServiceInterface maintenancePlanService;

    @Override
    public MaintenanceModel save(MaintenanceRequestDTO dto) {

        EquipmentModel equipment = equipmentService.findById(dto.getEquipmentId());

        MaintenancePlanModel plan = findPlan(dto.getMaintenancePlanId());

        MaintenanceModel maintenance = buildMaintenance(
                dto,
                equipment,
                plan);

        return maintenanceRepository.save(maintenance);
    }

    @Override
    public MaintenanceModel registerMaintenance(MaintenanceRequestDTO dto) {

        EquipmentModel equipment = equipmentService.findById(dto.getEquipmentId());

        MaintenancePlanModel plan = maintenancePlanService.findByEquipmentId(
                dto.getEquipmentId());

        if (plan != null &&
                !plan.getEquipment().getId().equals(equipment.getId())) {

            throw new RuntimeException(
                    "O plano de manutenção não pertence ao equipamento informado.");
        }

        if (dto.getType() == MaintenanceType.PREVENTIVA && plan == null) {

            throw new RuntimeException(
                    "Este equipamento não possui um plano de manutenção.");
        }

        MaintenanceModel maintenance = MaintenanceModel.builder()
                .equipment(equipment)
                .maintenancePlan(plan)
                .type(dto.getType())
                .date(dto.getDate())
                .description(dto.getDescription())
                .cost(dto.getCost())
                .observation(dto.getObservation())
                .build();

        MaintenanceModel saved = maintenanceRepository.save(maintenance);

        if (dto.getType() == MaintenanceType.PREVENTIVA) {

            updateMaintenancePlan(
                    plan,
                    dto.getDate());
        }

        return saved;
    }

    @Override
    public MaintenanceModel findById(Long id) {

        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Manutenção não encontrada."));
    }

    @Override
    public List<MaintenanceModel> findAll() {

        return maintenanceRepository.findAllByOrderByDateDesc();
    }

    @Override
    public List<MaintenanceModel> findByEquipmentId(
            Long equipmentId) {

        return maintenanceRepository
                .findByEquipmentIdOrderByDateDesc(equipmentId);
    }

    @Override
    public MaintenanceModel update(
            Long id,
            MaintenanceRequestDTO dto) {

        MaintenanceModel maintenance = findById(id);

        EquipmentModel equipment = equipmentService.findById(dto.getEquipmentId());

        MaintenancePlanModel plan = findPlan(dto.getMaintenancePlanId());

        maintenance.setEquipment(equipment);
        maintenance.setMaintenancePlan(plan);
        maintenance.setType(dto.getType());
        maintenance.setDate(dto.getDate());
        maintenance.setDescription(dto.getDescription());
        maintenance.setCost(dto.getCost());
        maintenance.setObservation(dto.getObservation());

        return maintenanceRepository.save(maintenance);
    }

    @Override
    public void delete(Long id) {

        MaintenanceModel maintenance = findById(id);

        maintenanceRepository.delete(maintenance);
    }

    private MaintenanceModel buildMaintenance(
            MaintenanceRequestDTO dto,
            EquipmentModel equipment,
            MaintenancePlanModel plan) {

        MaintenanceModel maintenance = new MaintenanceModel();

        maintenance.setEquipment(equipment);
        maintenance.setMaintenancePlan(plan);
        maintenance.setType(dto.getType());
        maintenance.setDate(dto.getDate());
        maintenance.setDescription(dto.getDescription());
        maintenance.setCost(dto.getCost());
        maintenance.setObservation(dto.getObservation());

        return maintenance;
    }

    private MaintenancePlanModel findPlan(Long planId) {

        if (planId == null) {
            return null;
        }

        return maintenancePlanService.findById(planId);
    }

    private void updateMaintenancePlan(
            MaintenancePlanModel plan,
            LocalDate maintenanceDate) {

        if (plan == null) {
            return;
        }

        plan.setLastMaintenanceDate(maintenanceDate);

        plan.setNextMaintenanceDate(
                calculateNextMaintenance(
                        plan,
                        maintenanceDate));

        maintenancePlanService.save(plan);
    }

    private LocalDate calculateNextMaintenance(
            MaintenancePlanModel plan,
            LocalDate date) {

        return switch (plan.getFrequencyUnit()) {

            case DAY ->
                date.plusDays(plan.getFrequency());

            case WEEK ->
                date.plusWeeks(plan.getFrequency());

            case MONTH ->
                date.plusMonths(plan.getFrequency());

            case YEAR ->
                date.plusYears(plan.getFrequency());
        };
    }
}