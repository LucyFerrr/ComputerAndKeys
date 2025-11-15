package org.ksa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
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
public class ComputerDTO {

    @NotBlank(message = "Type is required")
    @JacksonXmlProperty(localName = "type")
    private String type;

    @NotBlank(message = "Maker is required")
    @JacksonXmlProperty(localName = "maker")
    private String maker;

    @NotBlank(message = "Model is required")
    @JacksonXmlProperty(localName = "model")
    private String model;

    @JacksonXmlProperty(localName = "language")
    private String language;

    @JacksonXmlProperty(localName = "colors")
    @JsonProperty("colors")
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
        private List<String> color;
    }
}
