package shop.mtcoding.bank.domain.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
//Full Arguments 생성자가 있으면, 반드시 넣어주어야 하는 Lombok
@NoArgsConstructor //스프링이 User 객체 생성할 때, 빈 생성자로 new 하기 때문!!
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user_tb")
@Entity
public class User { //extends 시간설정(상속)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    private String username;

    @Column(nullable = false, length = 60)  //패스워드 인코드(BCrypt)
    private String password;

    @Column(nullable = false, length = 20)
    private String email;
    @Column(nullable = false, length = 20)
    private String fullname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserEnum role;  //ADMIN, CUSTOMER

    @CreatedDate    //Insert
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate   //Insert, Update
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public User(Long id, String username, String password, String email, String fullname, UserEnum role, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullname = fullname;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}

