package com.github.jaeukkang12.ewarn.warn.exception;

public class WarningCannotBeMinus extends RuntimeException {
    private static final String MESSAGE = "경고는 음수가 될 수 없습니다.";

    public WarningCannotBeMinus() {
        super(MESSAGE);
    }
}
