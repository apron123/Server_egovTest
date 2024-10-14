package egovframework.cbm.web.user.repository.userinfo;

import egovframework.cbm.web.user.model.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository("userInfoRepository")
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer>, UserInfoRepositoryCustom {

    Optional<UserInfo> findOneByUserIdAndUserPwAndUseYn(String userId, String userPassword, char useAt);

    List<UserInfo> findByUserNm(String userName);

    Optional<UserInfo> findOneByUserId(String userId);

    Optional<UserInfo> findByUserCntadr(String Cntadr);

    Optional<UserInfo> findOneByUserIdAndUserNm(String userId, String userName);

    Optional<UserInfo> findByUserEmail(String userEmail);

    Set<UserInfo> findAllByUserInfoSeqIn(Set<Integer> userInfoSeqs);

}
