package org.ksa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Request DTO for submitting SSH key.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "SSH key request wrapper")
public class SshKeyRequestDTO {

    @Valid
    @JsonProperty("ssh-key")
    @Schema(description = "SSH key details")
    private SshKeyDTO sshKey;

    /**
     * DTO representing SSH key details
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SshKeyDTO {

        @NotBlank(message = "SSH key type is required")
        @Pattern(regexp = "^(ssh-rsa|ssh-ed25519)$")
        @Schema(description = "SSH key type", example = "ssh-ed25519")
        private String type;

        @NotBlank(message = "Public key is required")
        @JsonProperty("public")
        @Schema(description = "Public key content", example = "AAAAC3NzaC1lZDI1NTE5AAAAIOiKKC7lLUcyvJMo1gjvMr56XvOq814Hhin0OCYFDqT4")
        private String publicKey;

        @Schema(description = "Key comment", example = "happy@isr")
        private String comment;
    }
}
