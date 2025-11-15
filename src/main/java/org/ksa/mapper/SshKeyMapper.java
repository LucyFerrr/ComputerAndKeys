package org.ksa.mapper;

import org.ksa.dto.ComputerDTO;
import org.ksa.dto.SshKeyRequestDTO;
import org.ksa.dto.SshKeyResponseDTO;
import org.ksa.entity.Computer;
import org.ksa.entity.SshKey;
import org.springframework.stereotype.Component;

@Component
public class SshKeyMapper {

   public static SshKeyResponseDTO mapToResponseDto (SshKey sshKey) {
       if (sshKey == null) return null;

       return SshKeyResponseDTO.builder()
               .id(sshKey.getId())
               .type(sshKey.getType())
               .publicKey(sshKey.getPublicKey())
               .comment(sshKey.getComment())
               .server(sshKey.getServerName())
               .user(sshKey.getUserName())
               .build();
   }

   public static SshKey mapToSshKey(SshKeyRequestDTO.SshKeyDTO dto, String serverName, String userName) {
       if (dto == null) return null;

       return SshKey.builder()
               .serverName(serverName)
               .userName(userName)
               .type(dto.getType())
               .publicKey(dto.getPublicKey())
               .comment(dto.getComment())
               .build();
   }

    // Update entity with DTO data
    public static void updateEntityFromDTO(SshKeyRequestDTO.SshKeyDTO sshKeyDto, SshKey sshKey) {
        if (sshKeyDto == null || sshKey == null) return;

        if (sshKeyDto.getType() != null) sshKey.setType(sshKeyDto.getType());

        if (sshKeyDto.getPublicKey() != null) sshKey.setPublicKey(sshKeyDto.getPublicKey());

        if (sshKeyDto.getComment() != null) sshKey.setComment(sshKeyDto.getComment());
    }
}
