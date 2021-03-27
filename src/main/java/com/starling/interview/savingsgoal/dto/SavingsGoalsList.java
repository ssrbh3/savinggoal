package com.starling.interview.savingsgoal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SavingsGoalsList {
    private List<SavingsGoal> savingsGoalList;
}
