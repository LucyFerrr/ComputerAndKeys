package org.ksa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SshKeyRequestDTO {

    @Valid
    @JsonProperty("ssh-key")
    private SshKeyDTO sshKey;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SshKeyDTO {

        @NotBlank(message = "SSH key type is required")
        @Pattern(regexp = "^(ssh-rsa|ssh-ed25519)$")
        private String type;

        @NotBlank(message = "Public key is required")
        @JsonProperty("public")
        private String publicKey;

        private String comment;
    }
}
