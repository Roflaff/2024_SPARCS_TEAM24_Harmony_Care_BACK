package rofla.back.harmonycareback.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// 메인 화면에 하모니 객체 전달 (리스트 안)
@Getter
@Setter
@Data
@AllArgsConstructor
public class ListOfHarmonyRespone {
    // 거주지
    private String regin;
    // 이름 (중간 별표)
    private String name;
    // xx세
    private String age;
    // 제목
    private String title;
    // 겍체 접근 위한 id
    private Long id;
}
