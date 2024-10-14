package egovframework.cbm.web.dctn.repository.domaininfo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.dsl.BooleanExpression;

import egovframework.cbm.web.common.repository.CommonRepository;
import egovframework.cbm.web.dctn.model.entity.DctnDomainInfo;
import egovframework.cbm.web.dctn.model.entity.QDctnDomainInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Query Dsl Override 매서드 작성
 * QEntity 서치 에러시, mvn clean compile 할 것
 *
 * @author 이상민
 * @since 2024.05.21 16:30
 */
@Slf4j
@RequiredArgsConstructor
public class DctnDomainInfoRepositoryCustomImpl implements DctnDomainInfoRepositoryCustom {

    private final CommonRepository<DctnDomainInfo> commonRepository;

    private static final QDctnDomainInfo qDcdnDomainInfo = QDctnDomainInfo.dctnDomainInfo;

    @Override
    public Page<DctnDomainInfo> findByFilters(String keyword, String classification, Pageable pageable) {

        BooleanExpression whereQuery = qDcdnDomainInfo.apprvPtclsDomain.apprvDttm.isNotNull()
                .and(qDcdnDomainInfo.apprvPtclsDomain.apprv.isNotEmpty())
                .and(qDcdnDomainInfo.cmncdInfo.cd.containsIgnoreCase(classification));

        whereQuery = whereQuery.and(
                (qDcdnDomainInfo.domainNm.containsIgnoreCase(keyword))
                        .or(qDcdnDomainInfo.domainClsfctWord.containsIgnoreCase(keyword))
                        .or(qDcdnDomainInfo.logicDataTypeInfo.cdNm.containsIgnoreCase(keyword))
                        .or(qDcdnDomainInfo.domainTypeInfo.cdNm.containsIgnoreCase(keyword)));

        return commonRepository.findByFilters(qDcdnDomainInfo, whereQuery, pageable, qDcdnDomainInfo.logicDataTypeInfo,
                qDcdnDomainInfo.domainTypeInfo, qDcdnDomainInfo.cmncdInfo, qDcdnDomainInfo.apprvPtclsDomain);

    }

}
