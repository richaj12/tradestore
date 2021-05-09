package com.ric.tradestore.exception;

public class TradeException extends RuntimeException {

    private final String id;

    public TradeException(final String id) {
        super("Trade is invalid- " + id);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
