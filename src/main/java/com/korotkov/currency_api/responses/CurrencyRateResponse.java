package com.korotkov.currency_api.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Currency exchange rate")
public class CurrencyRateResponse {
    @Schema(description = "The unique identifier of the currency", example = "440")
    private int curId;
    @Schema(description = "The date for which the exchange rate is valid", example = "2023-06-30")
    private String date;
    @Schema(description = "The abbreviation of the currency", example = "AUD")
    private String curAbbreviation;
    @Schema(description = "The scale factor of the currency", example = "1")
    private int curScale;
    @Schema(description = "The full name of the currency", example = "Австралийский доллар")
    private String curName;
    @Schema(description = "The official exchange rate", example = "1.0")
    private double curOfficialRate;
}
