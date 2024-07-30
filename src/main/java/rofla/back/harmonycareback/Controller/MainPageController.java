package rofla.back.harmonycareback.Controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rofla.back.harmonycareback.Dto.ListOfPermonyRespone;
import rofla.back.harmonycareback.Model.MemberFeat;
import rofla.back.harmonycareback.Service.MemberFeatService;
import rofla.back.harmonycareback.Service.MemberService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/main")
public class MainPageController {
    // [role : S] [role : P] 구분 확인
    // S -> 시니어 접속 이므로 시니어가 봐야 할 페이지 객체 전달
    // P -> 학부모 접속 이므로 학부모가 봐야 할 페이지 객체 전달

    private final MemberService memberService;
    private final MemberFeatService memberFeatService;

    // 둘 다 허용
    public ResponseEntity<String> addFeat(MemberFeat memberFeat){
        memberFeatService.addMemberFeat(memberFeat);
        return ResponseEntity.ok("ok");
    }

    // [role : P] 부모님들이 시니어들 정보 확인
    @GetMapping("/listOfALlHarmony")
    public ResponseEntity<List<ListOfPermonyRespone>> getlistOfALlHarmony(HttpServletRequest http){
        // 멤버 권한 확인
        if(memberService.checkRole(http)) {
            return ResponseEntity.status(409).build();
        }



        return ResponseEntity.status(409).build();
    }


}