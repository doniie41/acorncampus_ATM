package com.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransactionLog {
    // 거래가 발생한 계좌번호
    private final String accountNo;
    // 거래 유형 (입금, 출금, 이체 등)
    private final String transactionType;
    // 거래 금액
    private final long amount;
    // 거래가 끝난 직후 계좌 잔액
    private final long balanceAfter;
    // 거래 발생 시각
    private final LocalDateTime timestamp;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // 로그 객체 생성 시 현재 시각을 함께 저장
    public TransactionLog(String accountNo, String transactionType, long amount, long balanceAfter) {
        this.accountNo = accountNo;
        this.transactionType = transactionType;
        this.amount = amount;
        this.balanceAfter = balanceAfter;

        this.timestamp = LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter);
        //오류 날지 안날지 모름 테스트해봐야됌
    }

    public String getAccountNo() {
        return accountNo;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public long getAmount() {
        return amount;
    }

    public long getBalanceAfter() {
        return balanceAfter;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    // 로그 내용을 바로 확인할 수 있도록 문자열 형태로 반환
    @Override
    public String toString() {
        return "TransactionLog{" +
                "accountNo='" + accountNo + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", amount=" + amount +
                ", balanceAfter=" + balanceAfter +
                ", timestamp=" + timestamp +
                '}';
    }
}
