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

    @Override
    public ComputerDTO getComputerByMakerAndModel(String maker, String model) {

        if(model == null || model.trim().isEmpty() || model.equals("/")) {
            if(computerRepository.existsByMaker(maker)) {
                throw new InvalidMakerException("Model parameter required");
            } else {
                throw new ComputerNotFoundException("Maker '" + maker + "' not found");
            }
        }

        if(!computerRepository.existsByMaker(maker)) {
            throw new ComputerNotFoundException("Maker '" + maker + "' not found");
        }

        Computer computer = computerRepository.findByMakerAndModel(maker, model)
                .orElseThrow(() -> {
                    return new ComputerNotFoundException("Computer not found for maker '" + maker + "' and model '" + model + "'");
                });

        return ComputerMapper.mapToComputerDto(computer);
    }
}
