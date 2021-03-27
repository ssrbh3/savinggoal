package com.starling.interview.savingsgoal.util;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoundUpUtil {

    /**
     * Util method to help us calculate the sum of the spare changes.
     */

    public static Long getSpareChangeSum(List<Long> spendingList) {
        return spendingList.stream()
                .map(pence -> pence % 100 == 0 ? 0 : 100 - pence % 100)
                .reduce(0L, Long::sum);
    }

}
