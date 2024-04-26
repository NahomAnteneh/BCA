package bank;

import java.util.UUID;

public class Transaction {
    private String transactionID;
    private int fromAccountNo = 0;
    private int toAccountNo;
    private double amount;
    private String timestamp;
    private String description;


    public Transaction(int fromAccountNo, int toAccountNo, double amount, String description) {
        transactionID = generateTransactionId();
        this.fromAccountNo = fromAccountNo;
        this.toAccountNo = toAccountNo;
        this.amount = amount;
        this.description = description;
    }

    private String generateTransactionId() {
        String prefix = "TXN";
        
        long timestamp = System.currentTimeMillis();
        String randomPart = UUID.randomUUID().toString().substring(0, 2);
        String transactionId = String.format("%s-%d-%s", prefix, timestamp, randomPart);

        return transactionId;
    }

    public String getTransactionId() {
        return transactionID;
    }
}