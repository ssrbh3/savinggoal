package com.starling.interview.savingsgoal.util;

import com.starling.interview.savingsgoal.dto.CurrencyAndAmount;
import com.starling.interview.savingsgoal.dto.FeedItem;
import com.starling.interview.savingsgoal.dto.FeedItems;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("UT. RoundUpUtilTest.")
@ExtendWith(MockitoExtension.class)
class RoundUpUtilTest {

    @DisplayName("GIVEN feedItems, savingsGoalUid WHEN getSpareChangeSum THEN return spareChangeSum")
    @Test
    void testGetSpareChangeSum() {

        //given
        Long expectedSum = 80L;
        FeedItems feedItems = TestData.getFeedItems();
        String savingsGoalUid = "test-uid";
        List<Long> spendingList = feedItems.getFeedItems()
                .stream().filter(feedItem -> feedItem.getDirection().equals("OUT") && !feedItem.getCounterPartyUid().equals(savingsGoalUid))
                .map(FeedItem::getAmount)
                .map(CurrencyAndAmount::getMinorUnits).collect(Collectors.toList());

        //when
        Long spareChangeSum = RoundUpUtil.getSpareChangeSum(spendingList);

        //then
        assertEquals(expectedSum, spareChangeSum);

    }


}
