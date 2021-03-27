package com.starling.interview.savingsgoal.adapter;

import com.fasterxml.jackson.databind.JsonNode;
import com.starling.interview.savingsgoal.config.AppConfig;
import com.starling.interview.savingsgoal.dto.Accounts;
import com.starling.interview.savingsgoal.dto.FeedItems;
import com.starling.interview.savingsgoal.dto.SavingsGoal;
import com.starling.interview.savingsgoal.dto.SavingsGoalTopUp;
import com.starling.interview.savingsgoal.dto.SavingsGoalsList;
import com.starling.interview.savingsgoal.exception.RoundUpException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

/**
 * Adapter class for starling apis
 */
@RequiredArgsConstructor
@Component
public class StarlingAdapterImpl implements StarlingAdapter {

    public static final String ACCOUNT = "account";
    private final AppConfig appConfig;
    private final RestTemplate restTemplate;

    private HttpHeaders getHeader(String token) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.set("Authorization", token);
        httpHeaders.set("User-Agent", "Saurabh Sharma");
        return httpHeaders;
    }

    /**
     * Get all the accounts for a customer. ASSUMPTION - an user can have more than one account e.g. GBP/EUR and Savings , current etc.
     * Throw exception if account doesn't exist
     */

    @Override
    public Accounts getAccount(String token) {

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(appConfig.getBaseUrl()).path("/accounts");
        final Accounts accounts = restTemplate.exchange(uriComponentsBuilder.toUriString(), HttpMethod.GET, new HttpEntity<>(getHeader(token)), Accounts.class).getBody();
        if (!Objects.isNull(accounts) && !ObjectUtils.isEmpty(accounts.getAccounts())) {
            return accounts;
        } else
            throw new RoundUpException("No account available for this customer");
    }

    /**
     * Get account feed per account , throw exception if Feed doesn't exist
     */

    @Override
    public FeedItems getTransactionFeed(String token, String accountID, String category) {

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(appConfig.getBaseUrl())
                .path("/feed")
                .pathSegment(ACCOUNT, accountID)
                .pathSegment("category", category)
                .queryParam("changesSince", OffsetDateTime.now(ZoneOffset.UTC).minusDays(appConfig.getWeek()));
        final FeedItems feedItems = restTemplate.exchange(uriComponentsBuilder.toUriString(), HttpMethod.GET, new HttpEntity<>(getHeader(token)), FeedItems.class).getBody();
        if (!Objects.isNull(feedItems) && !ObjectUtils.isEmpty(feedItems.getFeedItems())) {
            return feedItems;
        } else
            throw new RoundUpException("No Transaction Feed available for associated account/s");
    }

    /**
     * Get savings goal per account , throw exception if Savings goal doesn't exist
     */

    @Override
    public SavingsGoal getSavingsGoal(String token, String accountID) {

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(appConfig.getBaseUrl())
                .pathSegment(ACCOUNT, accountID)
                .path("savings-goals");
        final SavingsGoalsList savingsGoalList = restTemplate.exchange(uriComponentsBuilder.toUriString(), HttpMethod.GET, new HttpEntity<>(getHeader(token)), SavingsGoalsList.class).getBody();
        if (!Objects.isNull(savingsGoalList) && !ObjectUtils.isEmpty(savingsGoalList.getSavingsGoalList())) {
            return savingsGoalList.getSavingsGoalList().get(0); //Assumption : In case of multiple goals per account the first one would be taken, as there there might be ambiguity in picking up goal and there is no customer interaction.
        } else
            throw new RoundUpException("No Savings goal available for associated account/s");
    }

    /**
     * Top up functionality to put spare changes into savings goal
     */

    @Override
    public String topUpSavingsGoal(String token, String accountID, String savingsGoalId, SavingsGoalTopUp savingsGoalTopUp) {

        /*
          if there were 0 spare changes for a particular account transaction feed,
          no calls will be made to starling top up api and simply standard response with 0 amount will be returned,
          not throwing exception from here or other accounts will be impacted as well e.g. when a customer has 2 account where the other
          had spare change available.

          If customer has 2 accounts both accounts will be required to savings goals set up or error will be thrown to the user in response.
           There is a slight business clarity required here - as in case of 2 accounts if 1 account has savings goal that will be added but if the other
           doesn't then error will be thrown for whole transaction where as for one savings goal system has already topped up.
           This could be handled but would require business clarity and I am leaving it open for discussion.
         */
        if (savingsGoalTopUp.getAmount().getMinorUnits() <= 0)
            return "";

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(appConfig.getBaseUrl())
                .pathSegment(ACCOUNT, accountID)
                .pathSegment("savings-goals", savingsGoalId)
                .pathSegment("add-money", UUID.randomUUID().toString());
        HttpEntity<SavingsGoalTopUp> httpEntity = new HttpEntity<>(savingsGoalTopUp, getHeader(token));

        final JsonNode responseNode = restTemplate.exchange(uriComponentsBuilder.toUriString(), HttpMethod.PUT, httpEntity, JsonNode.class).getBody();

        if (!Objects.isNull(responseNode) && !ObjectUtils.isEmpty(responseNode.get("transferUid"))) {
            return responseNode.get("transferUid").asText();
        } else
            throw new RoundUpException("Couldn't top up customer account, id:  " + accountID);

    }

}
