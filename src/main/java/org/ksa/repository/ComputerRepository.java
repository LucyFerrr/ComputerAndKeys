package org.ksa.repository;

import org.ksa.entity.Computer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for accessing and managing {@link Computer} entities.
 */
public interface ComputerRepository extends JpaRepository<Computer, Long> {

    /**
     * Find a computer by maker and model.
     * <p>
     * GET /computers/{maker}/{model} endpoint
     *
     * @param maker manufacturer of the computer
     * @param model model name of computer
     * @return an {@link Optional} containing the matching computer.
     */
    Optional<Computer> findByMakerAndModel(String maker, String model);

    /**
     * Check if computer exists.
     *
     * @param maker manufacturer to check
     * @return {@code true} if at least 1 computer exists, otherwise {@code false}
     */
    boolean existsByMaker(String maker);

    /**
     * Checks if computer exists with the given maker and model.
     *
     * @param maker manufacturer to check
     * @param model model name of computer
     * @return {@code true} if matching computer exists, otherwise {@code false}
     */
    boolean existsByMakerAndModel(String maker, String model);
}
