package com.tcc.controledemanutencao.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tcc.controledemanutencao.dto.MaintenancePlanRequestDTO;
import com.tcc.controledemanutencao.enums.FrequencyUnit;
import com.tcc.controledemanutencao.interfaces.EquipmentServiceInterface;
import com.tcc.controledemanutencao.interfaces.MaintenancePlanServiceInterface;
import com.tcc.controledemanutencao.models.MaintenancePlanModel;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/planos-manutencao")
@RequiredArgsConstructor
public class MaintenancePlanController {

    private final MaintenancePlanServiceInterface maintenancePlanService;
    private final EquipmentServiceInterface equipmentService;

    @GetMapping("/novo")
    public String createForm(Model model) {

        model.addAttribute(
                "maintenancePlanRequestDTO",
                new MaintenancePlanRequestDTO());

        model.addAttribute(
                "equipments",
                equipmentService.findAll());

        model.addAttribute(
                "frequencyUnits",
                FrequencyUnit.values());

        return "maintenance-plan/form";
    }

    @PostMapping
    public String save(
            @Valid @ModelAttribute MaintenancePlanRequestDTO dto,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {

            model.addAttribute(
                    "equipments",
                    equipmentService.findAll());

            model.addAttribute(
                    "frequencyUnits",
                    FrequencyUnit.values());

            return "maintenance-plan/form";
        }

        maintenancePlanService.save(dto);

        return "redirect:/equipamentos";
    }

    @GetMapping("/{id}/editar")
    public String editForm(
            @PathVariable Long id,
            Model model) {

        MaintenancePlanModel plan = maintenancePlanService.findById(id);

        MaintenancePlanRequestDTO dto = convertToDTO(plan);

        model.addAttribute(
                "maintenancePlanRequestDTO",
                dto);

        model.addAttribute(
                "maintenancePlanId",
                id);

        model.addAttribute(
                "equipments",
                equipmentService.findAll());

        model.addAttribute(
                "frequencyUnits",
                FrequencyUnit.values());

        return "maintenance-plan/form";
    }

    @PostMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @Valid @ModelAttribute MaintenancePlanRequestDTO dto,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {

            model.addAttribute(
                    "maintenancePlanId",
                    id);

            model.addAttribute(
                    "equipments",
                    equipmentService.findAll());

            model.addAttribute(
                    "frequencyUnits",
                    FrequencyUnit.values());

            return "maintenance-plan/form";
        }

        maintenancePlanService.update(id, dto);

        return "redirect:/equipamentos";
    }

    @PostMapping("/{id}/excluir")
    public String delete(
            @PathVariable Long id) {

        maintenancePlanService.delete(id);

        return "redirect:/equipamentos";
    }

    private MaintenancePlanRequestDTO convertToDTO(
            MaintenancePlanModel plan) {

        MaintenancePlanRequestDTO dto = new MaintenancePlanRequestDTO();

        dto.setEquipmentId(
                plan.getEquipment().getId());

        dto.setDescription(
                plan.getDescription());

        dto.setFrequency(
                plan.getFrequency());

        dto.setFrequencyUnit(
                plan.getFrequencyUnit());

        dto.setLastMaintenanceDate(
                plan.getLastMaintenanceDate());

        return dto;
    }
}
