package com.tcc.controledemanutencao.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tcc.controledemanutencao.dto.MaintenancePlanRequestDTO;
import com.tcc.controledemanutencao.interfaces.EquipmentServiceInterface;
import com.tcc.controledemanutencao.interfaces.MaintenancePlanServiceInterface;
import com.tcc.controledemanutencao.models.EquipmentModel;
import com.tcc.controledemanutencao.models.MaintenancePlanModel;
import com.tcc.controledemanutencao.repositories.MaintenancePlanRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MaintenancePlanService
        implements MaintenancePlanServiceInterface {

    private final MaintenancePlanRepository maintenancePlanRepository;
    private final EquipmentServiceInterface equipmentService;

    @Override
    public MaintenancePlanModel save(MaintenancePlanRequestDTO dto) {

        if (maintenancePlanRepository
                .findByEquipmentId(dto.getEquipmentId())
                .isPresent()) {

            throw new RuntimeException(
                    "Este equipamento já possui um plano de manutenção.");
        }

        EquipmentModel equipment = equipmentService.findById(dto.getEquipmentId());

        MaintenancePlanModel plan = new MaintenancePlanModel();

        plan.setEquipment(equipment);
        plan.setDescription(dto.getDescription());
        plan.setFrequency(dto.getFrequency());
        plan.setFrequencyUnit(dto.getFrequencyUnit());

        LocalDate lastMaintenanceDate = dto.getLastMaintenanceDate();

        plan.setLastMaintenanceDate(lastMaintenanceDate);

        plan.setNextMaintenanceDate(
                calculateNextMaintenance(
                        lastMaintenanceDate,
                        dto.getFrequency(),
                        dto.getFrequencyUnit()));

        return maintenancePlanRepository.save(plan);
    }

    @Override
    public MaintenancePlanModel findById(Long id) {

        return maintenancePlanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Plano de manutenção não encontrado."));
    }

    @Override
    public MaintenancePlanModel findByEquipmentId(Long equipmentId) {

        return maintenancePlanRepository
                .findByEquipmentId(equipmentId)
                .orElse(null);
    }

    @Override
    public MaintenancePlanModel update(
            Long id,
            MaintenancePlanRequestDTO dto) {

        MaintenancePlanModel plan = findById(id);

        plan.setDescription(dto.getDescription());
        plan.setFrequency(dto.getFrequency());
        plan.setFrequencyUnit(dto.getFrequencyUnit());
        plan.setLastMaintenanceDate(dto.getLastMaintenanceDate());

        plan.setNextMaintenanceDate(
                calculateNextMaintenance(
                        dto.getLastMaintenanceDate(),
                        dto.getFrequency(),
                        dto.getFrequencyUnit()));

        return maintenancePlanRepository.save(plan);
    }

    @Override
    public void delete(Long id) {

        MaintenancePlanModel plan = findById(id);

        maintenancePlanRepository.delete(plan);
    }

    private LocalDate calculateNextMaintenance(
            LocalDate date,
            Integer frequency,
            com.tcc.controledemanutencao.enums.FrequencyUnit unit) {

        if (date == null) {
            return LocalDate.now();
        }

        return switch (unit) {

            case DAY ->
                date.plusDays(frequency);

            case WEEK ->
                date.plusWeeks(frequency);

            case MONTH ->
                date.plusMonths(frequency);

            case YEAR ->
                date.plusYears(frequency);
        };
    }

    @Override
    public MaintenancePlanModel save(
            MaintenancePlanModel maintenancePlan) {
        return maintenancePlanRepository.save(maintenancePlan);
    }

    @Override
    public List<MaintenancePlanModel> findByNextMaintenanceDateLessThanEqualOrderByNextMaintenanceDateAsc(
            LocalDate date) {

        return maintenancePlanRepository
                .findByNextMaintenanceDateLessThanEqualOrderByNextMaintenanceDateAsc(date);
    }
}
