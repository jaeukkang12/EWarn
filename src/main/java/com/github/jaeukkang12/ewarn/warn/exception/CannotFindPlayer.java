package com.github.jaeukkang12.ewarn.warn.exception;

public class CannotFindPlayer extends RuntimeException {
    private static final String MESSAGE = "플레이어를 찾을 수 없습니다.";

    public CannotFindPlayer() {
        super(MESSAGE);
    }
}
