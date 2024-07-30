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
    // DB 접근을 위한 함수
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

    // 별점
    @Column(name = "star")
    private double star;

    // 이메일
    @Column(name = "e_mail", length = 40)
    private String eMail;

    // 거주지
    @Column(name = "regin", length = 500)
    private String regin;

    // 전화번호
    @Column(name = "phone_num", length = 11)
    private String phoneNum;

}