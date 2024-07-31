package rofla.back.harmonycareback.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class CompletionService {

    @Value("${api.host}")
    private String host;

    @Value("${api.keyH}")
    private String apiKey;

    @Value("${api.key.primary.valH}")
    private String apiKeyPrimaryVal;

    @Value("${api.request.idH}")
    private String requestId;

    private final RestTemplate restTemplate;

    public ResponseEntity<String> execute(String completionRequest) {
        String url = host + "/testapp/v1/chat-completions/HCX-DASH-001";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-CLOVASTUDIO-API-KEY", apiKey);
        headers.set("X-NCP-APIGW-API-KEY", apiKeyPrimaryVal);
        headers.set("X-NCP-CLOVASTUDIO-REQUEST-ID", requestId);
        headers.set("Content-Type", "application/json; charset=utf-8");
        headers.set("Accept", "text/event-stream");

        HttpEntity<String> requestEntity = new HttpEntity<>(completionRequest, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
