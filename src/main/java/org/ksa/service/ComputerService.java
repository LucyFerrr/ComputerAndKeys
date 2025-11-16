package org.ksa.service;

import org.ksa.dto.ComputerDTO;

import java.util.List;

/**
 * Service interface for managing {@link org.ksa.entity.Computer} entities.
 */
public interface ComputerService {

    /**
     * Retrieves a computer by its maker and model.
     *
     * @param maker manufacturer of the computer
     * @param model model name of computer
     * @return the matching {@link ComputerDTO}, or {@code null} if not found
     */
    ComputerDTO getComputerByMakerAndModel(String maker, String model);

    /**
     * Retrieves all computer.
     *
     * @return a list of matching {@link ComputerDTO} objects
     */
    List<ComputerDTO> getAllComputers();

    /**
     * Creates a new computer record.
     *
     * @param computerDTO DTO containing computer details
     * @return created {@link ComputerDTO}
     */
    ComputerDTO createComputer(ComputerDTO computerDTO);

    /**
     * Updates an existing computer by maker and model.
     *
     * @param maker       manufacturer of the computer
     * @param model       model name of computer
     * @param computerDTO DTO containing computer details
     * @return updated {@link ComputerDTO}
     */
    ComputerDTO updateComputer(String maker, String model, ComputerDTO computerDTO);

    /**
     * Deletes computer by maker and model.
     *
     * @param maker manufacturer of the computer
     * @param model model name of computer
     */
    void deleteComputer(String maker, String model);
}
