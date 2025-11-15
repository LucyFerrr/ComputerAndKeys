package org.ksa.mapper;

import org.ksa.dto.ComputerDTO;
import org.ksa.entity.Computer;
import org.springframework.stereotype.Component;

@Component
public class ComputerMapper {

    // Entity to DTO
    public static ComputerDTO mapToComputerDto(Computer computer) {
        if(computer == null) return null;

        ComputerDTO.ColorsWrapper colorsWrapper = ComputerDTO.ColorsWrapper.builder()
                .color(computer.getColors())
                .build();

        return ComputerDTO.builder()
                .type(computer.getType())
                .maker(computer.getMaker())
                .model(computer.getModel())
                .language(computer.getLanguage())
                .colors(colorsWrapper)
                .build();
    }

    // DTO to entity
    public static Computer mapToComputer (ComputerDTO computerDTO) {
        if (computerDTO == null) return null;

        return Computer.builder()
                .type(computerDTO.getType())
                .maker(computerDTO.getMaker())
                .model(computerDTO.getModel())
                .language(computerDTO.getLanguage())
                .colors(computerDTO.getColors() != null && computerDTO.getColors().getColor() != null
                ? computerDTO.getColors().getColor() : null)
                .build();
    }

    // Update entity with DTO data
    public static void updateEntityFromDTO(ComputerDTO computerDTO, Computer computer) {
        if (computerDTO == null || computer == null) return;

        if (computerDTO.getType() != null) computer.setType(computerDTO.getType());

        if(computerDTO.getMaker() != null) computer.setMaker(computerDTO.getMaker());

        if(computerDTO.getModel() != null) computer.setModel(computerDTO.getModel());

        if(computerDTO.getLanguage() != null) computer.setLanguage(computerDTO.getLanguage());

        if(computerDTO.getColors() != null && computerDTO.getColors().getColor() != null) computer.setColors(computerDTO.getColors().getColor());
    }
}
