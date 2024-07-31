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

    // 공통!!
    @Value("${api.host}")
    private String host;
    @Value("${api.key}")
    private String apiKey;
    @Value("${api.key.primary.val}")
    private String apiKeyPrimaryVal;

    @Value("${api.request.idH}")
    private String requestIdH;
    @Value("${api.request.idC}")
    private String requestIdC;


    private final RestTemplate restTemplate;


    // H 하모니 키워드 => 줄 글
    public ResponseEntity<String> executeH(String completionHRequest) {
        String url = host + "/testapp/v1/chat-completions/HCX-DASH-001";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-CLOVASTUDIO-API-KEY", apiKey);
        headers.set("X-NCP-APIGW-API-KEY", apiKeyPrimaryVal);
        headers.set("X-NCP-CLOVASTUDIO-REQUEST-ID", requestIdH);
        headers.set("Content-Type", "application/json; charset=utf-8");
        headers.set("Accept", "text/event-stream");

        HttpEntity<String> requestEntity = new HttpEntity<>(completionHRequest, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // H 하모니 키워드 => 줄 글
    public ResponseEntity<String> executeC(String completionCRequest) {
        String url = host + "/testapp/v1/chat-completions/HCX-003";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-CLOVASTUDIO-API-KEY", apiKey);
        headers.set("X-NCP-APIGW-API-KEY", apiKeyPrimaryVal);
        headers.set("X-NCP-CLOVASTUDIO-REQUEST-ID", requestIdC);
        headers.set("Content-Type", "application/json; charset=utf-8");
        headers.set("Accept", "text/event-stream");

        HttpEntity<String> requestEntity = new HttpEntity<>(completionCRequest, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
