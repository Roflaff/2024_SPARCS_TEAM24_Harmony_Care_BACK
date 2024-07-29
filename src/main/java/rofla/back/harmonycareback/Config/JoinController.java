package rofla.back.harmonycareback.Config;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rofla.back.harmonycareback.Dto.JoinDTO;
import rofla.back.harmonycareback.Service.JoinService;

@RestController
@AllArgsConstructor
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/join")
    public ResponseEntity<String> joinProcess(@RequestBody JoinDTO joinDTO) {
        joinService.joinProcess(joinDTO);
        return ResponseEntity.ok("ok");
    }
}