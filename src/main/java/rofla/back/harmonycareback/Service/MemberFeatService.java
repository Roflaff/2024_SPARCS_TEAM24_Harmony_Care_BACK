package rofla.back.harmonycareback.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rofla.back.harmonycareback.Model.Member;
import rofla.back.harmonycareback.Model.MemberFeat;
import rofla.back.harmonycareback.Repository.MemberFeatRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberFeatService {
    private final MemberFeatRepository memberFeatRepository;

    // 저장 로직
    public void addMemberFeat(MemberFeat memberFeat) {
        // 저장 할 객체의 아이디로 DB에 존재 하는 항목 확인
        List<MemberFeat> allMf = memberFeatRepository.findByMemberIdFeat(memberFeat.getMemberIdFeat().getUsername());
        // 유저 권한 확인
        String role = memberFeat.getMemberIdFeat().getRole();
        // 하모니 일 때
        if(role.equals("H")) {
            memberFeatRepository.save(memberFeat);
            
        }
    }
}
