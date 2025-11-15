package org.ksa.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.ksa.dto.SshKeyRequestDTO;
import org.ksa.dto.SshKeyResponseDTO;
import org.ksa.service.SshKeyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/{serverType}/{serverName}/authorized_keys")
@AllArgsConstructor
@Validated
public class SshKeyController {

    private final SshKeyService sshKeyService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400")
        }
    )
    public ResponseEntity<SshKeyResponseDTO> addSshKey(@PathVariable String serverType, @PathVariable String serverName, @Valid @RequestBody SshKeyRequestDTO request) {

        SshKeyResponseDTO response = sshKeyService.addSshKey(serverName, serverType, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
