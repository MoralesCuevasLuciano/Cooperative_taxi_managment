package com.pepotec.cooperative_taxi_managment.exceptions;

public class MemberAlreadyInactiveException extends RuntimeException {
    private Long memberId;
    
    public MemberAlreadyInactiveException(Long memberId) {
        super("El miembro con ID " + memberId + " ya está inactivo");
        this.memberId = memberId;
    }
    
    public Long getMemberId() {
        return memberId;
    }
    
    public MemberAlreadyInactiveException(String message) {
        super(message);
    }
    

}
