package spharos.client.clients.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import spharos.client.global.common.domain.BaseEntity;

import java.util.Collection;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    @Column(nullable = false, length = 10, name = "client_registration_number") // TODO 코드리뷰 - 자리수 좀 더 여유둘 것
    private String clientRegistrationNumber;
    @Column(nullable = false, name = "client_registration_img_url")
    private String clientRegistrationImgUrl;
    @Column(nullable = false, name = "client_status", columnDefinition = "tinyint default 3")
    private Integer clientStatus;

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
