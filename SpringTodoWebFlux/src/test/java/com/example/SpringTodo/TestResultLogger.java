package com.example.SpringTodo;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class TestResultLogger implements TestWatcher {

    @Override
    public void testSuccessful(ExtensionContext context) {
        log.debug(context.getDisplayName() + " was successful!");
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        log.debug(context.getDisplayName() + " failed with reason: " + cause.getMessage());
    }

}