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

/**
 * REST controller for managing SSH key records.
 * CRUD operations via JSON payloads.
 */
@RestController
@RequestMapping("/{serverType}/{serverName}/authorized_keys")
@AllArgsConstructor
@Validated
public class SshKeyController {

    private final SshKeyService sshKeyService;

    /**
     * Adds a new SSH key for specific server.
     * <p>
     * POST /{server-type}/{server-name}/authorized_keys
     *
     * @param serverType type of the server
     * @param serverName name of the server
     * @param request    SSH key request payload
     * @return the created {@link SshKeyResponseDTO}
     */
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400")
    }
    )
    public ResponseEntity<SshKeyResponseDTO> addSshKey(@PathVariable String serverType, @PathVariable String serverName, @Valid @RequestBody SshKeyRequestDTO request) {
        SshKeyResponseDTO response = sshKeyService.addSshKey(serverType, serverName, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Retrieves specific SSH key by ID.
     * <p>
     * GET /{server-type}/{server-name}/authorized_keys/{id}
     *
     * @param serverType type of the server
     * @param serverName name of the server
     * @param id         ID of the SSH key
     * @return the matching {@link SshKeyResponseDTO}
     */
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404")
    })
    public ResponseEntity<SshKeyResponseDTO> getSshKeyById(@PathVariable String serverType, @PathVariable String serverName, @PathVariable Long id) {
        SshKeyResponseDTO sshKeyResponseDTO = sshKeyService.getKeyById(id);
        return ResponseEntity.ok(sshKeyResponseDTO);
    }

    /**
     * Retrieves all SSH keys for the specific server.
     * <p>
     * GET /{server-type}/{server-name}/authorized_keys
     *
     * @param serverType type of the server
     * @param serverName name of the server
     * @return list of {@link SshKeyResponseDTO}
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    public ResponseEntity<List<SshKeyResponseDTO>> getAllSshKeys(@PathVariable String serverType, @PathVariable String serverName) {
        List<SshKeyResponseDTO> keys = sshKeyService.getAllKeys(serverType, serverName);
        return ResponseEntity.ok(keys);
    }

    /**
     * Updates an existing SSH key by ID for specific  server.
     * <p>
     * PUT /{server-type}/{server-name}/authorized_keys/{id}
     *
     * @param serverType       type of the server
     * @param serverName       name of the server
     * @param id               ID of the SSH key to update
     * @param sshKeyRequestDTO updated SSH key data
     * @return the updated {@link SshKeyResponseDTO}
     */
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

    /**
     * Deletes an SSH key by ID for specific server.
     * <p>
     * DELETE /{server-type}/{server-name}/authorized_keys/{id}
     *
     * @param serverType type of the server
     * @param serverName name of the server
     * @param id         ID of the SSH key to update
     * @return HTTP 204 No Content if deletion is successful
     */
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
