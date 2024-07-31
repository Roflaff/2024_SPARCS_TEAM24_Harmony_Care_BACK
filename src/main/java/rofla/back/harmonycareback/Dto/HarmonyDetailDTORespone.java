package rofla.back.harmonycareback.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
public class HarmonyDetailDTORespone {
    // 제목 => MemberFeat / 생성 필요
    private String extraExplainText;
    // 이름 => Member
    private String name;
    // 나이 형식 : [xx세/19xx년생] => Member / 생성 필요
    private String age;
    // 거주지 => Member
    private String regin;
    // 자기 소개 줄글 => MemberFeat
    private String explainText;
    // 하모니 성격 1 => MemberFeat
    private String f1H;
    // 하모니 성격 2 => MemberFeat
    private String f2H;
    // 하모니 성격 3 => MemberFeat
    private String f3H;
    // 선호 아이 성격 1 => MemberFeat
    private String f1C;
    // 아이 성격 2 => MemberFeat
    private String f2C;
    // 아이 성격 3 => MemberFeat
    private String f3C;

    // 어떤 H 인지 알기 위한 id 값 => Member
    private Long id;
}
