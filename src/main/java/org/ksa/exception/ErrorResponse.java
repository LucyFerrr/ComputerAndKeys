package org.ksa.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Standard error response structure returned by the API.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Structure for API error response")
public class ErrorResponse {

    @Schema(description = "Error timestamp")
    private LocalDateTime timestamp;

    @Schema(description = "HTTP status code of errors", example = "200")
    private int status;

    @Schema(description = "Short description of HTTP error", example = "Not Found")
    private String error;

    @Schema(description = "Detailed error message")
    private String message;

    @Schema(description = "Map of validation errors")
    private Map<String, String> validationErrors;
}
