package spharos.client.clients.vo.request;

import lombok.Getter;

@Getter
public class ClientChangePasswordRequest {

    private String email;
    private String password;

}
