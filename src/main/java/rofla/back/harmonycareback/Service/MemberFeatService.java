package rofla.back.harmonycareback.Service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import rofla.back.harmonycareback.Dto.ChildFeatDTORequest;
import rofla.back.harmonycareback.Dto.SaveTextHProfileDTORequest;
import rofla.back.harmonycareback.Dto.addMemberFeatDTO;
import rofla.back.harmonycareback.Jwt.JWTUtil;
import rofla.back.harmonycareback.Model.Member;
import rofla.back.harmonycareback.Model.MemberFeat;
import rofla.back.harmonycareback.Repository.MemberFeatRepository;
import rofla.back.harmonycareback.Repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberFeatService {
    private final MemberFeatRepository memberFeatRepository;
    private final MemberRepository memberRepository;
    private final JWTUtil jwtUtil;
    private final CompletionService completionService;

    private String username;
    private String role;
    private String token ;



    // 저장 로직
    public void addMemberFeat(addMemberFeatDTO memberFeat, HttpServletRequest http) {
        // 저장 할 객체의 아이디로 DB에 존재 하는 항목 확인
        this.token = http.getHeader("access");
        this.username = jwtUtil.getUsername(token);
        this.role = jwtUtil.getRole(token);

        String username = this.username;
        String role = this.role;

        System.out.println("특징 추가 유저: " + username);
        List<MemberFeat> allMf = memberFeatRepository.findByMemberIdFeat(memberRepository.findByUsername(username).get());
        // 유저 권한 확인
        // 하모니 일 때
        if(role.equals("H")) {
            // 빈 객체 생성
            MemberFeat mf = new MemberFeat();
            // 특징 값 저장 f1C ~ f3C (선호 하는 아이 키워드 값)
            mf.setF1C(memberFeat.getF1C());
            mf.setF2C(memberFeat.getF2C());
            mf.setF3C(memberFeat.getF3C());

            // 특징 값 저장 f1H ~ f3H (선호 하는 하모니 키워드 값)
            mf.setF1H(memberFeat.getF1H());
            mf.setF2H(memberFeat.getF2H());
            mf.setF3H(memberFeat.getF3H());

            // 하모니 멤버 객체 외래키
            mf.setMemberIdFeat(memberRepository.findByUsername(username).get());
            // H 값 저장
            mf.setType(role);
            // MemberFeat -> DB 저장
            memberFeatRepository.save(mf);
        }
        // 부모님 일 때
        else if(role.equals("P")) {
            String temp = "C1";
            // 자식이 있으면
            if(!allMf.isEmpty()) {
                int i = allMf.size();
                // 자녀가 이미 존재 할 때 항목 다르게 저장
                if(i == 1) {
                    temp = "C2";
                }
                else if(i==2) {
                    temp = "C3";
                }
                else if(i==3) {
                    temp = "C4";
                }
                else if(i==4) {
                    temp = "C5";
                }
                else if(i==5) {
                    temp = "C6";
                }
            }
            MemberFeat mf = new MemberFeat();
            mf.setType(temp);

            // 줄글 에서 저장 하는 메서드 만들고 여기서 실행 => 아이의 키워드 3개 추출

            mf.setMemberIdFeat(memberRepository.findByUsername(username).get());
            memberFeatRepository.save(mf);
        }
    }

    // 아이 특징 저장
    public void addCFeat(ChildFeatDTORequest childFeatDTORequest, HttpServletRequest httpServletRequest) {
        this.token = httpServletRequest.getHeader("access");
        this.username = jwtUtil.getUsername(token);
        this.role = jwtUtil.getRole(token);

        String username = this.username;
        String role = this.role;

        System.out.println("특징 추가 유저: " + username);
        List<MemberFeat> allMf = memberFeatRepository.findByMemberIdFeat(memberRepository.findByUsername(username).get());
        if(role.equals("P")) {
            String temp = "C1";
            // 자식이 있으면
            if (!allMf.isEmpty()) {
                int i = allMf.size();
                // 자녀가 이미 존재 할 때 항목 다르게 저장
                if (i == 1) {
                    temp = "C2";
                } else if (i == 2) {
                    temp = "C3";
                } else if (i == 3) {
                    temp = "C4";
                } else if (i == 4) {
                    temp = "C5";
                } else if (i == 5) {
                    temp = "C6";
                }
            }
            MemberFeat mf = new MemberFeat();
            mf.setType(temp);

            // 줄글 에서 저장 하는 메서드 만들고 여기서 실행 => 아이의 키워드 3개 추출
            mf.setExplainText(childFeatDTORequest.getTitle()); // 아이 소개서 제목
            mf.setSex(childFeatDTORequest.getSex()); // 아이 성별
            mf.setAge(childFeatDTORequest.getAge()); // 아이 나이
            mf.setExtraExplainText(childFeatDTORequest.getExtraExplainText()); // 줄글 -> 키워드 위한 줄글

            mf.setMemberIdFeat(memberRepository.findByUsername(username).get()); // 객체 저장

            memberFeatRepository.save(mf);
        }
    }

    



    // 하모니 키워드 3개로 줄글 api 호출 [H]
    public String makeHarmonyProfile(HttpServletRequest http) {
        this.token = http.getHeader("access");
        this.username = jwtUtil.getUsername(token);
        this.role = jwtUtil.getRole(token);
        String username = this.username;
        String f1H, f2H, f3H;

        Member member = memberRepository.findByUsername(username).get();

        List<MemberFeat> memberFeats = memberFeatRepository.findByMemberIdFeat(member);

        MemberFeat memberFeat = memberFeats.getLast();

        f1H = memberFeat.getF1H();
        f2H = memberFeat.getF2H();
        f3H = memberFeat.getF3H();

        String f_total_H = f1H + " / " + f2H + " / " + f3H;

        String resultText;
        String jsonText = "{\n" +
                "  \"messages\" : [ {\n" +
                "    \"role\" : \"system\",\n" +
                "    \"content\" : \"- 너는 아이를 돌보는 일자리에 지원하는 \\\"시니어\\\" 역할이다.\\n- 친근하고 정감있는 말투로 자기소개를 한다.\\n- 사용자가 입력한 단어 3개로 무조건 600자 이상의 문장을 만든다\\n- 각 문단은 들여쓰기로 구분해\\n- 결과는 보기 쉬워야 한다\\n\\n단어 : 책임감 / 체력 / 소통능력 / 응급처치및안전관리 / 인내심 / 문제해결능력 / 관찰력 / 조리및영양지식 / 위생관리 / 감정조절능력 / 안정감 / 문화적다양성\\n###\\n\"\n" +
                "  }, {\n" +
                "    \"role\" : \"user\",\n" +
                "    \"content\" : \"단어 : 관찰력 / 안정감 / 문화적 다양성\"\n" +
                "  }, {\n" +
                "    \"role\" : \"assistant\",\n" +
                "    \"content\" : \"안녕하세요, 저는 시니어 도우미라고 합니다. \\n저는 오랜 시간 동안 여러 가정에서 베이비시터나 보육교사로 일하며 많은 경험을 쌓았습니다. 그 덕분에 아기들을 돌볼 때 필요한 지식과 기술을 많이 습득했습니다. 특히 제가 가진 강점 중 하나는 바로 뛰어난 관찰력 입니다. 이를 통해 아기들의 작은 변화도 놓치지 않고 빠르게 파악할 수 있습니다. \\n\\n또 다른 장점인 안정감으로 부모님들이 안심하고 맡길 수 있도록 하겠습니다. 그리고 외국어 능력이 있어 다문화 가정도 무리 없이 돌볼 수 있고 이와 더불어 문화적 다양성을 존중하여 모든 가족 구성원에게 편안한 환경을 제공하겠습니다.\"\n" +
                "  }, {\n" +
                "    \"role\" : \"user\",\n" +
                "    \"content\" : \"단어 : " + f_total_H +"\"\n" +
                "  } ],\n" +
                "  \"topP\" : 0.8,\n" +
                "  \"topK\" : 0,\n" +
                "  \"maxTokens\" : 256,\n" +
                "  \"temperature\" : 0.61,\n" +
                "  \"repeatPenalty\" : 7.75,\n" +
                "  \"stopBefore\" : [ ],\n" +
                "  \"includeAiFilters\" : true,\n" +
                "  \"seed\" : 0\n" +
                "}";

        resultText = completionService.executeH(jsonText).getBody();

        String[] lines = resultText.split("\n");

        // JSON 파싱에 사용할 변수들을 초기화합니다.
        boolean resultEventFound = false;
        StringBuilder jsonStringBuilder = new StringBuilder();

        // 줄 단위로 문자열을 순회합니다.
        for (String line : lines) {
            if (line.startsWith("event:result")) {
                resultEventFound = true;
                continue; // 다음 줄부터 JSON 데이터를 수집합니다.
            }

            if (resultEventFound && line.startsWith("data:")) {
                // "data:" 부분을 제거하고 JSON 데이터를 추가합니다.
                jsonStringBuilder.append(line.substring(5));
            } else if (resultEventFound) {
                // "data:"로 시작하지 않는 줄도 JSON 데이터로 추가합니다.
                jsonStringBuilder.append(line);
            }
        }

        if (!jsonStringBuilder.isEmpty()) {
            // 수집된 JSON 문자열을 파싱합니다.
            String jsonString = jsonStringBuilder.toString();
            JSONObject jsonObject = new JSONObject(jsonString);

            // "message" 객체 안의 "content" 값을 가져옵니다.
            resultText = jsonObject.getJSONObject("message").getString("content");

        } else {
            System.out.println("Error: 'event:result' not found or no data to parse.");
        }

        System.out.println(resultText);

        return resultText;
    }

    // 3. 프론트 수정 텍스트 받아서 저장 [H]
    public void saveTextOfHProfile(SaveTextHProfileDTORequest saveTextHProfileDTORequest, HttpServletRequest http) {
        this.token = http.getHeader("access");
        this.username = jwtUtil.getUsername(token);
        this.role = jwtUtil.getRole(token);
        String username = this.username;
        Member member = memberRepository.findByUsername(username).get();
        List<MemberFeat> memberFeats = memberFeatRepository.findByMemberIdFeat(member);
        // 리스트의 마지막 객체를 가져옴
        if (!memberFeats.isEmpty()) {
            MemberFeat memberFeat = memberFeats.get(memberFeats.size() - 1);

            // 필요한 열 값을 수정
            memberFeat.setExplainText(saveTextHProfileDTORequest.getExplainText()); // 수정할 열에 맞게 설정
            // 변경사항을 저장
            memberFeatRepository.save(memberFeat);
        } else {
            throw new RuntimeException("No MemberFeat found for the member");
        }
    }

    // 아이 줄글로 키워드 3개 추출 [P]
    public String makeCKeyword(HttpServletRequest http) {
        this.token = http.getHeader("access");
        this.username = jwtUtil.getUsername(token);
        this.role = jwtUtil.getRole(token);
        String username = this.username;

        // 부모님 객체 불러오기
        Member member = memberRepository.findByUsername(username).get();

        // 부모님이 대리고 있는 모든 아이 특징 가져옴
        List<MemberFeat> memberFeats = memberFeatRepository.findByMemberIdFeat(member);

        // 현제 아이는 방금 저장 했으므로 가장 마지막에 있는 데이터
        MemberFeat memberFeat = memberFeats.getLast();

        String text = memberFeat.getExtraExplainText();
        System.out.println(text);

        String resultText;
        String jsonText = "{\n" +
                "  \"messages\" : [ {\n" +
                "    \"role\" : \"system\",\n" +
                "    \"content\" : \"- 이것은 문장 성격 분류기 입니다\\n- 사용자의 글에서 주어진 단어 중 특징을 드러나는 단어 단 3개만 출력합니다\\n\\n- 주어진 단어 : 활발한 / 예의바른 / 사교적인 / 감성적인 / 잘웃는 / 차분한 / 독립적인 / 용감한 / 잘먹는 / 집중력있는 / 규칙을 잘지키는 / 신중한 / 도전적인 / 명량한 / 조심스러운 / 창의적인 / 마음이 따듯한 / 엉뚱한 / 유머러스한 / 다정한 / 수다스러운\\n\\n문장 : 우리 아이는 친구들을 잘 챙겨주고,  예의가 발라요 또한 먹는 걸 좋아해요\\n\\n감정 : 사교적인, 예의바른, 잘먹는\\n###\\n\"\n" +
                "  }, {\n" +
                "    \"role\" : \"user\",\n" +
                "    \"content\" : \"문장 : 우리 아이는 밝고 호기심이 넘치는 성격을 가지고 있습니다. 새로운 것에 대한 탐구심이 강해 항상 질문을 던지고, 다양한 활동에 적극적으로 참여합니다. 친구들과 잘 어울리며, 타인을 배려하는 마음을 지니고 있어 주변에서 사랑받고 있습니다. 가끔은 조금 내성적일 때도 있지만, 자신의 생각을 솔직하게 표현하는 용기가 있습니다. 긍정적이고 성실한 태도로 어려움을 극복하는 모습을 보여줍니다.\"\n" +
                "  }, {\n" +
                "    \"role\" : \"assistant\",\n" +
                "    \"content\" : \"감정 : 사교적인, 감성적인, 도전적인\"\n" +
                "  }, {\n" +
                "    \"role\" : \"user\",\n" +
                "    \"content\" : \"문장 : " + text + "\"\n" +
                "  } ],\n" +
                "  \"topP\" : 0.8,\n" +
                "  \"topK\" : 0,\n" +
                "  \"maxTokens\" : 256,\n" +
                "  \"temperature\" : 0.06,\n" +
                "  \"repeatPenalty\" : 5.0,\n" +
                "  \"stopBefore\" : [ ],\n" +
                "  \"includeAiFilters\" : true,\n" +
                "  \"seed\" : 0\n" +
                "}";

        resultText = completionService.executeC(jsonText).getBody();

        String[] lines = resultText.split("\n");

        // JSON 파싱에 사용할 변수들을 초기화합니다.
        boolean resultEventFound = false;
        StringBuilder jsonStringBuilder = new StringBuilder();

        // 줄 단위로 문자열을 순회합니다.
        for (String line : lines) {
            if (line.startsWith("event:result")) {
                resultEventFound = true;
                continue; // 다음 줄부터 JSON 데이터를 수집합니다.
            }

            if (resultEventFound && line.startsWith("data:")) {
                // "data:" 부분을 제거하고 JSON 데이터를 추가합니다.
                jsonStringBuilder.append(line.substring(5));
            } else if (resultEventFound) {
                // "data:"로 시작하지 않는 줄도 JSON 데이터로 추가합니다.
                jsonStringBuilder.append(line);
            }
        }

        if (!jsonStringBuilder.isEmpty()) {
            // 수집된 JSON 문자열을 파싱합니다.
            String jsonString = jsonStringBuilder.toString();
            JSONObject jsonObject = new JSONObject(jsonString);

            // "message" 객체 안의 "content" 값을 가져옵니다.
            resultText = jsonObject.getJSONObject("message").getString("content");

        } else {
            System.out.println("Error: 'event:result' not found or no data to parse.");
        }

        System.out.println(resultText);

        // 토큰을 저장할 리스트
        List<String> tokens = new ArrayList<>();

        // " : "을 기준으로 문자열을 나누고, 그 후에 ", "를 기준으로 다시 나눔
        String[] parts = resultText.split(" : ")[1].split(", ");

        // 필요한 토큰을 리스트에 추가
        for (String part : parts) {
            tokens.add(part);
        }

        memberFeat.setF1C(tokens.get(0));
        memberFeat.setF2C(tokens.get(1));
        memberFeat.setF3C(tokens.get(2));

        memberFeatRepository.save(memberFeat);

        // 결과 출력
        System.out.println("추출된 토큰: " + tokens);

        return resultText;
    }

    // TODO : 하모니 상세페이지 정보 DTO 전달

    // TODO : 하모니 정렬 알고리즘 테스트 (List 전달)

    // 모든 하모니 출력 [P]
    public List<MemberFeat> getAllHarmony(String type) {
        return memberFeatRepository.findByType("H");
    }
}
