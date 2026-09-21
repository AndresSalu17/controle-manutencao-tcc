package com.tcc.controledemanutencao.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tcc.controledemanutencao.dto.EquipmentRequestDTO;
import com.tcc.controledemanutencao.interfaces.EquipmentServiceInterface;
import com.tcc.controledemanutencao.models.EquipmentModel;
import com.tcc.controledemanutencao.repositories.EquipmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EquipmentService implements EquipmentServiceInterface {

    private final EquipmentRepository equipmentRepository;

    @Override
    public EquipmentModel save(EquipmentRequestDTO dto) {

        validateCodeForCreation(dto.getCode());

        EquipmentModel equipment = new EquipmentModel();

        equipment.setName(dto.getName());
        equipment.setCode(dto.getCode());
        equipment.setCategory(dto.getCategory());
        equipment.setLocation(dto.getLocation());
        equipment.setDescription(dto.getDescription());
        equipment.setStatus(dto.getStatus());

        return equipmentRepository.save(equipment);
    }

    @Override
    public EquipmentModel findById(Long id) {

        return equipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado."));
    }

    @Override
    public List<EquipmentModel> findAll() {

        return equipmentRepository.findAllByOrderByNameAsc();
    }

    @Override
    public EquipmentModel update(
            Long id,
            EquipmentRequestDTO dto) {

        EquipmentModel equipment = findById(id);

        validateCodeForUpdate(dto.getCode(), id);

        equipment.setName(dto.getName());
        equipment.setCode(dto.getCode());
        equipment.setCategory(dto.getCategory());
        equipment.setLocation(dto.getLocation());
        equipment.setDescription(dto.getDescription());
        equipment.setStatus(dto.getStatus());

        return equipmentRepository.save(equipment);
    }

    @Override
    public void delete(Long id) {

        EquipmentModel equipment = findById(id);

        equipmentRepository.delete(equipment);
    }

    @Override
    public EquipmentModel findByCode(String code) {

        return equipmentRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado."));
    }

    private void validateCodeForCreation(String code) {

        if (equipmentRepository.findByCode(code).isPresent()) {
            throw new RuntimeException(
                    "Já existe um equipamento com esse código.");
        }
    }

    private void validateCodeForUpdate(String code, Long equipmentId) {

        equipmentRepository.findByCode(code)
                .ifPresent(existingEquipment -> {

                    if (!existingEquipment.getId().equals(equipmentId)) {
                        throw new RuntimeException(
                                "Já existe um equipamento com esse código.");
                    }
                });
    }
}