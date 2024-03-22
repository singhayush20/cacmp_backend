package com.ayushsingh.cacmp_backend.util.exceptionUtil;

public class DuplicateVoteException extends RuntimeException {

    public DuplicateVoteException (String message) {
        super(message);
    }
}
