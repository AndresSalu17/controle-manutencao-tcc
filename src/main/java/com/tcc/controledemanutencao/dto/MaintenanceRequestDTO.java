package com.tcc.controledemanutencao.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.tcc.controledemanutencao.enums.MaintenanceType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaintenanceRequestDTO {

    @NotNull(message = "O equipamento é obrigatório.")
    private Long equipmentId;

    private Long maintenancePlanId;

    @NotNull(message = "O tipo da manutenção é obrigatório.")
    private MaintenanceType type;

    @NotNull(message = "A data da manutenção é obrigatória.")
    private LocalDate date;

    @NotBlank(message = "A descrição da manutenção é obrigatória.")
    private String description;

    @PositiveOrZero(message = "O custo não pode ser negativo.")
    private BigDecimal cost;

    private String observation;
}
