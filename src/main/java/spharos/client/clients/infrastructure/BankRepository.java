package spharos.client.clients.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.client.clients.domain.Bank;

public interface BankRepository extends JpaRepository<Bank, Long> {

}
