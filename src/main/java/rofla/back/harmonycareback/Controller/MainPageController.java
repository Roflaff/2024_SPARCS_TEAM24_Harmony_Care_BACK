package rofla.back.harmonycareback.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rofla.back.harmonycareback.Dto.ListOfHermonyRespone;
import rofla.back.harmonycareback.Dto.SaveTextHProfileDTORequest;
import rofla.back.harmonycareback.Dto.addMemberFeatDTO;
import rofla.back.harmonycareback.Jwt.JWTUtil;
import rofla.back.harmonycareback.Model.Member;
import rofla.back.harmonycareback.Model.MemberFeat;
import rofla.back.harmonycareback.Service.MemberFeatService;
import rofla.back.harmonycareback.Service.MemberService;
import rofla.back.harmonycareback.Service.ParentService;

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
    private final ParentService parentService;
    private final JWTUtil jwtUtil;

    // 로그인 직후 바로 get 을 통해 권한 Boolean 전달 P : 1 H : 0
    @GetMapping("/role")
    public ResponseEntity<Boolean> getRoleOfMember(HttpServletRequest http){
        String token = http.getHeader("access");
        String username = jwtUtil.getUsername(token);
        Member member = memberService.searchByUsername(username).get();
        if(member.getRole().equals("P"))
            return ResponseEntity.ok(true);
        else
            return ResponseEntity.ok(false);
    }

    // 둘 다 허용
    @PutMapping("/addFeat")
    public ResponseEntity<String> updateFeat(@RequestBody addMemberFeatDTO memberFeat, HttpServletRequest http){
        memberFeatService.addMemberFeat(memberFeat, http);
        return ResponseEntity.ok("ok");
    }

    // [role : P] 시니어 하모니 정보 모두 출력
    @GetMapping("/listOfALlHarmony")
    public ResponseEntity<List<MemberFeat>> getlistOfALlHarmony(HttpServletRequest http){
        // 멤버 권한 확인
        if(memberService.checkRole(http)) {
            return ResponseEntity.status(409).build();
        }
        // 유저 객체 찾아서 반환 없으면 null
        String token = http.getHeader("access");
        String username = jwtUtil.getUsername(token);
        Member member = memberService.searchByUsername(username).get();

        parentService.getAllHofMemberFeat(member);

        List<MemberFeat> m1 = parentService.getAllHofMemberFeat(member);

        return ResponseEntity.ok(m1);

    }

    @GetMapping("/makeHProfile")
    public ResponseEntity<String> getTextOfHProfile(HttpServletRequest httpServletRequest) {
        String temp = memberFeatService.makeHarmonyProfile(httpServletRequest);
        return ResponseEntity.ok(temp);
    }

    @PutMapping("/saveHProfile")
    public ResponseEntity<String> saveTextOfHProfile(@RequestBody SaveTextHProfileDTORequest saveTextHProfileDTORequest, HttpServletRequest http){
        try {
            memberFeatService.saveTextOfHProfile(saveTextHProfileDTORequest, http);
            return ResponseEntity.ok("ok");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).build();
        }
    }


}
