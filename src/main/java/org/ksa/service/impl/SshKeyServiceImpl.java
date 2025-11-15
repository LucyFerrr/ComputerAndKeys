package org.ksa.service.impl;

import lombok.AllArgsConstructor;
import org.ksa.dto.SshKeyRequestDTO;
import org.ksa.dto.SshKeyResponseDTO;
import org.ksa.entity.SshKey;
import org.ksa.exception.InvalidSshKeyException;
import org.ksa.exception.SshKeyNotFoundException;
import org.ksa.mapper.SshKeyMapper;
import org.ksa.repository.SshKeyRepository;
import org.ksa.service.SshKeyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link SshKeyService} for managing {@link SshKey} entities.
 * Provides CRUD operations for SSH keys.
 */
@Service
@AllArgsConstructor
@Transactional
public class SshKeyServiceImpl implements SshKeyService {

    private SshKeyRepository sshKeyRepository;

    /**
     * Adds a new SSH key to the specific server.
     * <p>
     * Business rules:
     * <ul>
     *     <li>400 if the key is invalid for its type</li>
     *     <li>403 if the key already exists for the given server</li>
     *     <li>201 with created key if successful</li>
     * </ul>
     *
     * @param serverType type of the server
     * @param serverName name of the server
     * @param request    DTO containing SSH key details
     * @return the created {@link SshKeyResponseDTO}
     * @throws InvalidSshKeyException if thet key is invalid or already exist
     */
    @Override
    public SshKeyResponseDTO addSshKey(String serverType, String serverName, SshKeyRequestDTO request) {
        SshKeyRequestDTO.SshKeyDTO keyDTO = request.getSshKey();

        validateSshKey(keyDTO);

        if (sshKeyRepository.existsByServerTypeAndServerNameAndPublicKey(serverType, serverName, keyDTO.getPublicKey())) {
            throw new InvalidSshKeyException("SSH key already exists");
        }

        SshKey entity = SshKeyMapper.mapToSshKey(keyDTO, serverType, serverName);
        SshKey saved = sshKeyRepository.save(entity);

        return SshKeyMapper.mapToResponseDto(saved);
    }

    /**
     * Retrieves an SSH key by ID
     * <p>
     * Business rules:
     * <ul>
     *     <li>404 if the key does not exist</li>
     *     <li>200 if the key data is found</li>
     * </ul>
     *
     * @param id ID of the SSH key
     * @return the matching {@link SshKeyResponseDTO}
     * @throws SshKeyNotFoundException if the key is not found
     */
    @Override
    public SshKeyResponseDTO getKeyById(Long id) {
        SshKey sshKey = sshKeyRepository.findById(id)
                .orElseThrow(() -> new SshKeyNotFoundException("SSH key not found"));

        return SshKeyMapper.mapToResponseDto(sshKey);
    }

    /**
     * Retrieves all SSH keys associated with specific server.
     *
     * @param serverType name of the server
     * @param serverName type of the server
     * @return a list of {@link SshKeyResponseDTO} objects
     */
    @Override
    public List<SshKeyResponseDTO> getAllKeys(String serverType, String serverName) {
        return sshKeyRepository.findByServerTypeAndServerName(serverType, serverName)
                .stream()
                .map(SshKeyMapper::mapToResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing SSH key by its ID.
     * <p>
     * Business rules:
     * <ul>
     *     <li>404 if the key does not exist</li>
     *     <li>200 if the updated key is successful</li>
     * </ul>
     *
     * @param id               ID of the SSH key
     * @param sshKeyRequestDTO DTO containing SSH key details
     * @return the updated {@link SshKeyResponseDTO}
     * @throws SshKeyNotFoundException if the key is not found
     */
    @Override
    public SshKeyResponseDTO updateSshKey(Long id, SshKeyRequestDTO sshKeyRequestDTO) {
        SshKey sshKey = sshKeyRepository.findById(id)
                .orElseThrow(() -> new SshKeyNotFoundException("SSH key not found"));

        SshKeyRequestDTO.SshKeyDTO sshKeyDTO = sshKeyRequestDTO.getSshKey();

        SshKeyMapper.updateEntityFromDTO(sshKeyDTO, sshKey);
        SshKey updated = sshKeyRepository.save(sshKey);

        return SshKeyMapper.mapToResponseDto(updated);
    }

    /**
     * Deletes an SSH key by its ID.
     * <p>
     * Business rules:
     * <ul>
     *     <li>404 if the key does not exist</li>
     *     <li>204 if deletion is successful</li>
     * </ul>
     *
     * @param id ID of the SSH key to delete
     * @throws SshKeyNotFoundException if the key is not found
     */
    @Override
    public void deleteSshKey(Long id) {
        if (!sshKeyRepository.existsById(id)) {
            throw new SshKeyNotFoundException("SSH key not found");
        }

        sshKeyRepository.deleteById(id);
    }

    /**
     * Validates the SSH key based on its type.
     * <p>
     * Business rules:
     * <ul>
     *     <li>For {@code ssh-rsa}, key must be at least 300 characters</li>
     *     <li>For {@code ssh-ed25519}, key must be between 40 and 100 characters</li>
     * </ul>
     *
     * @param sshKeyDTO SSH key DTO to validate
     * @throws InvalidSshKeyException if thet key fails validation.
     * @implNote This validation is basic and should be improved
     */
    private void validateSshKey(SshKeyRequestDTO.SshKeyDTO sshKeyDTO) {
        String type = sshKeyDTO.getType();
        String publicKey = sshKeyDTO.getPublicKey();

        if ("ssh-rsa".equals(type)) {
            if (publicKey.length() < 300) {
                throw new InvalidSshKeyException("The content of the public key is invalid for the type 'ssh-rsa'");
            }
        }

        if ("ssh-ed25519".equals(type)) {
            if (publicKey.length() < 40 || publicKey.length() > 100) {
                throw new InvalidSshKeyException("The content of the public key is invalid for the type 'ed25519'");
            }
        }
    }
}
