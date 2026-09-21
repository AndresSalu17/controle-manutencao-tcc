package com.tcc.controledemanutencao.dto;

import java.time.LocalDate;

import com.tcc.controledemanutencao.enums.FrequencyUnit;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaintenancePlanRequestDTO {

    @NotNull(message = "O equipamento é obrigatório.")
    private Long equipmentId;

    @NotBlank(message = "A descrição da manutenção é obrigatória.")
    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres.")
    private String description;

    @NotNull(message = "A periodicidade é obrigatória.")
    @Min(value = 1, message = "A periodicidade deve ser maior que zero.")
    private Integer frequency;

    @NotNull(message = "A unidade da periodicidade é obrigatória.")
    private FrequencyUnit frequencyUnit;

    @NotNull(message = "A data da última manutenção é obrigatória.")
    private LocalDate lastMaintenanceDate;
}
