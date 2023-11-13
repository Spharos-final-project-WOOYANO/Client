package spharos.client.bank.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.client.bank.domain.Bank;

public interface BankRepository extends JpaRepository<Bank, Long> {

}
