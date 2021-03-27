package com.starling.interview.savingsgoal.service;

import com.starling.interview.savingsgoal.dto.RoundUpResponse;

import java.util.List;

public interface RoundUpService {
    List<RoundUpResponse> roundUp(String token);
}
