package com.coopschedulingapplication.restapiserver.persistence;

class SuccessOrNull<T>{
    T successOrNull(ITry<T> run){
        try {
            return run.iTry();
        }catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }
}

interface ITry<T>{
    T iTry();
}