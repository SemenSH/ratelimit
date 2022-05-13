package ratelimiter.validation;

import ratelimiter.exception.ValidtionException;

public class Validation {

    public static long requirePositive(long number, String message) {
        if (number < 0) {
            throw new ValidtionException(message);
        }
        return number;
    }
}
