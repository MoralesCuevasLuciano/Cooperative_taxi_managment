package com.pepotec.cooperative_taxi_managment.exceptions;

public class DuplicateFieldException extends RuntimeException {
    private String fieldName;
    private String fieldValue;
    private String resourceName;

    public DuplicateFieldException(String message) {
        super(message);
    }

    public DuplicateFieldException(String fieldName, String fieldValue, String resourceName) {
        super(String.format("Ya existe un %s con %s: %s", resourceName, fieldName, fieldValue));
        this.fieldName = fieldName; 
        this.fieldValue = fieldValue;
        this.resourceName = resourceName;
    }

    public String getResourceName() {
        return resourceName;
    }
    
    public String getFieldName() {
        return fieldName;
    }
    
    public String getFieldValue() {
        return fieldValue;
    }
}
