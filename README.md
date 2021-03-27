# Savings Goal Round Up

### Building/Running the application

1. Clone/Download the source code.
2. Go to root directory ```/savingsgoal```
3. Execute following command from command line ``` mvn spring-boot:run```.That should both build and run the app.
4. Point 3 should enable URL http://localhost:8080, and the operation should be available at ```/round-up```
5. Set the Authorization header token and start using the round up service.
6. I have also attached the swagger file (api-docs.yaml) in ```/resources``` for reference.

### Manual Test

###### Prerequisite

      Make sure the customer (Used in APi call) has a valid token , account/s , transaction feed and Savings Goal setup.

1. Perform a ```Get``` call on ```http://localhost:8080/round-up``` after building and running the application. Pass the
   bearer access token from any rest client.
2. The app will then find the account/s associated with the token, next the app will find all the feeds available to
   account/s round them up and save it to the respective account goal. A response list containing details of top up will
   be sent back to the caller.
3. If there are any missing details in point 2 for cases like customer doesn't have an account or there is no feed
   available for the requested time frame (A week, as asked in test) or there are no saving goals, an error will be
   thrown to indicate exact problem (more details in StarlingAdapterImpl class comments )

### Tech Stack

    1. JDK 15
    2. SpringBoot 2.4.0
    3. Junit5 BDD Mockito 
    4. Jacoco
    5. Lombok
    6. Springdoc-openapi

### Assumptions

1. A user can have more than one account e.g. Savings , current and EUR/GBP etc.
2. In case of multiple goals per account the first one would be taken, as there might be ambiguity in picking up goal
   and there is no customer interaction.
3. Multiple calls to roundup api with same token where no change in account feed has happened will have no effect on
   saving goal account. A logic in place to exclude goal savings transfers (Direction OUT) from being calculated as
   transaction feeds and round up in case multiple invocations are made subsequently. As they are not spending but a txn
   towards goal.

### Run Test Cases

1. Go to root directory ```/savingsgoal```
2. execute ```mvn test``` 


