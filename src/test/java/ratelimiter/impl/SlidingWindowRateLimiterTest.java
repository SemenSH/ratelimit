package ratelimiter.impl;

import org.junit.jupiter.api.Test;
import ratelimiter.RateLimiter;
import ratelimiter.model.Request;
import ratelimiter.model.TimeFrame;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

import static java.time.LocalDateTime.of;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class SlidingWindowRateLimiterTest {

    public static final String USER_ID = "1";
    private RateLimiter rateLimiter;

    @Test
    void should_reject_request_if_more_than_limit() {
        //given/init
        var time = of(2000, 1, 1, 1, 1);
        var userIdToRequest = createToMockRequests(time, USER_ID);
        var limit = 2;
        var timeFrame = new TimeFrame(1L, ChronoUnit.MINUTES);
        var request = new Request(time, USER_ID);
        rateLimiter = new SlidingWindowRateLimiter(limit, timeFrame, userIdToRequest, () -> time);

        //when
        boolean hit = rateLimiter.hit(request);

        //then
        assertFalse(hit);
    }
    @Test
    void should_allow_request_if_less_than_limit() {
        //given/init
        var time = of(2000, 1, 1, 1, 1);
        var userIdToRequest = createToMockRequests(time, USER_ID);
        var limit = 3;
        var timeFrame = new TimeFrame( 1L, ChronoUnit.MINUTES);
        var request = new Request(time, USER_ID);
        rateLimiter = new SlidingWindowRateLimiter(limit, timeFrame, userIdToRequest, () -> time);

        //when
        boolean hit = rateLimiter.hit(request);

        //then
        assertTrue(hit, "Expected limit was less then count of requests. but limiter rejected request");
    }


    private HashMap<String, UserRequest> createToMockRequests(LocalDateTime time, String userId) {
        var userIdToRequest = new HashMap<String, UserRequest>();
        UserRequest userRequest = new UserRequest();
        userRequest.add(time.plusSeconds(1L));
        userRequest.add(time.plusSeconds(2L));
        userIdToRequest.put(userId, userRequest);
        return userIdToRequest;
    }
}