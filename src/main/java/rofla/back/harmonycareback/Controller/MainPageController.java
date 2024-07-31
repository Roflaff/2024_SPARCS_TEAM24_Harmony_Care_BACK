package rofla.back.harmonycareback.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rofla.back.harmonycareback.Dto.*;
import rofla.back.harmonycareback.Jwt.JWTUtil;
import rofla.back.harmonycareback.Model.Member;
import rofla.back.harmonycareback.Model.MemberFeat;
import rofla.back.harmonycareback.Service.MemberFeatService;
import rofla.back.harmonycareback.Service.MemberService;
import rofla.back.harmonycareback.Service.ParentService;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
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

    // [role : P] 시니어 하모니 정보 모두 출력 (정렬)
    @GetMapping("/listOfALlHarmony")
    public ResponseEntity<List<ListOfHarmonyRespone>> getlistOfALlHarmony(HttpServletRequest http){
        // 멤버 권한 확인
        if(memberService.checkRole(http)) {
            return ResponseEntity.status(409).build();
        }
        // 유저 객체 찾아서 반환 없으면 null
        String token = http.getHeader("access");
        String username = jwtUtil.getUsername(token);
        Member member = memberService.searchByUsername(username).get();

        List<MemberFeat> m1 = parentService.getAllHofMemberFeat(member);

        // ListOfHarmonyRespone 에 정렬된 정보 중 필요한 정보 매칭
        List<ListOfHarmonyRespone> listOfHarmonyRespones = new ArrayList<>();
        for(MemberFeat m : m1) {
            String regin = m.getMemberIdFeat().getRegin();
            String name = m.getMemberIdFeat().getName();

            // 나이 계산 로직
            LocalDate bir = m.getMemberIdFeat().getAge();
            LocalDate current = LocalDate.of(2024, 1, 1);

            int age = Period.between(bir, current).getYears();
            String ageM = age + "세";

            String title = m.getExtraExplainText();
            Long id = m.getMemberIdFeat().getId();
            ListOfHarmonyRespone listOfHarmonyRespone = new ListOfHarmonyRespone(regin, name, ageM,title ,id);
            listOfHarmonyRespones.add(listOfHarmonyRespone);
        }

        return ResponseEntity.ok(listOfHarmonyRespones);
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

    // 1. 아이 정보 DB 저장 (키워드 추출 전)
    @PostMapping("/addCFeat")
    public ResponseEntity<String> saveTextOfCProfile(@RequestBody ChildFeatDTORequest childFeatDTORequest, HttpServletRequest httpServletRequest) {
        memberFeatService.addCFeat(childFeatDTORequest, httpServletRequest);
        return ResponseEntity.ok("ok");
    }

    // 2. 키워드 추출 후 DB 저장
    @PutMapping("/saveCKeyword")
    public ResponseEntity<String> saveCKeyword(HttpServletRequest httpServletRequest) {
        try {
            memberFeatService.makeCKeyword(httpServletRequest);
            return ResponseEntity.ok("ok");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).build();
        }
    }

    // 하모니 정보 페이지 부를 때 호출
    @GetMapping("/Harmony/{id}")
    public ResponseEntity<HarmonyDetailDTORespone> getHarmonyById(@PathVariable Long id, HttpServletRequest http) {
        HarmonyDetailDTORespone harmonyDetailDTORespone = memberService.getHarmonyById(id, http);
        return ResponseEntity.ok(harmonyDetailDTORespone);
    }


}
