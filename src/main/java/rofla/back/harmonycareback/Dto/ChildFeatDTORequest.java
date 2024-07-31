package rofla.back.harmonycareback.Dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class ChildFeatDTORequest {
    private String title;
    private String sex;
    private String age;
    private String extraExplainText;
}
