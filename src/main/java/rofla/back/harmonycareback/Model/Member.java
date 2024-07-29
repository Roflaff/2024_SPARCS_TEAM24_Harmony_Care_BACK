package rofla.back.harmonycareback.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "member", schema = "sparcs")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    // 실제 아이디(회원가입 정보에서 받는 아이디)
    @Column(name = "username", nullable = false, length = 20)
    private String username;

    // 암호화 된 비번
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    // 권한 설정 (S / P / A) (시니어 / 부모 / 관리자)
    @Column(name = "role", nullable = false, length = 2)
    private String role;

    // 실제 이름
    @Column(name = "name", length = 30)
    private String name;

    // 나이 (LocalDate 형식으로 나중에 변환 필요)
    @Column(name = "age")
    private LocalDate age;

}