package egovframework.cbm.web.user.repository.tokeninfo;

import egovframework.cbm.web.user.model.entity.TokenInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository("tokenInfoRepository")
public interface TokenInfoRepository extends JpaRepository<TokenInfo, Integer> {

    Optional<TokenInfo> findByUserId(String userId);

    @Transactional
    void deleteByUserId(String userId);

}
