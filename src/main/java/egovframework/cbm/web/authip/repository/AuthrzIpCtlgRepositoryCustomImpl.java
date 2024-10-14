package egovframework.cbm.web.authip.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import egovframework.cbm.web.authip.model.entity.AuthrzIpCtlg;
import egovframework.cbm.web.authip.model.entity.QAuthrzIpCtlg;
import egovframework.cbm.web.common.repository.CommonRepository;
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
public class AuthrzIpCtlgRepositoryCustomImpl implements AuthrzIpCtlgRepositoryCustom {

    private final CommonRepository<AuthrzIpCtlg> commonRepository;

    private static final QAuthrzIpCtlg qAuthrzIpCtlg = QAuthrzIpCtlg.authrzIpCtlg1;

    @Override
    public Page<AuthrzIpCtlg> findByFilters(String keyword, String ip, Pageable pageable) {

        BooleanExpression whereQuery = qAuthrzIpCtlg.authrzIpCtlg.containsIgnoreCase(ip);

        whereQuery = whereQuery.and(
                (qAuthrzIpCtlg.expln.containsIgnoreCase(keyword))
                        .or(qAuthrzIpCtlg.regstr.containsIgnoreCase(keyword))
                        .or(qAuthrzIpCtlg.mdfr.containsIgnoreCase(keyword))
        );

        return commonRepository.findByFilters(qAuthrzIpCtlg, whereQuery, pageable);

    }

}
