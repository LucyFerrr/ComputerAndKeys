package org.ksa.mapper;

import org.ksa.dto.ComputerDTO;
import org.ksa.entity.Computer;
import org.springframework.stereotype.Component;

@Component
public class ComputerMapper {

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
}
