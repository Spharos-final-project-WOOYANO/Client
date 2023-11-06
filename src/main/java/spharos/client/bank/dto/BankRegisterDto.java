package spharos.client.bank.dto;

import lombok.*;
import spharos.client.clients.domain.Client;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankRegisterDto {

    private String bankName;
    private String bankAccount;
    private String bankHolder;
    private Boolean bankState;
    private String bankImgUrl;
    private Client client;

}
