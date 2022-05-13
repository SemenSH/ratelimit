package ratelimiter.model;

import ratelimiter.validation.Validation;

import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class TimeFrame {
    private final ChronoUnit chronoUnit;
    private final long amount;

    public ChronoUnit getChronoUnit() {
        return chronoUnit;
    }

    public long getAmount() {
        return amount;
    }

    public TimeFrame(long amount, ChronoUnit chronoUnit) {
        Validation.requirePositive(amount, "Amount of time cannot be negative");
        this.amount = Objects.requireNonNull(amount, "Amount of time cannot be null");
        this.chronoUnit = Objects.requireNonNull(chronoUnit, "chronoUnit of time cannot be null");
    }
}
