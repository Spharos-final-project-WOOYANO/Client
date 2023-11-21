package spharos.client.clients.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientInformationResponse {

    private String clientId;
    private String ceoName;
    private String clientName;
    private String clientPhone;
    private String clientAddress;
    private String clientRegistrationNumber;
    private LocalDateTime createdAt;

}
