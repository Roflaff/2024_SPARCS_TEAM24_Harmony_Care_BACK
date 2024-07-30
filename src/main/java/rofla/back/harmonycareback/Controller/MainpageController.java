package rofla.back.harmonycareback.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/main")
public class MainpageController {

    // [role : S] [role : P] 구분 확인
    // S -> 시니어 접속 이므로 시니어가 봐야 할 페이지 객체 전달
    // P -> 학부모 접속 이므로 학부모가 봐야 할 페이지 객체 전달



}
