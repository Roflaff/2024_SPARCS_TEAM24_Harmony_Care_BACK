package rofla.back.harmonycareback.Service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rofla.back.harmonycareback.Jwt.JWTUtil;
import rofla.back.harmonycareback.Model.Member;
import rofla.back.harmonycareback.Repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final JWTUtil jwtUtil;

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

    public Boolean checkRole(HttpServletRequest http) {
        String accessHeaderValue = http.getHeader("access");
        // 시니어 유저 1
        if(jwtUtil.getRole(accessHeaderValue).equals("H")) {
            System.out.println("login User Role : H");
            return true;
        }
        //  부모님 유저 0
        else if(jwtUtil.getRole(accessHeaderValue).equals("P")){
            System.out.println("login User Role : P");
            return false;
        }
        // 관리자 유저 0
        else {
            return false;
        }
    }

    public List<Member> getALl() {
        return memberRepository.findAll();
    }

    public Optional<Member> searchByUsername(String username){
        return memberRepository.findByUsername(username);
    }
}
