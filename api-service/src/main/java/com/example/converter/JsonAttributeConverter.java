package com.example.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Converter(autoApply = false)
public class JsonAttributeConverter implements AttributeConverter<Map<String, Object>, String> {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, Object> attribute) {
        if (attribute == null) return null;
        try {
            return mapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to convert map to JSON", e);
        }
    }


    @Override
    public Map<String, Object> convertToEntityAttribute(String dbData) {
        if (dbData == null) return Collections.emptyMap();
        try {
            return mapper.readValue(dbData, Map.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to read JSON to map", e);
        }
    }
}
