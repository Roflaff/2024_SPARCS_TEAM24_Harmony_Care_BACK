package rofla.back.harmonycareback.Service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rofla.back.harmonycareback.Dto.JoinDTO;
import rofla.back.harmonycareback.Model.Member;
import rofla.back.harmonycareback.Repository.MemberRepository;

// 회원 가입 로직
@Service
@AllArgsConstructor
public class JoinService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinProcess(JoinDTO joinDTO) {

        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();

        Boolean isExist = memberRepository.existsByUsername(username);

        if (isExist) {
            // 동일한 아이디 존재 시 오류
            return;
        }

        Member data = new Member();
        data.setUsername(username);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setName(joinDTO.getName());
        data.setRole(joinDTO.getRole());
        data.setAge(joinDTO.getAge());
        data.setRegin(joinDTO.getRegin());
        data.setPhoneNum(joinDTO.getPhoneNum());

        memberRepository.save(data);
    }

    // 인증 로직
    public String searchUsername(String username) {
        Boolean isExist = memberRepository.existsByUsername(username);
        if(isExist) {
            return "동일한 아이디 존재";
        }
        else {
            return "사용 가능한 아이디";
        }
    }
}