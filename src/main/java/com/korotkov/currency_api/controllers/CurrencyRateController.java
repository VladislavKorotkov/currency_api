package com.korotkov.currency_api.controllers;

import com.korotkov.currency_api.responses.CurrencyRateResponse;
import com.korotkov.currency_api.responses.ResultResponse;
import com.korotkov.currency_api.services.CurrencyRateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/currency-rates")
@Tag(name = "Currency API", description = "Endpoints for getting exchange rates")
public class CurrencyRateController {

    private final CurrencyRateService service;

    public CurrencyRateController(CurrencyRateService service) {
        this.service = service;
    }

    @GetMapping("/load")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Load currency rates", responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultResponse.class))),
    })
    public ResultResponse loadRates(@Parameter(description = "Date for which to load rates") @RequestParam String date) {
        ResultResponse res = service.loadRatesByDate(date);
        return res;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get currency rates", responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CurrencyRateResponse.class, type = "array")))
    })
    public List<CurrencyRateResponse> getRates(@Parameter(description = "Date for which to get rates") @RequestParam String date) {
        List<CurrencyRateResponse> rates = service.getRatesByDate(date);
        return rates;
    }

    @GetMapping("/{code}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get currency rate by code", responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CurrencyRateResponse.class)))
    })
    public CurrencyRateResponse getRate(@Parameter(description = "Date for which to get rate") @RequestParam String date,
                                        @Parameter(description = "Currency code") @PathVariable int code) {
        CurrencyRateResponse rate = service.getRateByDateAndCode(date, code);
        return rate;
    }
}
