package rofla.back.harmonycareback.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rofla.back.harmonycareback.Service.CompletionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/completion")
public class CompletionController {

    private final CompletionService completionService;

    @PostMapping()
    public ResponseEntity<String> getCompletion(@RequestBody String completionRequest) {
        ResponseEntity<String> response = completionService.execute(completionRequest);
        return response;
    }
}
