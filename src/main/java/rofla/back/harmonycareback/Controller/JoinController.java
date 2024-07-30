package rofla.back.harmonycareback.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rofla.back.harmonycareback.Dto.JoinDTO;
import rofla.back.harmonycareback.Service.JoinService;

@RestController
@AllArgsConstructor
public class JoinController {

    private final JoinService joinService;

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<String> joinProcess(@RequestBody JoinDTO joinDTO) {
        joinService.joinProcess(joinDTO);
        return ResponseEntity.ok("ok");
    }

    // 아이디 중복 체크
    @GetMapping("/findUsername/{username}")
    public ResponseEntity<String> searchUsername(@PathVariable String username) {
        String result =  joinService.searchUsername(username);
        return ResponseEntity.ok(result);
    }

}