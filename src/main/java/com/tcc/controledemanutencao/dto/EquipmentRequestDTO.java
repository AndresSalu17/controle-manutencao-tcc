package com.tcc.controledemanutencao.dto;

import com.tcc.controledemanutencao.enums.EquipmentStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EquipmentRequestDTO {

    @NotBlank(message = "O nome do equipamento é obrigatório.")
    @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres.")
    private String name;

    @NotBlank(message = "O código do equipamento é obrigatório.")
    @Size(max = 50, message = "O código deve ter no máximo 50 caracteres.")
    private String code;

    @Size(max = 100, message = "A categoria deve ter no máximo 100 caracteres.")
    private String category;

    @Size(max = 150, message = "A localização deve ter no máximo 150 caracteres.")
    private String location;

    private String description;

    @NotNull(message = "O status do equipamento é obrigatório.")
    private EquipmentStatus status;
}