package rofla.back.harmonycareback.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rofla.back.harmonycareback.Model.Member;
import rofla.back.harmonycareback.Model.MemberFeat;

import java.util.List;

@Repository
public interface MemberFeatRepository extends JpaRepository<MemberFeat, Long> {
    List<MemberFeat> findByMemberIdFeat(Member memberIdFeat);
}
