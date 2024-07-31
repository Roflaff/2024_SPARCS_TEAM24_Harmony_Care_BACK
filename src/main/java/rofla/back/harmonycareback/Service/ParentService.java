package rofla.back.harmonycareback.Service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.stereotype.Service;
import rofla.back.harmonycareback.Model.Member;
import rofla.back.harmonycareback.Model.MemberFeat;
import rofla.back.harmonycareback.Repository.MemberFeatRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// 부모들이 시니어 추천 받는 로직
@RequiredArgsConstructor
@Service
public class ParentService {
    private final MemberFeatService memberFeatService;
    private final MemberFeatRepository memberFeatRepository;

    // 하모니 특징 중 선호 하는 성격 출력
    public List<MemberFeat> getAllHofMemberFeat(Member member) {
        // 1. 모든 하모니 list 에 저장
        List<MemberFeat> listOfH = memberFeatService.getAllHarmony("H");

        // 2. 해당 부모에 속한 아이 저장
        MemberFeat listOfC = memberFeatRepository.findByMemberIdFeat(member).getLast();

        // 3. 부모 거주지
        String reginOfP = member.getRegin();

        // 4. 정렬 로직: 먼저 부모 거주지로 정렬하고, 같은 지역 내에서 특성 값으로 정렬
        listOfH = listOfH.stream()
                .sorted(Comparator.comparing((MemberFeat mf) -> getMatchScore(reginOfP, mf.getMemberIdFeat().getRegin()))
                        .thenComparing((MemberFeat mf) -> getFeatureMatchScore(listOfC, mf), Comparator.reverseOrder()))
                .collect(Collectors.toList());

        return listOfH;
    }

    // 부모와 시니어의 지역 일치 점수 계산
    private static int getMatchScore(String parentRegion, String region) {
        LevenshteinDistance levenshtein = new LevenshteinDistance();
        int distance = levenshtein.apply(parentRegion, region);
        return distance;
    }

    // listOfC의 성격 값과 MemberFeat의 성격 값 일치 점수 계산
    private static int getFeatureMatchScore(MemberFeat listOfC, MemberFeat mf) {
        List<String> parentTraits = List.of(listOfC.getF1C(), listOfC.getF2C(), listOfC.getF3C());
        List<String> memberTraits = List.of(mf.getF1C(), mf.getF2C(), mf.getF3C());

        int score = 0;
        for (String trait : parentTraits) {
            if (memberTraits.contains(trait)) {
                score++;
            }
        }

        return score;
    }
}
