package org.ksa.repository;

import org.ksa.entity.Computer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComputerRepository extends JpaRepository<Computer, Long> {

    Optional<Computer> findByMakerAndModel(String maker, String model);

    boolean existsByMaker(String maker);

    boolean existsByMakerAndModel(String maker, String model);
}
