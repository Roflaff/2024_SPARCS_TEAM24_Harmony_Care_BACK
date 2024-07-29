package rofla.back.harmonycareback.Service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rofla.back.harmonycareback.Dto.JoinDTO;
import rofla.back.harmonycareback.Model.Member;
import rofla.back.harmonycareback.Repository.MemberRepository;

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
            return;
        }

        Member data = new Member();
        data.setUsername(username);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setRole("ADMIN");

        memberRepository.save(data);
    }
}