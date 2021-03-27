package com.starling.interview.savingsgoal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {
    private String accountUid;
    private String accountType;
    private String defaultCategory;
    private String currency;
}
