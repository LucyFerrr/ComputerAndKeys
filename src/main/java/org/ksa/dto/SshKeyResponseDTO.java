package org.ksa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for SSH key record
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "SSH key response")
public class SshKeyResponseDTO {

    @Schema(description = "Key ID")
    private Long id;

    @Schema(description = "SSH key type", example = "ssh-ed25519")
    private String type;

    @JsonProperty("public")
    @Schema(description = "Public key content")
    private String publicKey;

    @Schema(description = "Key comment", example = "happy@isr")
    private String comment;

    @Schema(description = "Server type", example = "build-server")
    private String serverType;

    @Schema(description = "Server name", example = "jenkins")
    private String serverName;
}
