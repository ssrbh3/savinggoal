package com.starling.interview.savingsgoal.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.starling.interview.savingsgoal.adapter.StarlingAdapterImpl;
import com.starling.interview.savingsgoal.config.AppConfig;
import com.starling.interview.savingsgoal.dto.Accounts;
import com.starling.interview.savingsgoal.dto.FeedItems;
import com.starling.interview.savingsgoal.dto.SavingsGoal;
import com.starling.interview.savingsgoal.dto.SavingsGoalTopUp;
import com.starling.interview.savingsgoal.dto.SavingsGoalsList;
import com.starling.interview.savingsgoal.exception.RoundUpException;
import com.starling.interview.savingsgoal.util.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;


@DisplayName("UT. StarlingAdapterImplTest.")
@ExtendWith(MockitoExtension.class)
class StarlingAdapterImplTest {

    String token;
    String accountID;
    String category;
    String savingGoalId;
    @Mock
    private AppConfig appConfig;
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private StarlingAdapterImpl target;

    @BeforeEach
    public void setUp() {
        token = "TestToken";
        accountID = "8b057e16-6c1f-4099-9547-c35b8dac3c93";
        category = "23a6f510-2e9a-4e59-9c95-4ec5824f4bf4";
        savingGoalId = "sgid";
    }

    @DisplayName("GIVEN token WHEN getAccount THEN get all accounts for customer")
    @Test
    void testGetAccount() {

        //given
        Accounts expectedAccounts = TestData.getAccounts();
        ResponseEntity<Accounts> responseEntity = ResponseEntity.of(Optional.of(expectedAccounts));
        given(appConfig.getBaseUrl()).willReturn("https://dev.starling/api");
        given(restTemplate.exchange(anyString(), any(), any(HttpEntity.class), eq(Accounts.class))).willReturn(responseEntity);

        //when
        Accounts actualAccount = target.getAccount(token);

        //then
        assertEquals(expectedAccounts.getAccounts().get(0).getAccountUid(), actualAccount.getAccounts().get(0).getAccountUid());
        assertEquals(expectedAccounts.getAccounts().get(0).getCurrency(), actualAccount.getAccounts().get(0).getCurrency());
        assertEquals(expectedAccounts.getAccounts().get(0).getDefaultCategory(), actualAccount.getAccounts().get(0).getDefaultCategory());
        then(restTemplate).should().exchange(anyString(), any(), any(HttpEntity.class), eq(Accounts.class));

    }

    @DisplayName("GIVEN token where customer has no account WHEN getAccount THEN exception")
    @Test
    void testGetAccountException() {

        //given
        Accounts accounts = TestData.getEmptyAccounts();
        ResponseEntity<Accounts> responseEntity = ResponseEntity.of(Optional.of(accounts));
        given(appConfig.getBaseUrl()).willReturn("https://dev.starling/api");
        given(restTemplate.exchange(anyString(), any(), any(HttpEntity.class), eq(Accounts.class)))
                .willReturn(responseEntity);

        //when //then
        assertThrows(RoundUpException.class, () -> target.getAccount(token));
    }

    @DisplayName("GIVEN token, accountID, category WHEN getTransactionFeed THEN get account txn feed")
    @Test
    void testGetTransactionFeed() {

        //given
        FeedItems expectedFeedItems = TestData.getFeedItems();
        ResponseEntity<FeedItems> responseEntity = ResponseEntity.of(Optional.of(expectedFeedItems));
        given(appConfig.getBaseUrl()).willReturn("https://dev.starling/api");
        given(appConfig.getWeek()).willReturn(7L);
        given(restTemplate.exchange(anyString(), any(), any(HttpEntity.class), eq(FeedItems.class))).willReturn(responseEntity);

        //when
        FeedItems actualTransactionFeed = target.getTransactionFeed(token, accountID, category);

        //then
        assertEquals(expectedFeedItems.getFeedItems().get(0).getAmount().getMinorUnits(), actualTransactionFeed.getFeedItems().get(0).getAmount().getMinorUnits());
        assertEquals(expectedFeedItems.getFeedItems().get(0).getDirection(), actualTransactionFeed.getFeedItems().get(0).getDirection());
        assertEquals(expectedFeedItems.getFeedItems().get(0).getCategoryUid(), actualTransactionFeed.getFeedItems().get(0).getCategoryUid());
        then(restTemplate).should().exchange(anyString(), any(), any(HttpEntity.class), eq(FeedItems.class));

    }

    @DisplayName("GIVEN customer account has no txn feed account WHEN getTransactionFeed THEN exception")
    @Test
    void testGetTransactionFeedException() {

        //given
        FeedItems feedItems = TestData.getEmptyFeedItems();
        ResponseEntity<FeedItems> responseEntity = ResponseEntity.of(Optional.of(feedItems));
        given(appConfig.getBaseUrl()).willReturn("https://dev.starling/api");
        given(restTemplate.exchange(anyString(), any(), any(HttpEntity.class), eq(FeedItems.class)))
                .willReturn(responseEntity);

        //when //then
        assertThrows(RoundUpException.class, () -> target.getTransactionFeed(token, accountID, category));
    }

    @DisplayName("GIVEN token, accountId WHEN getSavingsGoal THEN get savings goal")
    @Test
    void testGetSavingsGoal() {

        //given
        SavingsGoalsList expectedSavingsGoalList = TestData.getSavingsGoalList();
        ResponseEntity<SavingsGoalsList> responseEntity = ResponseEntity.of(Optional.of(expectedSavingsGoalList));
        given(appConfig.getBaseUrl()).willReturn("https://dev.starling/api");
        given(restTemplate.exchange(anyString(), any(), any(HttpEntity.class), eq(SavingsGoalsList.class))).willReturn(responseEntity);

        //when
        SavingsGoal actualSavingsGoal = target.getSavingsGoal(token, accountID);

        //then
        assertEquals(expectedSavingsGoalList.getSavingsGoalList().get(0).getSavingsGoalUid(), actualSavingsGoal.getSavingsGoalUid());
        assertEquals(expectedSavingsGoalList.getSavingsGoalList().get(0).getName(), actualSavingsGoal.getName());
        then(restTemplate).should().exchange(anyString(), any(), any(HttpEntity.class), eq(SavingsGoalsList.class));

    }

    @DisplayName("GIVEN customer account has no savings goal account WHEN getSavingsGoal THEN exception")
    @Test
    void testGetSavingsGoalException() {

        //given
        SavingsGoalsList expectedSavingsGoalList = TestData.getEmptySavingsGoalList();
        ResponseEntity<SavingsGoalsList> responseEntity = ResponseEntity.of(Optional.of(expectedSavingsGoalList));
        given(appConfig.getBaseUrl()).willReturn("https://dev.starling/api");
        given(restTemplate.exchange(anyString(), any(), any(HttpEntity.class), eq(SavingsGoalsList.class)))
                .willReturn(responseEntity);

        //when //then
        assertThrows(RoundUpException.class, () -> target.getSavingsGoal(token, accountID));
    }

    @DisplayName("GIVEN token, accountId, savingGoalId, savingsGoalTopUp WHEN topUpSavingsGoal THEN return transferUid")
    @Test
    void testTopUpSavingsGoal() {

        //given
        SavingsGoalTopUp savingsGoalTopUp = TestData.getSavingsGoalTopUp();
        ObjectNode jsonNode = JsonNodeFactory.instance.objectNode();
        jsonNode.put("transferUid", "8d822cce-9c37-4daf-94c1-66b959bbf646");
        ResponseEntity<JsonNode> responseEntity = ResponseEntity.of(Optional.of(jsonNode));
        given(appConfig.getBaseUrl()).willReturn("https://dev.starling/api");
        given(restTemplate.exchange(anyString(), any(), any(HttpEntity.class), eq(JsonNode.class))).willReturn(responseEntity);

        //when
        String actualTransferUid = target.topUpSavingsGoal(token, accountID, savingGoalId, savingsGoalTopUp);

        //then
        assertEquals(jsonNode.get("transferUid").asText(), actualTransferUid);
        then(restTemplate).should().exchange(anyString(), any(), any(HttpEntity.class), eq(JsonNode.class));

    }

    @DisplayName("GIVEN 0 amount in savingsGoalTopUp WHEN topUpSavingsGoal THEN return transferUid")
    @Test
    void testTopUpSavingsGoal0Amount() {

        //given
        SavingsGoalTopUp savingsGoalTopUp = TestData.getSavingsGoalTopUp();
        savingsGoalTopUp.getAmount().setMinorUnits(0L);

        //when
        String actualTransferUid = target.topUpSavingsGoal(token, accountID, savingGoalId, savingsGoalTopUp);

        //then
        assertTrue(actualTransferUid.isEmpty());

    }


    @DisplayName("GIVEN savingsGoalTopUp payload but error occurs at Starling end WHEN topUpSavingsGoal THEN exception ")
    @Test
    void testTopUpSavingsGoalException() {

        //given
        SavingsGoalTopUp savingsGoalTopUp = TestData.getSavingsGoalTopUp();
        JsonNode jsonNode = JsonNodeFactory.instance.objectNode();
        ResponseEntity<JsonNode> responseEntity = ResponseEntity.of(Optional.of(jsonNode));
        given(appConfig.getBaseUrl()).willReturn("https://dev.starling/api");
        given(restTemplate.exchange(anyString(), any(), any(HttpEntity.class), eq(JsonNode.class)))
                .willReturn(responseEntity);

        //when //then
        assertThrows(RoundUpException.class, () -> target.topUpSavingsGoal(token, accountID, savingGoalId, savingsGoalTopUp));
    }

}
