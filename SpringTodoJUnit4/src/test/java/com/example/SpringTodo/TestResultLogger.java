package com.example.SpringTodo;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class TestResultLogger extends TestWatcher {
    @Override
    protected void succeeded(Description description) {
        log.debug(description.getDisplayName() + " was successful!");
    }

    @Override
    protected void failed(Throwable e, Description description) {
    	log.debug(description.getDisplayName() + " failed with reason: " + e.getMessage());
    }

    // 다른 오버라이드 가능한 메서드들도 있습니다.
    // 예: starting, finished 등
}