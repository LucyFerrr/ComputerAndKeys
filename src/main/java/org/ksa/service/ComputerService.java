package org.ksa.service;

import org.ksa.dto.ComputerDTO;

public interface ComputerService {
    ComputerDTO getComputerByMakerAndModel(String maker, String model);

    ComputerDTO createComputer(ComputerDTO computerDTO);

    ComputerDTO updateComputer(String maker, String model, ComputerDTO computerDTO);

    void deleteComputer(String maker, String model);
}
