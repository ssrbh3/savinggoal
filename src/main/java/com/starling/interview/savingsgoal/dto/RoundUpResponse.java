package com.starling.interview.savingsgoal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoundUpResponse {
    private String accountUid;
    private String savingGoalName;
    private CurrencyAndAmount saving;
    private String transferUid;
}
