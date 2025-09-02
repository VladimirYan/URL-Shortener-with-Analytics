package com.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;

import java.time.OffsetDateTime;
import java.util.Map;

public class ApiErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    public ApiErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping("/error")
    public ResponseEntity<Map<String, Object>> handleError(HttpServletRequest request) {
        ServletWebRequest sw = new ServletWebRequest(request);
        Map<String, Object> attrs = errorAttributes.getErrorAttributes(sw, ErrorAttributeOptions.of(
                ErrorAttributeOptions.Include.MESSAGE,
                ErrorAttributeOptions.Include.EXCEPTION,
                ErrorAttributeOptions.Include.BINDING_ERRORS
        ));

        attrs.putIfAbsent("timestamp", OffsetDateTime.now().toString());
        Object statusObj = attrs.get("status");
        int status = 500;
        if (statusObj instanceof Integer) status = (Integer) statusObj;
        else if (statusObj instanceof String) {
            try { status = Integer.parseInt((String) statusObj); } catch (NumberFormatException ignored) {}
        }

        return ResponseEntity.status(status).body(attrs);
    }
}
