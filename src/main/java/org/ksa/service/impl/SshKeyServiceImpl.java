package org.ksa.service.impl;

import lombok.AllArgsConstructor;
import org.ksa.dto.SshKeyRequestDTO;
import org.ksa.dto.SshKeyResponseDTO;
import org.ksa.entity.SshKey;
import org.ksa.mapper.SshKeyMapper;
import org.ksa.repository.SshKeyRepository;
import org.ksa.service.SshKeyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class SshKeyServiceImpl implements SshKeyService {

    private SshKeyRepository sshKeyRepository;

    @Override
    public SshKeyResponseDTO addSshKey(String serverName, String userName, SshKeyRequestDTO request) {

        SshKeyRequestDTO.SshKeyDTO keyDTO = request.getSshKey();

        if (sshKeyRepository.existsByServerNameAndUserNameAndPublicKey(serverName, userName, keyDTO.getPublicKey())){
            throw new RuntimeException();
        }

        SshKey entity = SshKeyMapper.mapToSshKey(keyDTO, serverName, userName);
        SshKey saved = sshKeyRepository.save(entity);

        return SshKeyMapper.mapToResponseDto(saved);
    }
}
