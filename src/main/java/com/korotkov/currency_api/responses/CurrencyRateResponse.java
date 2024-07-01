package com.korotkov.currency_api.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyRateResponse {
    private int curId;
    private String date;
    private String curAbbreviation;
    private int curScale;
    private String curName;
    private double curOfficialRate;
}
