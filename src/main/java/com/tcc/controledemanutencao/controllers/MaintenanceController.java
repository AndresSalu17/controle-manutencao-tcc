package com.tcc.controledemanutencao.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tcc.controledemanutencao.dto.MaintenanceRequestDTO;
import com.tcc.controledemanutencao.enums.MaintenanceType;
import com.tcc.controledemanutencao.interfaces.EquipmentServiceInterface;
import com.tcc.controledemanutencao.interfaces.MaintenanceServiceInterface;
import com.tcc.controledemanutencao.models.MaintenanceModel;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/manutencoes")
@RequiredArgsConstructor
public class MaintenanceController {

    private final MaintenanceServiceInterface maintenanceService;
    private final EquipmentServiceInterface equipmentService;

    @GetMapping
    public String list(Model model) {

        model.addAttribute(
                "maintenances",
                maintenanceService.findAll());

        return "maintenance/list";
    }

    @GetMapping("/nova")
    public String createForm(Model model) {

        prepareForm(model);

        model.addAttribute(
                "maintenanceRequestDTO",
                new MaintenanceRequestDTO());

        return "maintenance/form";
    }

    @PostMapping
    public String save(
            @Valid @ModelAttribute MaintenanceRequestDTO dto,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {

            prepareForm(model);

            return "maintenance/form";
        }

        maintenanceService.registerMaintenance(dto);

        return "redirect:/manutencoes";
    }

    @GetMapping("/{id}/editar")
    public String editForm(
            @PathVariable Long id,
            Model model) {

        MaintenanceModel maintenance = maintenanceService.findById(id);

        MaintenanceRequestDTO dto = convertToDTO(maintenance);

        model.addAttribute(
                "maintenanceRequestDTO",
                dto);

        model.addAttribute(
                "maintenanceId",
                id);

        prepareForm(model);

        return "maintenance/form";
    }

    @PostMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @Valid @ModelAttribute MaintenanceRequestDTO dto,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {

            model.addAttribute(
                    "maintenanceId",
                    id);

            prepareForm(model);

            return "maintenance/form";
        }

        maintenanceService.update(id, dto);

        return "redirect:/manutencoes";
    }

    @PostMapping("/{id}/excluir")
    public String delete(
            @PathVariable Long id) {

        maintenanceService.delete(id);

        return "redirect:/manutencoes";
    }

    @GetMapping("/equipamento/{equipmentId}")
    public String equipmentHistory(
            @PathVariable Long equipmentId,
            Model model) {

        model.addAttribute(
                "equipment",
                equipmentService.findById(equipmentId));

        model.addAttribute(
                "maintenances",
                maintenanceService.findByEquipmentId(equipmentId));

        return "maintenance/history";
    }

    private void prepareForm(Model model) {

        model.addAttribute(
                "equipments",
                equipmentService.findAll());

        model.addAttribute(
                "maintenanceTypes",
                MaintenanceType.values());
    }

    private MaintenanceRequestDTO convertToDTO(
            MaintenanceModel maintenance) {

        MaintenanceRequestDTO dto = new MaintenanceRequestDTO();

        dto.setEquipmentId(
                maintenance.getEquipment().getId());

        if (maintenance.getMaintenancePlan() != null) {

            dto.setMaintenancePlanId(
                    maintenance.getMaintenancePlan().getId());
        }

        dto.setType(maintenance.getType());
        dto.setDate(maintenance.getDate());
        dto.setDescription(maintenance.getDescription());
        dto.setCost(maintenance.getCost());
        dto.setObservation(maintenance.getObservation());

        return dto;
    }
}
