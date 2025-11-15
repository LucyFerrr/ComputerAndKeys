package org.ksa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * DTO representing computer entity.
 * Support both JSON and XML formats.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "computer")
@Schema(description = "Computer DTO")
public class ComputerDTO {

    @NotBlank(message = "Type is required")
    @JacksonXmlProperty(localName = "type")
    @Schema(description = "Type of Computer", example = "laptop")
    private String type;

    @NotBlank(message = "Maker is required")
    @JacksonXmlProperty(localName = "maker")
    @Schema(description = "Computer manufacturer", example = "ASUS")
    private String maker;

    @NotBlank(message = "Model is required")
    @JacksonXmlProperty(localName = "model")
    @Schema(description = "Computer model", example = "X507UA")
    private String model;

    @JacksonXmlProperty(localName = "language")
    @Schema(description = "Display language", example = "日本語")
    private String language;

    @JacksonXmlProperty(localName = "colors")
    @JsonProperty("colors")
    @Schema(description = "Available colors")
    private ColorsWrapper colors;

    /**
     * Wrapper class for list of color options.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ColorsWrapper {

        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "color")
        @JsonProperty("color")
        @Schema(description = "List of available colors", example = "[\"black\", \"silver\"]")
        private List<String> color;
    }
}
