package spharos.client.clients.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
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

}
