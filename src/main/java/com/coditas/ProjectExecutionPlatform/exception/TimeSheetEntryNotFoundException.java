package com.coditas.ProjectExecutionPlatform.exception;

public class TimeSheetEntryNotFoundException extends RuntimeException {
    public TimeSheetEntryNotFoundException(String message) {
        super(message);
    }
}
