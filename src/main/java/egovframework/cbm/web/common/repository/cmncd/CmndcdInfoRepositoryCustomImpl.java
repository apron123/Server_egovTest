package egovframework.cbm.web.common.repository.cmncd;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import egovframework.cbm.web.common.model.entity.CmncdInfo;
import egovframework.cbm.web.common.model.entity.QCmncdInfo;
import egovframework.cbm.web.common.repository.CommonRepository;
import egovframework.utils.enums.Yn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Slf4j
@RequiredArgsConstructor
public class CmndcdInfoRepositoryCustomImpl implements CmndcdInfoRepositoryCustom {

    public final CommonRepository<CmncdInfo> commonRepository;

    private final JPAQueryFactory queryFactory;

    private static final QCmncdInfo qCmncdInfo = QCmncdInfo.cmncdInfo;

    @Override
    public Page<CmncdInfo> findByFilters(String keyword, Pageable pageable) {

        BooleanExpression whereQuery = qCmncdInfo.grpYn.eq(Yn.Y.getC())
                .and(qCmncdInfo.grpCd.containsIgnoreCase(keyword));

        return commonRepository.findByFilters(qCmncdInfo, whereQuery, pageable);
    }

    @Override
    public Page<CmncdInfo> findByFilters(String grpCd, String keyword, Pageable pageable) {

        BooleanExpression whereQuery = qCmncdInfo.grpYn.eq(Yn.N.getC())
                .and(qCmncdInfo.grpCd.eq(grpCd))
                .and(qCmncdInfo.grpCd.containsIgnoreCase(keyword));

        return commonRepository.findByFilters(qCmncdInfo, whereQuery, pageable);
    }


    @Override
    public long updateByGrpCd(String cd, String grpCd) {
        return queryFactory.update(qCmncdInfo)
                .set(qCmncdInfo.grpCd, grpCd)
                .where(qCmncdInfo.grpCd.eq(cd))
                .execute();
    }

    @Override
    public long deleteByGrpCd(String grpCd) {
        return queryFactory.delete(qCmncdInfo)
                .where(qCmncdInfo.grpCd.eq(grpCd))
                .execute();
    }

}
