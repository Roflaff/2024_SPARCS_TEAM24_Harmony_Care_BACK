package rofla.back.harmonycareback.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rofla.back.harmonycareback.Model.Member;
import rofla.back.harmonycareback.Repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // 회원가입
    @Transactional
    public void joinMember(Member member) {
        // 비밀번호가 null인지 확인
        if(memberRepository.findByUsername(member.getUsername()).isPresent()) {
            throw new IllegalArgumentException("동일한 아이디 유저 존재");
        }

        if (member.getPassword() == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
        memberRepository.save(member);
    }
}
