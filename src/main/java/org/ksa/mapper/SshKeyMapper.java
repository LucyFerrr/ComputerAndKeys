package org.ksa.mapper;

import org.ksa.dto.SshKeyRequestDTO;
import org.ksa.dto.SshKeyResponseDTO;
import org.ksa.entity.SshKey;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting between {@link SshKey} entities and their corresponding DTO.
 */
@Component
public class SshKeyMapper {

    /**
     * Converts a {@link SshKey} entity to a {@link SshKeyResponseDTO}.
     *
     * @param sshKey entity to convert
     * @return the corresponding response DTO, oro {@code null} if input is {@code null}
     */
    public static SshKeyResponseDTO mapToResponseDto(SshKey sshKey) {
        if (sshKey == null) return null;

        return SshKeyResponseDTO.builder()
                .id(sshKey.getId())
                .type(sshKey.getType())
                .publicKey(sshKey.getPublicKey())
                .comment(sshKey.getComment())
                .serverType(sshKey.getServerType())
                .serverName(sshKey.getServerName())
                .build();
    }

    /**
     * Converts a {@link SshKeyRequestDTO.SshKeyDTO} to a {@link SshKey} entity.
     *
     * @param sshKeyDTO  SSH key DTO
     * @param serverType associated server type
     * @param serverName associated server name
     * @return the corresponding entity, or {@code null} if DTO is {@code null}
     */
    public static SshKey mapToSshKey(SshKeyRequestDTO.SshKeyDTO sshKeyDTO, String serverType, String serverName) {
        if (sshKeyDTO == null) return null;

        return SshKey.builder()
                .serverType(serverType)
                .serverName(serverName)
                .type(sshKeyDTO.getType())
                .publicKey(sshKeyDTO.getPublicKey())
                .comment(sshKeyDTO.getComment())
                .build();
    }
    
    /**
     * Updates an existing {@link SshKey} entity with non-null values from a {@link SshKeyRequestDTO.SshKeyDTO}.
     *
     * @param sshKeyDto source DTO
     * @param sshKey    target entity to update
     */
    public static void updateEntityFromDTO(SshKeyRequestDTO.SshKeyDTO sshKeyDto, SshKey sshKey) {
        if (sshKeyDto == null || sshKey == null) {
            return;
        }

        if (sshKeyDto.getType() != null) {
            sshKey.setType(sshKeyDto.getType());
        }

        if (sshKeyDto.getPublicKey() != null) {
            sshKey.setPublicKey(sshKeyDto.getPublicKey());
        }

        if (sshKeyDto.getComment() != null) {
            sshKey.setComment(sshKeyDto.getComment());
        }
    }
}
