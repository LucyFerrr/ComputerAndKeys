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
import java.util.List;

@RestController
@RequestMapping("/{serverType}/{serverName}/authorized_keys")
@AllArgsConstructor
@Validated
public class SshKeyController {

    private final SshKeyService sshKeyService;

    // POST /{server-type}/{server-name}/authorized_keys
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
        SshKeyResponseDTO response = sshKeyService.addSshKey(serverType, serverName, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET /{server-type}/{server-name}/authorized_keys/{id}
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404")
    })
    public ResponseEntity<SshKeyResponseDTO> getSshKeyById(@PathVariable String serverType,@PathVariable String serverName,@PathVariable Long id) {
        SshKeyResponseDTO sshKeyResponseDTO = sshKeyService.getKeyById(id);
        return ResponseEntity.ok(sshKeyResponseDTO);
    }

    // GET /{server-type}/{server-name}/authorized_keys
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    public ResponseEntity<List<SshKeyResponseDTO>> getAllSshKeys(@PathVariable String serverType, @PathVariable String serverName) {
        List<SshKeyResponseDTO> keys = sshKeyService.getAllKeys(serverType, serverName);
        return ResponseEntity.ok(keys);
    }

    // PUT /{server-type}/{server-name}/authorized_keys/{id}
    @PutMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404"),
            @ApiResponse(responseCode = "400")
    })
    public ResponseEntity<SshKeyResponseDTO> updateSshKey(@PathVariable String serverType, @PathVariable String serverName, @PathVariable Long id, @Valid @RequestBody SshKeyRequestDTO sshKeyRequestDTO) {
        SshKeyResponseDTO updated = sshKeyService.updateSshKey(id, sshKeyRequestDTO);
        return ResponseEntity.ok(updated);
    }

    // DELETE /{server-type}/{server-name}/authorized_keys/{id}
    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404")
    })
    public ResponseEntity<Void> deleteSshKey(@PathVariable String serverType, @PathVariable String serverName, @PathVariable Long id) {
        sshKeyService.deleteSshKey(id);
        return ResponseEntity.noContent().build();
    }
}
