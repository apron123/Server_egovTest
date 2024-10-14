package egovframework.cbm.web.user.repository.userinfo;

import com.querydsl.core.types.dsl.BooleanExpression;
import egovframework.cbm.web.common.repository.CommonRepository;
import egovframework.cbm.web.user.model.entity.QUserInfo;
import egovframework.cbm.web.user.model.entity.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Query Dsl Override 매서드 작성
 * QEntity 서치 에러시, mvn clean compile 할 것
 *
 * @author 이상민
 * @since 2024.05.21 16:30
 */
@Slf4j
@RequiredArgsConstructor
public class UserInfoRepositoryCustomImpl implements UserInfoRepositoryCustom {

    private final CommonRepository<UserInfo> commonRepository;

    private static final QUserInfo qUserInfo = QUserInfo.userInfo;

    @Override
    public Page<UserInfo> findByFilters(String keyword, Pageable pageable) {

        BooleanExpression whereQuery = qUserInfo.userNm.containsIgnoreCase(keyword)
                .or(qUserInfo.userId.containsIgnoreCase(keyword));

        return commonRepository.findByFilters(qUserInfo, whereQuery, pageable, qUserInfo.cmncdInfo);

    }

}
