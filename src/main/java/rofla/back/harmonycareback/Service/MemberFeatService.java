package rofla.back.harmonycareback.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rofla.back.harmonycareback.Dto.addMemberFeatDTO;
import rofla.back.harmonycareback.Model.Member;
import rofla.back.harmonycareback.Model.MemberFeat;
import rofla.back.harmonycareback.Repository.MemberFeatRepository;
import rofla.back.harmonycareback.Repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberFeatService {
    private final MemberFeatRepository memberFeatRepository;
    private final MemberRepository memberRepository;

    // 저장 로직
    public void addMemberFeat(addMemberFeatDTO memberFeat) {
        // 저장 할 객체의 아이디로 DB에 존재 하는 항목 확인
        String username = memberFeat.getUsername();
        System.out.println("dddd "+username);
        List<MemberFeat> allMf = memberFeatRepository.findByMemberIdFeat(memberRepository.findByUsername(username).get());
        // 유저 권한 확인
        String role = memberFeat.getType();
        // 하모니 일 때
        if(role.equals("H")) {
            MemberFeat memberFeat1 = new MemberFeat();
            memberFeat1.setMemberIdFeat(memberRepository.findByUsername(username).get());
            memberFeat1.setType(memberFeat.getType());
            memberFeatRepository.save(memberFeat1);
        }
        // 부모님 일 때
        else if(role.equals("P")) {
            // 자식이 있으면
            if(!allMf.isEmpty()) {
                int i = allMf.size();
                String temp = memberFeat.getType();
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
                memberFeat.setType(temp);
            }
            MemberFeat memberFeat1 = new MemberFeat();
            memberFeat1.setMemberIdFeat(memberRepository.findByUsername(username).get());
            memberFeat1.setType(memberFeat.getType());
            memberFeatRepository.save(memberFeat1);
        }
    }

    public List<MemberFeat> getAllHarmony(String type) {
        return memberFeatRepository.findByType("H");
    }
}
