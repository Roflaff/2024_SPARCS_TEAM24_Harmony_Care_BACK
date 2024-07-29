package rofla.back.harmonycareback.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rofla.back.harmonycareback.Model.Member;
import rofla.back.harmonycareback.Service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<String> joinMember(Member member) {
        try {
            memberService.joinMember(member);
            return ResponseEntity.ok("join suc!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

}
