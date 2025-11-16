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

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link ComputerService} for managing {@link Computer} entities.
 * Provides CRUD operations for computers.
 */
@Service
@AllArgsConstructor
@Transactional
public class ComputerServiceImpl implements ComputerService {

    private ComputerRepository computerRepository;

    /**
     * Retrieves computer by maker and model.
     * <p>
     * Business rules:
     * <ul>
     *     <li>404 if maker doesn't exist</li>
     *     <li>403 if maker exists but model is missing</li>
     *     <li>200 if data is found</li>
     * </ul>
     *
     * @param maker manufacturer of the computer
     * @param model model name of computer
     * @return the matching {@link ComputerDTO}
     * @throws InvalidMakerException     if maker exists but model is missing
     * @throws ComputerNotFoundException if maker doesn't exist
     */
    @Override
    public ComputerDTO getComputerByMakerAndModel(String maker, String model) {
        if (model == null || model.trim().isEmpty() || model.equals("/")) {
            if (computerRepository.existsByMaker(maker)) {
                throw new InvalidMakerException("Model parameter required");
            } else {
                throw new ComputerNotFoundException("Maker '" + maker + "' not found");
            }
        }

        if (!computerRepository.existsByMaker(maker)) {
            throw new ComputerNotFoundException("Maker '" + maker + "' not found");
        }

        Computer computer = computerRepository.findByMakerAndModel(maker, model)
                .orElseThrow(() -> {
                    return new ComputerNotFoundException("Computer not found for maker '" + maker + "' and model '" + model + "'");
                });

        return ComputerMapper.mapToComputerDto(computer);
    }

    @Override
    public List<ComputerDTO> getAllComputers() {
        return computerRepository.findAll()
                .stream()
                .map(ComputerMapper::mapToComputerDto)
                .collect(Collectors.toList());
    }

    /**
     * Creates new computer record.
     * <p>
     * Business rules:
     * <ul>
     *     <li>400 if computer with same maker and model already exists</li>
     *     <li>201 if created data is successful</li>
     * </ul>
     *
     * @param computerDTO DTO containing computer details
     * @return the created {@link ComputerDTO}
     * @throws IllegalArgumentException if computer with same maker and model already exists
     */
    @Override
    public ComputerDTO createComputer(ComputerDTO computerDTO) {
        if (computerRepository.existsByMakerAndModel(computerDTO.getMaker(), computerDTO.getModel())) {
            throw new IllegalArgumentException("Computer already exists");
        }

        Computer computer = ComputerMapper.mapToComputer(computerDTO);
        Computer saved = computerRepository.save(computer);

        return ComputerMapper.mapToComputerDto(saved);
    }

    /**
     * Updates an existing computer by maker and model.
     * <p>
     * Business rules:
     * <ul>
     *     <li>404 if computer does not exist</li>
     *     <li>200 if updated data is successful</li>
     * </ul>
     *
     * @param maker       manufacturer of the computer
     * @param model       model name of computer
     * @param computerDTO DTO containing computer details
     * @return the updated {@link ComputerDTO}
     * @throws ComputerNotFoundException if computer does not exist
     */
    @Override
    public ComputerDTO updateComputer(String maker, String model, ComputerDTO computerDTO) {
        Computer computer = computerRepository.findByMakerAndModel(maker, model)
                .orElseThrow(() -> new ComputerNotFoundException("Computer not found"));

        ComputerMapper.updateEntityFromDTO(computerDTO, computer);
        Computer updated = computerRepository.save(computer);

        return ComputerMapper.mapToComputerDto(updated);
    }

    /**
     * Deletes a computer identified by maker and model.
     * <p>
     * Business rules:
     * <ul>
     *     <li>404 if computer does not exist</li>
     *     <li>204 if deletion is successful</li>
     * </ul>
     *
     * @param maker manufacturer of the computer
     * @param model model name of computer
     * @throws ComputerNotFoundException if the computer does not exist
     */
    @Override
    public void deleteComputer(String maker, String model) {
        Computer computer = computerRepository.findByMakerAndModel(maker, model)
                .orElseThrow(() -> new ComputerNotFoundException("Computer not found"));

        computerRepository.delete(computer);
    }
}
