package spharos.client.bank.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spharos.client.bank.domain.Bank;
import spharos.client.bank.dto.BankRegisterDto;
import spharos.client.bank.infrastructure.BankRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class BankServiceImpl implements BankService{

    private final BankRepository bankRepository;

    // 은행정보 등록
    @Override
    public void saveBank(BankRegisterDto dto) {
        Bank bank = Bank.createBank(dto.getBankName(), dto.getBankAccount(), dto.getBankHolder(), dto.getBankState(),
                dto.getBankImgUrl(), dto.getClient());
        bankRepository.save(bank);
    }
}
