package rofla.back.harmonycareback.Service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rofla.back.harmonycareback.Dto.HarmonyDetailDTORespone;
import rofla.back.harmonycareback.Jwt.JWTUtil;
import rofla.back.harmonycareback.Model.Member;
import rofla.back.harmonycareback.Model.MemberFeat;
import rofla.back.harmonycareback.Repository.MemberFeatRepository;
import rofla.back.harmonycareback.Repository.MemberRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final JWTUtil jwtUtil;
    private final MemberFeatRepository memberFeatRepository;

    private String username;
    private String role;
    private String token ;

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

    public HarmonyDetailDTORespone getHarmonyById(Long id, HttpServletRequest http) {
        // 절달 위한 DTO 객체 생성
        HarmonyDetailDTORespone harmonyDetailDTORespone = new HarmonyDetailDTORespone();

        this.token = http.getHeader("access");
        this.username = jwtUtil.getUsername(token);
        this.role = jwtUtil.getRole(token);
        String username = this.username; // 토큰 에서 출력한 아이디 (현제 로그인 한 사용자)
        String role = this.role;

        // 하모니 유저가 자기 정보 입력 후 자기 페이지 확인
        if(role.equals("H")) {
            // username으로 (token 으로) 찾아야 한다.
            Member member = memberRepository.findByUsername(username).get();
            MemberFeat memberFeat = memberFeatRepository.findByMemberIdFeat(member).getLast();

            // DTO 설정
            harmonyDetailDTORespone.setId(member.getId());
            harmonyDetailDTORespone.setExtraExplainText(memberFeat.getExtraExplainText());
            harmonyDetailDTORespone.setName(member.getName());

            // 나이 계산 로직
            LocalDate bir = member.getAge();
            LocalDate current = LocalDate.of(2024, 1, 1);

            int age = Period.between(bir, current).getYears();
            int birY = bir.getYear();
            String ageM = age + "세 " + birY + "년생";

            harmonyDetailDTORespone.setAge(ageM);
            harmonyDetailDTORespone.setRegin(member.getRegin());
            harmonyDetailDTORespone.setExplainText(memberFeat.getExplainText());
            harmonyDetailDTORespone.setF1H(memberFeat.getF1H());
            harmonyDetailDTORespone.setF2H(memberFeat.getF2H());
            harmonyDetailDTORespone.setF3H(memberFeat.getF3H());
            harmonyDetailDTORespone.setF1C(memberFeat.getF1C());
            harmonyDetailDTORespone.setF2C(memberFeat.getF2C());
            harmonyDetailDTORespone.setF3C(memberFeat.getF3C());
        }
        // 부모 유저가 하모니 리스트에서 하모니 객체 클릭시 확인
        else if(role.equals("P")) {
            // id로 찾아야 한다.
            Member member= memberRepository.findById(id).get();
            MemberFeat memberFeat = memberFeatRepository.findByMemberIdFeat(member).getLast();
            // DTO 설정
            harmonyDetailDTORespone.setId(member.getId());
            harmonyDetailDTORespone.setExtraExplainText(memberFeat.getExtraExplainText());
            harmonyDetailDTORespone.setName(member.getName());

            // 나이 계산 로직
            LocalDate bir = member.getAge();
            LocalDate current = LocalDate.of(2024, 1, 1);

            int age = Period.between(bir, current).getYears();
            int birY = bir.getYear();
            String ageM = age + "세 " + birY + "년생";

            harmonyDetailDTORespone.setAge(ageM);
            harmonyDetailDTORespone.setRegin(member.getRegin());
            harmonyDetailDTORespone.setExplainText(memberFeat.getExplainText());
            harmonyDetailDTORespone.setF1H(memberFeat.getF1H());
            harmonyDetailDTORespone.setF2H(memberFeat.getF2H());
            harmonyDetailDTORespone.setF3H(memberFeat.getF3H());
            harmonyDetailDTORespone.setF1C(memberFeat.getF1C());
            harmonyDetailDTORespone.setF2C(memberFeat.getF2C());
            harmonyDetailDTORespone.setF3C(memberFeat.getF3C());
        }


        return harmonyDetailDTORespone;
    }
}
