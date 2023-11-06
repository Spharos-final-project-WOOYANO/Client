package spharos.client.clients.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import spharos.client.global.common.domain.BaseEntity;

import java.util.Collection;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "client")
public class Client extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, length = 50, name = "client_id")
    private String clientId;
    @Column(nullable = false, name = "client_password")
    private String clientPassword;
    @Column(nullable = false, length = 20, name = "ceo_name")
    private String ceoName;
    @Column(nullable = false, length = 20, name = "client_name")
    private String clientName;
    @Column(nullable = false, length = 11, name = "client_phone")
    private String clientPhone;
    @Column(nullable = false, length = 50, name = "client_address")
    private String clientAddress;
    @Column(nullable = false, length = 15, name = "client_registration_number")
    private String clientRegistrationNumber;
    @Column(nullable = false, name = "client_registration_img_url")
    private String clientRegistrationImgUrl;
    @Column(nullable = false, name = "client_status", columnDefinition = "tinyint default 3")
    private Integer clientStatus;

    private Client(String clientId, String clientPassword, String ceoName,
                   String clientName,String clientPhone, String clientAddress,
                   String clientRegistrationNumber, String clientRegistrationImgUrl, Integer clientStatus) {
        this.clientId = clientId;
        this.clientPassword = clientPassword;
        this.ceoName = ceoName;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.clientAddress = clientAddress;
        this.clientRegistrationNumber = clientRegistrationNumber;
        this.clientRegistrationImgUrl = clientRegistrationImgUrl;
        this.clientStatus = clientStatus;
    }

    // 업체 생성
    public static Client createClient(String clientId, String clientPassword, String ceoName,
                                      String clientName,String clientPhone, String clientAddress,
                                      String clientRegistrationNumber, String clientRegistrationImgUrl,
                                      Integer clientStatus) {
        return new Client(clientId, clientPassword, ceoName, clientName, clientPhone, clientAddress
                ,clientRegistrationNumber, clientRegistrationImgUrl, clientStatus);
    }

    // 비밀번호 변경
    public void setClientPassword(String clientPassword) {
        this.clientPassword = clientPassword;
    }

    // 시큐리티 관련
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return clientPassword;
    }

    @Override
    public String getUsername() {
        return clientId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
