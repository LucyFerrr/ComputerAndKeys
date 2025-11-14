package org.ksa.service;

import org.ksa.dto.ComputerDTO;

public interface ComputerService {
    ComputerDTO getComputerByMakerAndModel(String maker, String model);
}
