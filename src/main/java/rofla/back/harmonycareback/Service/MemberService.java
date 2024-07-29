package rofla.back.harmonycareback.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rofla.back.harmonycareback.Model.Member;
import rofla.back.harmonycareback.Repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // 회원가입
    public void joinMember(Member member) {
        if(memberRepository.findByUsername(member.getUsername()).isPresent()) {
            System.out.println("존재하는 아이디");
        }
        else {
            memberRepository.save(member);
        }
    }
}
