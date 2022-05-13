package ratelimiter.impl;

import java.time.LocalDateTime;

/**
 * User local date machine time
 */
public class SystemDateService implements DateTimeService {

    @Override
    public LocalDateTime currentTime() {
        return LocalDateTime.now();
    }
}
