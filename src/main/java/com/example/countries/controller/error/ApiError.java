package com.example.countries.controller.error;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * {
 * "apiVersion": "2.0",
 * "error": {
 * "code": 404,
 * "message": "File Not Found",
 * "errors": [{
 * "domain": "Calendar",
 * "reason": "ResourceNotFoundException",
 * "message": "File Not Found"
 * }]
 * }
 * }
 */
@Getter
@Setter
public class ApiError {

    private final String apiVersion;
    private final Error error;

    public ApiError(String apiVersion, Error error) {
        this.apiVersion = apiVersion;
        this.error = error;
    }

    public ApiError(String apiVersion,
                    String code,
                    String message,
                    String domain,
                    String reason) {
        this.apiVersion = apiVersion;
        this.error = new Error(
                code,
                message,
                List.of(
                        new ErrorItem(
                                domain,
                                reason,
                                message)));
    }

    public static ApiError fromAttributesMap(String apiVersion, Map<String, Object> attributes) {
        return new ApiError(
                apiVersion,
                ((Integer) attributes.get("status")).toString(),
                ((String) attributes.getOrDefault("error", "No message found")),
                ((String) attributes.getOrDefault("path", "No path found")),
                ((String) attributes.getOrDefault("reason", "No reason found"))
        );
    }

    public Map<String, Object> toAttributesMap() {
        return Map.of(
                "apiVersion", apiVersion,
                "error", error
        );
    }

    private record Error(String code, String message, List<ErrorItem> errors) {
    }

    private record ErrorItem(String domain, String reason, String message) {
    }
}
