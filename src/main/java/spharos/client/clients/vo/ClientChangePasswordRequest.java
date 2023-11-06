package spharos.client.clients.vo;

import lombok.Getter;

@Getter
public class ClientChangePasswordRequest {

    private String email;
    private String password;

}
