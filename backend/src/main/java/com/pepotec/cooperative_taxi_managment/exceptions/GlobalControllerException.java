package com.pepotec.cooperative_taxi_managment.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.OffsetDateTime;



@RestControllerAdvice
public class GlobalControllerException {

    private ProblemDetail createProblemDetail(
        HttpStatus status,      // <- Código HTTP: 404, 400, 500, etc.
        String title,           // <- Título corto: "Miembro no encontrado"
        String details,         // <- Descripción: "El miembro con ID 5 no existe"
        HttpServletRequest request)  // <- Info de la petición HTTP
    {
        // Crea el objeto ProblemDetail con status y details
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, details);
        
        // Agrega el título
        problemDetail.setTitle(title);
        
        // Agrega la URL donde ocurrió el error
        problemDetail.setInstance(URI.create(request.getRequestURI().toString()));
        
        // Agrega la fecha/hora del error
        problemDetail.setProperty("timestamp", OffsetDateTime.now());
        return problemDetail;
    }



    /*
     * Manejador de excepción para ResourceNotFoundException
     * Retorna un ProblemDetail con el status NOT_FOUND, el título "Recurso no encontrado"
     * y el mensaje de la excepción
     * Además, agrega el tipo de recurso y el ID del recurso a la respuesta
     */

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFoundException(
        ResourceNotFoundException ex, 
        HttpServletRequest request) 
    {
        ProblemDetail problemDetail = createProblemDetail(
            HttpStatus.NOT_FOUND, 
            "Recurso no encontrado", 
            ex.getMessage(), 
            request
        );
        problemDetail.setProperty("resourceType", ex.getResourceName());
        problemDetail.setProperty("resourceId", ex.getMemberId());
        return problemDetail;
    }

    /*
     * Manejador de excepción para InvalidDataException
     * Retorna un ProblemDetail con el status BAD_REQUEST, el título "Datos inválidos"
     * y el mensaje de la excepción
     */

    @ExceptionHandler(InvalidDataException.class)
    public ProblemDetail handleInvalidDataException(
        InvalidDataException ex, 
        HttpServletRequest request) 
    {
        return createProblemDetail(
            HttpStatus.BAD_REQUEST, 
            "Datos inválidos", 
            ex.getMessage(), 
            request
        );
    }

    /*
     * Manejador de excepción para DuplicateFieldException
     * Retorna un ProblemDetail con el status BAD_REQUEST, el título "Datos duplicados"
     * y el mensaje de la excepción
     */

    @ExceptionHandler(DuplicateFieldException.class)
    public ProblemDetail handleDuplicateFieldException(
        DuplicateFieldException ex, 
        HttpServletRequest request) 
    {
        ProblemDetail problemDetail = createProblemDetail(
            HttpStatus.CONFLICT, 
            "Campo duplicado", 
            ex.getMessage(), 
            request
        );
        problemDetail.setProperty("fieldName", ex.getFieldName());
        problemDetail.setProperty("fieldValue", ex.getFieldValue());
        return problemDetail;
    }

    /*
     * Manejador de excepción para MemberAlreadyInactiveException
     * Retorna un ProblemDetail con el status BAD_REQUEST, el título "Miembro ya inactivo"
     * y el mensaje de la excepción
     */

    @ExceptionHandler(MemberAlreadyInactiveException.class)
    public ProblemDetail handleMemberAlreadyInactiveException(
        MemberAlreadyInactiveException ex, 
        HttpServletRequest request) 
    {
        ProblemDetail problemDetail = createProblemDetail(
            HttpStatus.CONFLICT, 
            "Miembro ya inactivo", 
            ex.getMessage(), 
            request
        );
        problemDetail.setProperty("resourceId", ex.getMemberId());
        return problemDetail;
    }

    /*
     * Manejador de excepción para Exception
     * Retorna un ProblemDetail con el status INTERNAL_SERVER_ERROR, el título "Error interno del servidor"
     * y el mensaje de la excepción
     */
    
     @ExceptionHandler(Exception.class)
     public ProblemDetail handleException(Exception ex, HttpServletRequest request) {
         return createProblemDetail(
             HttpStatus.INTERNAL_SERVER_ERROR, 
             "Error interno del servidor", 
             "Ocurrio un error inesperado en el servidor: " + ex.getMessage(), 
             request
         );
     }
}

