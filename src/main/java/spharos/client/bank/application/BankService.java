package spharos.client.bank.application;

import spharos.client.bank.dto.BankRegisterDto;

public interface BankService {

    // 은행정보 등록
    void saveBank(BankRegisterDto bankRegisterDto);

}
