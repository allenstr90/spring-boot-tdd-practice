package aem.example.tdd.ecasastorage.exception;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class ExceptionResponse {
    private final LocalDateTime time;
    private final String message;
    private final String details;

    public ExceptionResponse(String message, String details) {
        this.time = LocalDateTime.now(ZoneId.of("UTC"));
        this.message = message;
        this.details = details;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
