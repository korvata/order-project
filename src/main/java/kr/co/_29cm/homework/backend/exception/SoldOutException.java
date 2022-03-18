package kr.co._29cm.homework.backend.exception;

public class SoldOutException extends RuntimeException {

    public SoldOutException(String msg) {
        super(msg);
    }
}
