package com.korotkov.currency_api.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Result response")
public class ResultResponse {
    @Schema(description = "The message describing the result of the operation", example = "Currency rates loaded successfully")
    String message;
}
