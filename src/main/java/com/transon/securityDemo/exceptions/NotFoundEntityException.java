package com.transon.securityDemo.exceptions;

public class NotFoundEntityException extends RuntimeException {
    private Long id;
    private String entityName;

    public NotFoundEntityException(Long id, String entityName){
        this.id = id;
        this.entityName = entityName;
    }

    @Override
    public String getMessage() {
        String mess = "not found entity " + entityName + " with id: " + id + "!!!";
        return mess;
    }
}
