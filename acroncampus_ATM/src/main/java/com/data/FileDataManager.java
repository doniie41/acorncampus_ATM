package com.data;

import com.entity.Account;
import com.entity.AdminLog;
import com.entity.AtmMachine;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileDataManager {
    private final Gson gson;
    private static final String DATA_DIR = "data";
    private static final String ACCOUNTS_FILE = DATA_DIR + "/accounts.json";
    private static final String ATM_FILE = DATA_DIR + "/atm_machine.json";
    private static final String ADMIN_LOGS_FILE = DATA_DIR + "/admin_logs.json";

    public FileDataManager() {
        // LocalDateTime을 처리할 수 있도록 어댑터를 등록한 Gson 인스턴스 생성
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .setPrettyPrinting() // JSON 파일을 예쁘게 출력
                .create();
        // 데이터 저장을 위한 'data' 폴더 생성
        new File(DATA_DIR).mkdirs();
    }

    // --- 계좌 정보 저장/불러오기 ---
    public void saveAccounts(List<Account> accounts) {
        try (Writer writer = new FileWriter(ACCOUNTS_FILE)) {
            gson.toJson(accounts, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Account> loadAccounts() {
        try (Reader reader = new FileReader(ACCOUNTS_FILE)) {
            Type listType = new TypeToken<ArrayList<Account>>() {}.getType();
            List<Account> accounts = gson.fromJson(reader, listType);
            return accounts != null ? accounts : new ArrayList<>();
        } catch (FileNotFoundException e) {
            return new ArrayList<>(); // 파일이 없으면 빈 리스트 반환
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // --- ATM 기기 정보 저장/불러오기 ---
    public void saveAtmMachine(AtmMachine atmMachine) {
        try (Writer writer = new FileWriter(ATM_FILE)) {
            gson.toJson(atmMachine, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AtmMachine loadAtmMachine() {
        try (Reader reader = new FileReader(ATM_FILE)) {
            AtmMachine atmMachine = gson.fromJson(reader, AtmMachine.class);
            return atmMachine != null ? atmMachine : new AtmMachine(1_000_000_000L); // 파일이 비어있으면 기본값 반환
        } catch (FileNotFoundException e) {
            return new AtmMachine(1_000_000_000L); // 파일이 없으면 기본값 반환
        } catch (IOException e) {
            e.printStackTrace();
            return new AtmMachine(1_000_000_000L);
        }
    }

    // --- 관리자 로그 저장/불러오기 (DeunAdmin에서만 사용) ---
    public void saveAdminLogs(List<AdminLog> adminLogs) {
        try (Writer writer = new FileWriter(ADMIN_LOGS_FILE)) {
            gson.toJson(adminLogs, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<AdminLog> loadAdminLogs() {
        try (Reader reader = new FileReader(ADMIN_LOGS_FILE)) {
            Type listType = new TypeToken<ArrayList<AdminLog>>() {}.getType();
            List<AdminLog> logs = gson.fromJson(reader, listType);
            return logs != null ? logs : new ArrayList<>();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}

/*
🟢 추출 (Load) : 프로그램을 켰을 때
1.프로그램 시작: AtmMainFrame이 켜지면서 가장 먼저 FileDataManager 객체를 만듭니다.
2.파일 읽기: FileDataManager는 data 폴더에 가서 .json 텍스트 파일들을 읽어옵니다.
3.객체로 변환(번역): 텍스트로 된 JSON 데이터를 Gson 번역기가 자바 객체(Account, AtmMachine 등)로 조립해 줍니다.
4.서비스에 전달: 조립된 자바 객체들을 AccountAuthServiceImp, TransactionBalanceService 같은 서비스 클래스들에 넘겨줍니다.
(결과: 이전에 프로그램 껐을 때의 잔액과 상태 그대로 ATM 기기가 세팅됩니다!)

🔴 저장 (Save) : 거래가 발생했을 때
1.상태 변경: 사용자가 입금을 하거나 출금을 해서 서비스 클래스 내부의 자바 객체 데이터(잔액 등)가 바뀝니다.
2.저장 지시: 서비스 클래스는 데이터가 바뀌자마자 fileDataManager.saveAccounts(accounts) 등을 호출하여
    "이거 지금 상태로 저장해 줘!"라고 지시합니다.
3.텍스트로 변환(번역): Gson 번역기가 바뀐 자바 객체를 다시 JSON 텍스트로 변환합니다.
4.파일 덮어쓰기: 변환된 텍스트를 data 폴더 안의 .json 파일에 덮어씁니다.
(결과: 만약 이 직후에 갑자기 컴퓨터 전원이 꺼져도, 마지막 거래 내역까지 파일에 안전하게 기록되어 있습니다!)

💡 요약하자면: 서비스 로직(현겸, 영석, 해든 님의 코드)은 텍스트 파일에 대해 신경 쓸 필요 없이 그저 평소처럼 자바 객체만 수정하고,
 변경될 때마다 FileDataManager 에게 저장하라고 툭 던져주기만 하면 알아서 번역해서 파일에 기록해 주는 깔끔하고 객체지향적인 구조입니다.
* */