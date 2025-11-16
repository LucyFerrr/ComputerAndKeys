package org.ksa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.ksa.dto.ComputerDTO;
import org.ksa.service.ComputerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * REST controller for managing computer records.
 * CRUD operations via JSON and XML payloads.
 */
@RestController
@RequestMapping("/computers")
@AllArgsConstructor
@Validated
@Tag(name = "Computers", description = "API for managing computer information")
public class ComputerController {

    private final ComputerService computerService;

    /**
     * Retrieves computer by maker and model.
     * <p>
     * GET /computers/{maker}/{model}
     *
     * @param maker manufacturer of the computer
     * @param model model of the computer
     * @return matching {@link ComputerDTO}
     */
    @GetMapping(
            path = {"/{maker}/{model}", "/{maker}/{model}/", "/{maker}"},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @Operation(
            summary = "Get computer by maker and model",
            description = "Retrieves computer information. Returns JSON or XML based on Accept header."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Computer found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ComputerDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "403", description = "Model parameter required"),
            @ApiResponse(responseCode = "404", description = "Computer or maker not found")
    })
    public ResponseEntity<ComputerDTO> getComputer(
            @Parameter(description = "Computer manufacturer (e.g., ASUS, HP)", required = true)
            @PathVariable String maker,

            @Parameter(description = "Computer model (e.g., X507UA, Victus)")
            @PathVariable(required = false) String model) {
        ComputerDTO computer = computerService.getComputerByMakerAndModel(maker, model);
        return ResponseEntity.ok(computer);
    }

    @GetMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @Operation(
            summary = "Get all computers",
            description = "Retrieves a list of all computer in the system"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of computers",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ComputerDTO.class)
                    )
            )
    })
    public ResponseEntity<List<ComputerDTO>> getAllComputers() {
        List<ComputerDTO> computers = computerService.getAllComputers();
        return ResponseEntity.ok(computers);
    }

    /**
     * Creates a new computer.
     * <p>
     * POST /computers
     *
     * @param computerDTO computer data to create
     * @return the created {@link ComputerDTO}
     */
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @Operation(summary = "Create a new computer", description = "Adds a new computer to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Computer created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<ComputerDTO> createComputer(@Valid @RequestBody ComputerDTO computerDTO) {
        ComputerDTO created = computerService.createComputer(computerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Updates existing computer record by maker and model.
     * <p>
     * PUT /computers/{maker}/{model}
     *
     * @param maker       manufacturer of the computer
     * @param model       model of the computer
     * @param computerDTO updated computer data
     * @return the updated {@link ComputerDTO}
     */
    @PutMapping(
            path = "/{maker}/{model}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @Operation(summary = "Update computer", description = "Updates an existing computer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Computer updated"),
            @ApiResponse(responseCode = "404", description = "Computer not found")
    })
    public ResponseEntity<ComputerDTO> updateComputer(@PathVariable String maker, @PathVariable String model, @Valid @RequestBody ComputerDTO computerDTO) {
        ComputerDTO updated = computerService.updateComputer(maker, model, computerDTO);
        return ResponseEntity.ok(updated);
    }

    /**
     * Deletes a computer record by maker and model.
     * <p>
     * DELETE /computers/{maker}/{model}
     *
     * @param maker manufacturer of the computer
     * @param model model of the computer
     * @return HTTP 204 No Content if deletion is successful
     */
    @DeleteMapping("/{maker}/{model}")
    @Operation(summary = "Delete computer", description = "Removes a computer from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Computer deleted"),
            @ApiResponse(responseCode = "404", description = "Computer not found")
    })
    public ResponseEntity<Void> deleteComputer(@PathVariable String maker, @PathVariable String model) {
        computerService.deleteComputer(maker, model);
        return ResponseEntity.noContent().build();
    }
}
