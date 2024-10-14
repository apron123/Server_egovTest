package egovframework.cbm.web.user.repository.userinfo;

import egovframework.cbm.web.user.model.entity.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Query Dsl 메소드 작성
 *
 * @author 이상민
 * @since  2024.05.21 16:30
 */
public interface UserInfoRepositoryCustom {

    Page<UserInfo> findByFilters(String keyword, Pageable pageable);

}
