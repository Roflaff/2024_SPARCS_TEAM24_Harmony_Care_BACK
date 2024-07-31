package rofla.back.harmonycareback.Dto;

import lombok.Data;
import rofla.back.harmonycareback.Model.MemberFeat;

// 메인 화면에 하모니 객체 전달 (리스트 안)
@Data
public class ListOfHermonyRespone {
    private String regin;
    private String name;
    private String age; // 나이 계산 로직 필요
    private String username;
}
