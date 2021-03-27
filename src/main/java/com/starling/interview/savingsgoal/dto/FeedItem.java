package com.starling.interview.savingsgoal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeedItem {
    private String categoryUid;
    private String direction;
    private String status;
    private String counterPartyUid;
    private CurrencyAndAmount amount;

}

