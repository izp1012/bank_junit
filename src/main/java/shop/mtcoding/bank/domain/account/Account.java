package shop.mtcoding.bank.domain.account;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import shop.mtcoding.bank.domain.user.User;

import java.time.LocalDateTime;

//Full Arguments 생성자가 있으면, 반드시 넣어주어야 하는 Lombok
@NoArgsConstructor //스프링이 User 객체 생성할 때, 빈 생성자로 new 하기 때문!!
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "account_tb")
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, length = 20)
    private Long number;    //계좌번호
    @Column(nullable = false, length = 4)
    private Long password;  //계좌비번
    @Column(nullable = false)
    private Long balance;   //잔액 (기본값 1000원)

    //항상 ORM 에서 fk 의 주인은 Many Entity 쪽이다.
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @CreatedDate    //Insert
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate   //Insert, Update
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public Account(LocalDateTime updatedAt, LocalDateTime createdAt, User user, Long balance, Long password, Long number, Long id) {
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.user = user;
        this.balance = balance;
        this.password = password;
        this.number = number;
        this.id = id;
    }
}
