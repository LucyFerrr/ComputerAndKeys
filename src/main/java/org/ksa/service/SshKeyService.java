package org.ksa.service;

import org.ksa.dto.SshKeyRequestDTO;
import org.ksa.dto.SshKeyResponseDTO;

public interface SshKeyService {
    SshKeyResponseDTO addSshKey(String servername, String userName, SshKeyRequestDTO request);
}
