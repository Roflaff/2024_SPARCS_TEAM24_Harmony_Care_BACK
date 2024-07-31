package rofla.back.harmonycareback.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rofla.back.harmonycareback.Model.Member;
import rofla.back.harmonycareback.Model.MemberFeat;
import rofla.back.harmonycareback.Repository.MemberFeatRepository;

import java.util.Comparator;
import java.util.List;

// 부모들이 시니어 추천 받는 로직
@RequiredArgsConstructor
@Service
public class ParentService {
    //TODO : 자식 정보 받아서 해당 정보 값으로 잘 어울리는 시니어 리스트로 반환.
    // 이 때 추천 받을 때 1순위 지역 2순위 공통점 일치 순으로 오름 차순 나열
    // 리스트로 반환
    private final MemberFeatService memberFeatService;
    private final MemberFeatRepository memberFeatRepository;

    // 하모니 특징 중 선호 하는 성격 출력
    // 부모 정보가 매개 변수로 온다.
    public List<MemberFeat> getAllHofMemberFeat(Member member){
        // 1. 모든 하모니 list 에 저장
        List<MemberFeat> listOfH = memberFeatService.getAllHarmony("H");

        // 2. 해당 부모에 속한 모든 아이 리스트 저장
        List<MemberFeat> listOfC = memberFeatRepository.findByMemberIdFeat(member);

        // 3. 먼저 부모 거주지로 리스트 정렬 (문자열 간 유사도 판변)
        String reginOfP = member.getRegin(); // 부모 거주 지역

        // LevenshteinDistance 정렬
        listOfH.sort((mf1, mf2) -> {
            int match1 = getMatchScore(reginOfP, mf1.getMemberIdFeat().getRegin());
            int match2 = getMatchScore(reginOfP, mf2.getMemberIdFeat().getRegin());
            return Integer.compare(match2, match1); // 높은 일치도를 우선 정렬
        });

        System.out.println(listOfH);

        return listOfH;
    }
    // 일치 점수 계산
    private static int getMatchScore(String parentRegion, String region) {
        int score = 0;
        if (parentRegion.equals(region)) {
            score += 100; // 완벽히 일치
        } else if (region.contains(parentRegion)) {
            score += 50; // 부모 지역 정보가 포함됨
        } else if (parentRegion.contains(region)) {
            score += 25; // 부모 지역 정보가 부분적으로 포함됨
        }
        return score;
    }
}
