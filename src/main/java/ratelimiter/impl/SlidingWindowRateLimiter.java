package ratelimiter.impl;

import ratelimiter.RateLimiter;
import ratelimiter.model.Request;
import ratelimiter.model.TimeFrame;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class SlidingWindowRateLimiter extends RateLimiter {

    private final Map<String, UserRequest> storage;
    private final DateTimeService dateTimeService;


    public SlidingWindowRateLimiter(int limit, TimeFrame timeFrame, DateTimeService dateTimeService) {
        this(limit, timeFrame, new ConcurrentHashMap<>(), dateTimeService);
    }

    SlidingWindowRateLimiter(int limit, TimeFrame timeFrame, Map<String, UserRequest> storage, DateTimeService dateTimeService) {
        super(limit, timeFrame);
        this.storage = storage;
        this.dateTimeService = dateTimeService;
    }

    @Override
    public boolean hit(Request request) {
        Objects.requireNonNull(request);

        String userId = request.getUserId();
        var userRequest = storage.compute(userId, (k, v) -> v == null ? new UserRequest() : v);
        var current = dateTimeService.currentTime();
        synchronized (userRequest) {
        userRequest.removeExpiredRequests(current, timeFrame);
        userRequest.add(request.getTime());
        return userRequest.size() <= limit;
        }
    }
}
