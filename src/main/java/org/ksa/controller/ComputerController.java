package org.ksa.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.ksa.dto.ComputerDTO;
import org.ksa.service.ComputerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/computers")
@AllArgsConstructor
@Validated
public class ComputerController {

    private final ComputerService computerService;

    // GET /computers/{maker}/{model}
    @GetMapping(
            path = {"/{maker}/{model}", "/{maker}/{model}/", "/{maker}"},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ComputerDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "403"),
            @ApiResponse(responseCode = "404")
    })
    public ResponseEntity<ComputerDTO> getComputer(@PathVariable String maker, @PathVariable(required = false) String model) {
        ComputerDTO computer = computerService.getComputerByMakerAndModel(maker, model);
        return ResponseEntity.ok(computer);
    }

    // POST /computers
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400")
    })
    public ResponseEntity<ComputerDTO> createComputer(@Valid @RequestBody ComputerDTO computerDTO) {
        ComputerDTO created = computerService.createComputer(computerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // PUT /computers/{maker}/{model}
    @PutMapping(
            path = "/{maker}/{model}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404")
    })
    public ResponseEntity<ComputerDTO> updateComputer(@PathVariable String maker, @PathVariable String model, @Valid @RequestBody ComputerDTO computerDTO) {
        ComputerDTO updated = computerService.updateComputer(maker, model, computerDTO);
        return ResponseEntity.ok(updated);
    }

    // DELETE a computer
    @DeleteMapping("/{maker}/{model}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404")
    })
    public ResponseEntity<Void> deleteComputer(@PathVariable String maker, @PathVariable String model) {
        computerService.deleteComputer(maker, model);
        return ResponseEntity.noContent().build();
    }
}
