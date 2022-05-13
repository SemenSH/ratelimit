package ratelimiter;

import ratelimiter.model.Request;
import ratelimiter.model.TimeFrame;

import java.util.Objects;

public abstract class RateLimiter {
    protected final int limit;
    protected final TimeFrame timeFrame;

    /**
     *
     * @param limit  non negative
     * @param timeFrame time frame not null
     */
    protected RateLimiter(int limit, TimeFrame timeFrame) {
        this.limit = limit;
        this.timeFrame = Objects.requireNonNull(timeFrame);
    }

    public abstract boolean hit(Request request);
}
