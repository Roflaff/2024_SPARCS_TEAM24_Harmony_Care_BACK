package rofla.back.harmonycareback.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rofla.back.harmonycareback.Dto.CustomUserDetails;
import rofla.back.harmonycareback.Model.Member;
import rofla.back.harmonycareback.Repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByUsername(username).get();
        if (username != null) {
            return new CustomUserDetails(member);
        }

        return null;
    }


}