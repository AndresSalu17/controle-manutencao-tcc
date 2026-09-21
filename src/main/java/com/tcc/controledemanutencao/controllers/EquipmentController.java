package com.tcc.controledemanutencao.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tcc.controledemanutencao.dto.EquipmentRequestDTO;
import com.tcc.controledemanutencao.interfaces.EquipmentServiceInterface;
import com.tcc.controledemanutencao.interfaces.MaintenancePlanServiceInterface;
import com.tcc.controledemanutencao.models.EquipmentModel;
import com.tcc.controledemanutencao.models.MaintenancePlanModel;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/equipamentos")
@RequiredArgsConstructor
public class EquipmentController {

    private final EquipmentServiceInterface equipmentService;
    private final MaintenancePlanServiceInterface maintenancePlanService;

    @GetMapping
    public String list(Model model) {

        model.addAttribute(
                "equipments",
                equipmentService.findAll());

        return "equipment/list";
    }

    @GetMapping("/novo")
    public String createForm(Model model) {

        model.addAttribute(
                "equipmentRequestDTO",
                new EquipmentRequestDTO());

        return "equipment/form";
    }

    @PostMapping
    public String save(
            @Valid @ModelAttribute EquipmentRequestDTO dto,
            BindingResult result) {

        if (result.hasErrors()) {
            return "equipment/form";
        }

        equipmentService.save(dto);

        return "redirect:/equipamentos";
    }

    @GetMapping("/{id}/editar")
    public String editForm(
            @PathVariable Long id,
            Model model) {

        EquipmentModel equipment = equipmentService.findById(id);

        EquipmentRequestDTO dto = convertToDTO(equipment);

        model.addAttribute(
                "equipmentRequestDTO",
                dto);

        model.addAttribute(
                "equipmentId",
                id);

        return "equipment/form";
    }

    @PostMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @Valid @ModelAttribute EquipmentRequestDTO dto,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {

            model.addAttribute(
                    "equipmentId",
                    id);

            return "equipment/form";
        }

        equipmentService.update(id, dto);

        return "redirect:/equipamentos";
    }

    @PostMapping("/{id}/excluir")
    public String delete(
            @PathVariable Long id) {

        equipmentService.delete(id);

        return "redirect:/equipamentos";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {

        EquipmentModel equipment = equipmentService.findById(id);

        MaintenancePlanModel plan = maintenancePlanService.findByEquipmentId(id);

        model.addAttribute("equipment", equipment);
        model.addAttribute("maintenancePlan", plan);

        return "equipment/details";
    }

    private EquipmentRequestDTO convertToDTO(
            EquipmentModel equipment) {

        EquipmentRequestDTO dto = new EquipmentRequestDTO();

        dto.setName(equipment.getName());
        dto.setCode(equipment.getCode());
        dto.setCategory(equipment.getCategory());
        dto.setLocation(equipment.getLocation());
        dto.setDescription(equipment.getDescription());
        dto.setStatus(equipment.getStatus());

        return dto;
    }
}
