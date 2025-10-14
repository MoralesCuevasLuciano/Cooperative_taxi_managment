package com.pepotec.cooperative_taxi_managment.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    private Long memberId;
    private String resourceName;
    
    public ResourceNotFoundException(Long memberId, String resourceName) {
        super(memberId != null 
            ? String.format("El %s con ID %d no existe", resourceName, memberId)
            : String.format("%s no encontrado", resourceName));
        this.memberId = memberId;
        this.resourceName = resourceName;
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getResourceName() {
        return resourceName;
    }
}
