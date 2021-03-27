package com.starling.interview.savingsgoal.controller;

import com.starling.interview.savingsgoal.dto.RoundUpResponse;
import com.starling.interview.savingsgoal.service.RoundUpService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@OpenAPIDefinition(info = @Info(title = "Savings Goal Round Up"))
@RequiredArgsConstructor
@RestController
public class RoundUpController {

    private final RoundUpService roundUpService;

    /*
        Authorization Header required to identify the user and their account details.
     */
    @Operation(description = "Method used to calculate and round up spare change for a customer identified by the token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Savings Goals added",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RoundUpResponse.class)))})
            })
    @GetMapping("/round-up")
    public ResponseEntity<List<RoundUpResponse>> roundUp(@RequestHeader("Authorization") String token) {
        final List<RoundUpResponse> roundUpResponses = roundUpService.roundUp(token);
        return new ResponseEntity<>(roundUpResponses, HttpStatus.OK);
    }
}
