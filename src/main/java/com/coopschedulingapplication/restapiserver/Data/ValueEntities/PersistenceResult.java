package com.coopschedulingapplication.restapiserver.Data.ValueEntities;

public class PersistenceResult<T> {

    private String error;
    private T result;

    public PersistenceResult(T result, String error){
        this.result = result;
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public T getResult() {
        return result;
    }
}
