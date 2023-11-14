package spharos.client.clients.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientLoginResponse {

    private String token;
    private String clientId;
    private List<Long> serviceIdList;

}
