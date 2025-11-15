package org.ksa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class SshKeyResponseDTO {

    private Long id;

    private String type;

    @JsonProperty("public")
    private String publicKey;

    private String comment;

    private String serverType;

    private String serverName;
}
