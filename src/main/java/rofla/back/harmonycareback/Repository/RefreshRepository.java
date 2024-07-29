package rofla.back.harmonycareback.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import rofla.back.harmonycareback.Model.RefreshMember;

public interface RefreshRepository extends JpaRepository<RefreshMember, Long> {
    Boolean existsByRefresh(String refresh);

    @Transactional
    void deleteByRefresh(String refresh);
}
