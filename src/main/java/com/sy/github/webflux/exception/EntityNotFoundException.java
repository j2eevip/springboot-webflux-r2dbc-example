package com.sy.github.webflux.exception;

/**
 * @author Sherlock
 * @since 2021/8/30-21:30
 */
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException() {
        super("Can not found entity in database.");
    }
}
