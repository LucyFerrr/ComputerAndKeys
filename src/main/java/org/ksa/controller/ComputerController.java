package org.ksa.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.ksa.dto.ComputerDTO;
import org.ksa.service.ComputerService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/computers")
@AllArgsConstructor
@Validated
public class ComputerController {

    private final ComputerService computerService;

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
}
