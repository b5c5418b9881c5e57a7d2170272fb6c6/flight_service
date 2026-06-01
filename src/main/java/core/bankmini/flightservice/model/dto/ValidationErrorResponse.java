package core.bankmini.flightservice.model.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
public record ValidationErrorResponse(
        LocalDateTime timestamp,
        Integer status,
        String title,
        String path,
        Map<String, String> errors
) {
}