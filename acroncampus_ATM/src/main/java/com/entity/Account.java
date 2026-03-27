package com.entity;

import java.util.ArrayList;
import java.util.List;

public class Account {
    // 계좌번호
    private String accountNo;
    // 예금주명
    private String ownerName;
    // 비밀번호
    private String password;
    // 현재 잔액
    private long balance;
    // 비밀번호 오류 횟수
    private int passwordErrorCount;
    // 계좌 잠금 상태
    private boolean isLocked;
    // 계좌별 거래 내역
    private final List<TransactionLog> accountRecord = new ArrayList<>(); //영석이 만듦
    // 로그인 상태
    private boolean isLogined;

    // 계좌 생성자
    public Account(String accountNo, String ownerName, String password, long balance) {
        this.accountNo = accountNo;
        this.ownerName = ownerName;
        this.password = password;
        this.balance = balance;
        this.passwordErrorCount = 0;
        this.isLocked = false;
        this.isLogined = false;
    }

    // 객체 상태 확인용
    @Override
    public String toString() {
        return "Account{" +
                "accountNo='" + accountNo + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                ", passwordErrorCount=" + passwordErrorCount +
                ", isLocked=" + isLocked +
                ", isLogined=" + isLogined +
                '}';
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public int getPasswordErrorCount() {
        return passwordErrorCount;
    }

    // 비밀번호 오류 횟수 1 증가
    public void increasePasswordErrorCount() {
        this.passwordErrorCount++;
    }

    // 비밀번호 오류 횟수 초기화
    public void resetPasswordErrorCount() {
        this.passwordErrorCount = 0;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public boolean isLogined() {
        return isLogined;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public List<TransactionLog> getAccountRecord() { //영석이 만듦
        return accountRecord;
    }

    // 계좌 거래 내역에 로그 1건 추가
    public void addTransactionLog(TransactionLog transactionLog) { //영석이 만듦
        this.accountRecord.add(transactionLog);
    }

    // 로그인 상태 변경
    public void setLogined(boolean logined) {
        isLogined = logined;
    }
}
