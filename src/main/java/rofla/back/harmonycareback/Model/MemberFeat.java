package rofla.back.harmonycareback.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "member_feat", schema = "sparcs")
public class MemberFeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    // 해당 행의 타입 [ C1~C20 / H ]
    @Column(name = "type", nullable = false, length = 3)
    private String type;

    // 특징 1
    @Column(name = "f1_c", length = 20)
    private String f1C;

    // 특징 2
    @Column(name = "f2_c", length = 20)
    private String f2C;

    // 특징 3
    @Column(name = "f3_c", length = 20)
    private String f3C;

    // CLOVA studio 결과 or 사용자 Input
    @Column(name = "explain_text", length = 3000)
    private String explainText;

    // 성별
    @Column(name = "sex", length = 2)
    private String sex;

    // 아이 경우 나이
    @Column(name = "age")
    private LocalDate age;

    // 추가 설명 필요할 때
    @Column(name = "extra_explain_text", length = 3000)
    private String extraExplainText;

    // 외래키 (어던 멤버인지) 유저 아이디로 저장
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id_feat", nullable = false, referencedColumnName = "username")
    private Member memberIdFeat;

    @Column(name = "f1_h", length = 20)
    private String f1H;

    @Column(name = "f2_h", length = 20)
    private String f2H;

    @Column(name = "f3_h", length = 20)
    private String f3H;

}