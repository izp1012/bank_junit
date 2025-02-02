package shop.mtcoding.bank.domain.transaction;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import shop.mtcoding.bank.domain.account.Account;

import java.time.LocalDateTime;

@NoArgsConstructor //스프링이 User 객체 생성할 때, 빈 생성자로 new 하기 때문!!
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "transaction_tb")
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account WithdrawAccount;
    @ManyToOne(fetch = FetchType.LAZY)
    private Account depositAccount;

    @Column(nullable = false)
    private Long amount;

    private Long withdrawAccountBalance;    //111 계좌 -> 1000원 -> 500원 ->200원
    private Long depositAccountBalance;    //111 계좌 -> 1000원 -> 500원 ->200원

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionEnum gubun;  //WITHDRAW, DEPOSIT, TRANSFER, ALL

    //계좌가 사라져도 로그가 남아야 한다.
    private String sender;
    private String receiver;
    private String tel;

    @CreatedDate    //Insert
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate   //Insert, Update
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public Transaction(Long id, Account withdrawAccount, Account depositAccount, Long amount, Long withdrawAccountBalance, Long depositAccountBalance, TransactionEnum gubun, String sender, String receiver, String tel, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        WithdrawAccount = withdrawAccount;
        this.depositAccount = depositAccount;
        this.amount = amount;
        this.withdrawAccountBalance = withdrawAccountBalance;
        this.depositAccountBalance = depositAccountBalance;
        this.gubun = gubun;
        this.sender = sender;
        this.receiver = receiver;
        this.tel = tel;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}