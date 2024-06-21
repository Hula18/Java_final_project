package org.example.java_final_project.Model;

public class Transaction {
    private final String date;
    private final String from;
    private final String to;
    private final String description;
    private final String amount;
    private final boolean isIncoming;

    public Transaction(String date, String from, String to, String description, String amount, boolean isIncoming) {
        this.date = date;
        this.from = from;
        this.to = to;
        this.description = description;
        this.amount = amount;
        this.isIncoming = isIncoming;
    }

    public String getDate() {
        return date;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getDescription() {
        return description;
    }

    public String getAmount() {
        return amount;
    }

    public boolean isIncoming() {
        return isIncoming;
    }
}
