package rofla.back.harmonycareback.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rofla.back.harmonycareback.Service.CompletionService;

@RestController
@RequestMapping("/api/completion")
public class CompletionController {

    private final CompletionService completionService;

    public CompletionController(CompletionService completionService) {
        this.completionService = completionService;
    }

    @PostMapping()
    public ResponseEntity<String> getCompletion(@RequestBody String completionRequest) {
        ResponseEntity<String> response = completionService.execute(completionRequest);
        return response;
    }
}
