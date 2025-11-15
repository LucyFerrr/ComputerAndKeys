package org.ksa.mapper;

import org.ksa.dto.ComputerDTO;
import org.ksa.entity.Computer;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting between {@link Computer} entities and {@link ComputerDTO} objects.
 */
@Component
public class ComputerMapper {

    /**
     * Converts a {@link Computer} entity to a {@link ComputerDTO}.
     *
     * @param computer entity to convert
     * @return the corresponding DTO, or {@code null} if input is {@code null}
     */
    public static ComputerDTO mapToComputerDto(Computer computer) {
        if (computer == null) return null;

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

    /**
     * Converts a {@link ComputerDTO} to a {@link Computer} entity.
     *
     * @param computerDTO DTO to convert
     * @return the corresponding entity, or {@code null} if input is {@code null}
     */
    public static Computer mapToComputer(ComputerDTO computerDTO) {
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
    
    /**
     * Updates an existing {@link Computer} entity with values from {@link ComputerDTO}.
     *
     * @param computerDTO source DTO
     * @param computer    target entity to update
     */
    public static void updateEntityFromDTO(ComputerDTO computerDTO, Computer computer) {
        if (computerDTO == null || computer == null) {
            return;
        }

        if (computerDTO.getType() != null) {
            computer.setType(computerDTO.getType());
        }

        if (computerDTO.getMaker() != null) {
            computer.setMaker(computerDTO.getMaker());
        }

        if (computerDTO.getModel() != null) {
            computer.setModel(computerDTO.getModel());
        }

        if (computerDTO.getLanguage() != null) {
            computer.setLanguage(computerDTO.getLanguage());
        }

        if (computerDTO.getColors() != null && computerDTO.getColors().getColor() != null) {
            computer.setColors(computerDTO.getColors().getColor());
        }
    }
}
