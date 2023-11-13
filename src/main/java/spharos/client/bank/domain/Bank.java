package spharos.client.bank.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.client.clients.domain.Client;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "bank")
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 15, name = "bank_name")
    private String bankName;
    @Column(nullable = false, length = 20, name = "bank_account")
    private String bankAccount;
    @Column(nullable = false, length = 20, name = "bank_holder")
    private String bankHolder;
    @Column(nullable = false, name = "bank_state", columnDefinition = "boolean default false")
    private Boolean bankState;
    @Column(nullable = false, name = "bank_img_url")
    private String bankImgUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Client client;

    private Bank(String bankName, String bankAccount, String bankHolder, Boolean bankState, String bankImgUrl,
    Client client) {
        this.bankName = bankName;
        this.bankAccount = bankAccount;
        this.bankHolder = bankHolder;
        this.bankState = bankState;
        this.bankImgUrl = bankImgUrl;
        this.client = client;
    }

    // 업체 생성
    public static Bank createBank(String bankName, String bankAccount, String bankHolder, Boolean bankState,
                                  String bankImgUrl, Client client) {
        return new Bank(bankName, bankAccount, bankHolder, bankState, bankImgUrl, client);
    }

}
