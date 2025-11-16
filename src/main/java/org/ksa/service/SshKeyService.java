package org.ksa.service;

import org.ksa.dto.SshKeyRequestDTO;
import org.ksa.dto.SshKeyResponseDTO;
import org.ksa.entity.SshKey;

import java.util.List;

/**
 * Service interface for managing {@link SshKey} entities.
 */
public interface SshKeyService {

    /**
     * Adds a new SSH key to the specified server.
     *
     * @param serverName       name of the server
     * @param serverType       type of the server
     * @param sshKeyRequestDTO DTO containing SSH key details
     * @return created {@link SshKeyResponseDTO}
     */
    SshKeyResponseDTO addSshKey(String serverName, String serverType, SshKeyRequestDTO sshKeyRequestDTO);

    /**
     * Retrieves an SSH key by ID.
     *
     * @param id ID of the SSH key
     * @return corresponding {@link SshKeyResponseDTO}, or {@code null} if not found
     */
    SshKeyResponseDTO getKeyById(Long id);

    /**
     * Retrieves all SSH keys associated with a specific server.
     *
     * @param serverName name of the server
     * @param serverType type of the server
     * @return a list of matching {@link SshKeyResponseDTO} objects
     */
    List<SshKeyResponseDTO> getAllKeys(String serverName, String serverType);

    /**
     * Updates an existing SSH key by ID.
     *
     * @param id               ID of thet SSH key
     * @param sshKeyRequestDTO DTO containing SSH key details
     * @return the updated {@link SshKeyResponseDTO}
     */
    SshKeyResponseDTO updateSshKey(Long id, SshKeyRequestDTO sshKeyRequestDTO);

    /**
     * Deletes an SSH key by its ID.
     *
     * @param id ID of the SSH key to delete
     */
    void deleteSshKey(Long id);
}
