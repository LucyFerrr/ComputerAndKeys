package org.ksa.service;

import org.ksa.dto.SshKeyRequestDTO;
import org.ksa.dto.SshKeyResponseDTO;

import java.util.List;

public interface SshKeyService {
    SshKeyResponseDTO addSshKey(String serverName, String serverType, SshKeyRequestDTO sshKeyRequestDTO);

    SshKeyResponseDTO getKeyById(Long id);

    List<SshKeyResponseDTO> getAllKeys(String serverName, String serverType);

    SshKeyResponseDTO updateSshKey(Long id, SshKeyRequestDTO sshKeyRequestDTO);

    void deleteSshKey(Long id);
}
