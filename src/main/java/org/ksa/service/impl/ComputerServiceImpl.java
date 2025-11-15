package org.ksa.service.impl;

import lombok.AllArgsConstructor;
import org.ksa.dto.ComputerDTO;
import org.ksa.entity.Computer;
import org.ksa.exception.ComputerNotFoundException;
import org.ksa.exception.InvalidMakerException;
import org.ksa.mapper.ComputerMapper;
import org.ksa.repository.ComputerRepository;
import org.ksa.service.ComputerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class ComputerServiceImpl implements ComputerService {

    private ComputerRepository computerRepository;

    //    GET computer maker and model
    @Override
    public ComputerDTO getComputerByMakerAndModel(String maker, String model) {

        // Checking for model
        if(model == null || model.trim().isEmpty() || model.equals("/")) {
            if(computerRepository.existsByMaker(maker)) {
                throw new InvalidMakerException("Model parameter required");
            } else {
                throw new ComputerNotFoundException("Maker '" + maker + "' not found");
            }
        }

        // Checking if maker exist
        if(!computerRepository.existsByMaker(maker)) {
            throw new ComputerNotFoundException("Maker '" + maker + "' not found");
        }

        // Find the specific computer
        Computer computer = computerRepository.findByMakerAndModel(maker, model)
                .orElseThrow(() -> {
                    return new ComputerNotFoundException("Computer not found for maker '" + maker + "' and model '" + model + "'");
                });

        return ComputerMapper.mapToComputerDto(computer);
    }

    // POST create a new computer

    public ComputerDTO createComputer(ComputerDTO computerDTO) {
        // Check if computer already exists
        if(computerRepository.existsByMakerAndModel(computerDTO.getMaker(), computerDTO.getModel())) {
            throw new IllegalArgumentException("Computer already exists");
        }

        Computer computer = ComputerMapper.mapToComputer(computerDTO);
        Computer saved = computerRepository.save(computer);

        return ComputerMapper.mapToComputerDto(saved);
    }

    // PUT update existing computer
    public ComputerDTO updateComputer(String maker, String model, ComputerDTO computerDTO) {
        Computer computer = computerRepository.findByMakerAndModel(maker, model)
                .orElseThrow(() -> new ComputerNotFoundException("Computer not found"));

        ComputerMapper.updateEntityFromDTO(computerDTO, computer);
        Computer updated = computerRepository.save(computer);

        return ComputerMapper.mapToComputerDto(updated);
    }

    // DELETE computer
    public void deleteComputer(String maker, String model) {
        Computer computer = computerRepository.findByMakerAndModel(maker, model)
                .orElseThrow(() -> new ComputerNotFoundException("Computer not found"));

        computerRepository.delete(computer);
    }
}
