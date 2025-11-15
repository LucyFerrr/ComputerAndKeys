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

@Service
@AllArgsConstructor
@Transactional
public class SshKeyServiceImpl implements SshKeyService {

    private SshKeyRepository sshKeyRepository;

    // POST new SSH key
    @Override
    public SshKeyResponseDTO addSshKey(String serverType, String serverName, SshKeyRequestDTO request) {

        SshKeyRequestDTO.SshKeyDTO keyDTO = request.getSshKey();

        // Validate SSH key
        validateSshKey(keyDTO);

        // Check key if already exists
        if (sshKeyRepository.existsByServerTypeAndServerNameAndPublicKey(serverType, serverName, keyDTO.getPublicKey())){
            throw new InvalidSshKeyException("SSH key already exists");
        }

        // Save key
        SshKey entity = SshKeyMapper.mapToSshKey(keyDTO, serverType, serverName);
        SshKey saved = sshKeyRepository.save(entity);

        return SshKeyMapper.mapToResponseDto(saved);
    }

    // GET specific SSH key
    @Override
    public SshKeyResponseDTO getKeyById(Long id) {
        SshKey sshKey = sshKeyRepository.findById(id)
                .orElseThrow(() -> new SshKeyNotFoundException("SSH key not found"));

        return SshKeyMapper.mapToResponseDto(sshKey);
    }

    // GET all SSH keys
    @Override
    public List<SshKeyResponseDTO> getAllKeys(String serverType, String serverName) {
        return sshKeyRepository.findByServerTypeAndServerName(serverType, serverName)
                .stream()
                .map(SshKeyMapper::mapToResponseDto)
                .collect(Collectors.toList());
    }

    // PUT update existing SSH key
    @Override
    public SshKeyResponseDTO updateSshKey(Long id, SshKeyRequestDTO sshKeyRequestDTO) {
        SshKey sshKey = sshKeyRepository.findById(id)
                .orElseThrow(() -> new SshKeyNotFoundException("SSH key not found"));

        SshKeyRequestDTO.SshKeyDTO sshKeyDTO = sshKeyRequestDTO.getSshKey();

        SshKeyMapper.updateEntityFromDTO(sshKeyDTO, sshKey);

        // Save updated key
        SshKey updated = sshKeyRepository.save(sshKey);

        return SshKeyMapper.mapToResponseDto(updated);
    }

    // DELETE SSH key
    @Override
    public void deleteSshKey(Long id) {
        // Check if key exists
        if(!sshKeyRepository.existsById(id)) {
            throw new SshKeyNotFoundException("SSH key not found");
        }

        sshKeyRepository.deleteById(id);
    }

    // Validation of SSH key (Basic)
    // TODO: Improve SSH key validation.
    private void validateSshKey(SshKeyRequestDTO.SshKeyDTO sshKeyDTO) {
        String type = sshKeyDTO.getType();
        String publicKey = sshKeyDTO.getPublicKey();

        if ("ssh-rsa".equals(type)) {
            if(publicKey.length() < 300) {
                throw new InvalidSshKeyException("The content of the public key is invalid for the type 'ssh-rsa'");
            }
        }

        if("ssh-ed25519".equals(type)) {
            if(publicKey.length() < 40 || publicKey.length() > 100) {
                throw new InvalidSshKeyException("The content of the public key is invalid for the type 'ed25519'");
            }
        }
    }
}
