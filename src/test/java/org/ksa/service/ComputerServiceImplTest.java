package org.ksa.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.ksa.dto.ComputerDTO;
import org.ksa.entity.Computer;
import org.ksa.exception.ComputerNotFoundException;
import org.ksa.exception.InvalidMakerException;
import org.ksa.repository.ComputerRepository;
import org.ksa.service.impl.ComputerServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link ComputerServiceImpl}.
 * <p>
 * Verifies CRUD operations and exception handling using mocked {@link ComputerRepository}.
 * Test coverage includes:
 * <ul>
 *     <li>Retrieving computers by maker and model</li>
 *     <li>Creating new computers</li>
 *     <li>Updating existing computers</li>
 *     <li>Deleting computers</li>
 *     <li>Handling invalid inputs and missing records</li>
 * </ul>
 */
@ExtendWith(MockitoExtension.class)
public class ComputerServiceImplTest {

    @Mock
    private ComputerRepository computerRepository;

    @InjectMocks
    private ComputerServiceImpl computerService;

    private Computer testComputer;
    private ComputerDTO testComputerDTO;

    @BeforeEach
    void setUp() {
        testComputer = Computer.builder()
                .id(1L)
                .type("laptop")
                .maker("ASUS")
                .model("X507UA")
                .language("日本語")
                .colors(Arrays.asList("black", "silver"))
                .build();

        testComputerDTO = ComputerDTO.builder()
                .type("laptop")
                .maker("ASUS")
                .model("X507UA")
                .language("日本語")
                .colors(ComputerDTO.ColorsWrapper.builder()
                        .color(Arrays.asList("black", "silver"))
                        .build())
                .build();
    }

    /**
     * Tests successful retrieval of computer by maker and model
     * Verifies that correct DTO is returned and repository is queried once.
     */
    @Test
    void getComputerByMakerAndModel_Success() {
        when(computerRepository.existsByMaker("ASUS")).thenReturn(true);
        when(computerRepository.findByMakerAndModel("ASUS", "X507UA"))
                .thenReturn(Optional.of(testComputer));

        ComputerDTO result = computerService.getComputerByMakerAndModel("ASUS", "X507UA");

        assertNotNull(result);
        assertEquals("ASUS", result.getMaker());
        assertEquals("X507UA", result.getModel());
        verify(computerRepository, times(1)).findByMakerAndModel("ASUS", "X507UA");
    }

    /**
     * Tests behavior when the maker does not exist.
     * Expects {@link ComputerNotFoundException} to be thrown.
     */
    @Test
    void getComputerByMakerAndModel_MakerNotFound_ThrowsException() {
        when(computerRepository.existsByMaker("HP")).thenReturn(false);

        assertThrows(ComputerNotFoundException.class, () -> computerService.getComputerByMakerAndModel("HP", "Victus"));
    }

    /**
     * Tests behavior when the model is missing for an existing maker.
     * Expects {@link InvalidMakerException} to be thrown.
     */
    @Test
    void getComputerByMakerAndModel_ModelMissing_ThrowsInvalidMakerException() {
        when(computerRepository.existsByMaker("ASUS")).thenReturn(true);

        assertThrows(InvalidMakerException.class, () -> computerService.getComputerByMakerAndModel("ASUS", null));
    }

    /**
     * Tests successful retrieval of all computers.
     * Verifies that the returned list contains expected data.
     */
    @Test
    void getAllComputers_Success() {
        List<Computer> computers = Collections.singletonList(testComputer);
        when(computerRepository.findAll()).thenReturn(computers);

        List<ComputerDTO> result = computerService.getAllComputers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ASUS", result.get(0).getMaker());
    }

    /**
     * Tests successful creation of a new computer.
     * Verifies that the repository save method is called.
     */
    @Test
    void createComputer_Success() {
        when(computerRepository.existsByMakerAndModel("ASUS", "X507UA")).thenReturn(false);
        when(computerRepository.save(any(Computer.class))).thenReturn(testComputer);

        ComputerDTO result = computerService.createComputer(testComputerDTO);

        assertNotNull(result);
        assertEquals("ASUS", result.getMaker());
        verify(computerRepository, times(1)).save(any(Computer.class));
    }

    /**
     * Tests creation of a duplicate computer.
     * Expects {@link IllegalArgumentException} to be thrown.
     */
    @Test
    void createComputer_Duplicate_ThrowsException() {
        when(computerRepository.existsByMakerAndModel("ASUS", "X507UA")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> computerService.createComputer(testComputerDTO));
    }

    /**
     * Tests successful update of an existing computer record.
     * Verifies that the repository returns the existing computer and saves the updated entity.
     */
    @Test
    void updateComputer_Success() {
        when(computerRepository.findByMakerAndModel("ASUS", "X507UA")).thenReturn(Optional.of(testComputer));
        when(computerRepository.save(any(Computer.class))).thenReturn(testComputer);

        ComputerDTO result = computerService.updateComputer("ASUS", "X507UA", testComputerDTO);

        assertNotNull(result);
        verify(computerRepository, times(1)).save(any(Computer.class));
    }

    /**
     * Tests update behavior when the target computer is not found.
     * Expects {@link ComputerNotFoundException} to be thrown when no matching record exists.
     */
    @Test
    void updateComputer_NotFound_ThrowsException() {
        when(computerRepository.findByMakerAndModel("ASUS", "X507UA")).thenReturn(Optional.empty());

        assertThrows(ComputerNotFoundException.class, () -> computerService.updateComputer("ASUS", "X507UA", testComputerDTO));
    }

    /**
     * Tests successful deletion of an existing computer record.
     * Verifies that the repository finds the computer and deletes it.
     */
    @Test
    void deleteComputer_Success() {
        when(computerRepository.findByMakerAndModel("ASUS", "X507UA")).thenReturn(Optional.of(testComputer));

        computerService.deleteComputer("ASUS", "X507UA");

        verify(computerRepository, times(1)).delete(testComputer);
    }

    /**
     * Tests deletion behavior when the target computer is not found.
     * Expects {@link ComputerNotFoundException} to be thrown when no matching record exists.
     */
    @Test
    void deleteComputer_NotFound_ThrowsException() {
        when(computerRepository.findByMakerAndModel("ASUS", "X507UA")).thenReturn(Optional.empty());

        assertThrows(ComputerNotFoundException.class, () -> computerService.deleteComputer("ASUS", "X507UA"));
    }
}
