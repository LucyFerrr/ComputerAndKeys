package org.ksa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String server;

    private String user;
}
