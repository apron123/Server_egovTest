package egovframework.cbm.web.dctn.repository.dctninfo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.dsl.BooleanExpression;

import egovframework.cbm.web.common.repository.CommonRepository;
import egovframework.cbm.web.dctn.model.entity.DctnInfo;
import egovframework.cbm.web.dctn.model.entity.QDctnInfo;
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
public class DctnInfoRepositoryCustomImpl implements DctnInfoRepositoryCustom {

    private final CommonRepository<DctnInfo> commonRepository;

    private static final QDctnInfo qDctnInfo = QDctnInfo.dctnInfo;

    @Override
    public Page<DctnInfo> findByFilters(String keyword, String classification, char eqYn, Pageable pageable) {

        BooleanExpression whereQuery = qDctnInfo.apprvPtcls.apprvDttm.isNotNull()
                .and(qDctnInfo.apprvPtcls.apprv.isNotEmpty())
                .and(qDctnInfo.cmncdInfo.cd.containsIgnoreCase(classification));

        if (eqYn == 'N') {
            whereQuery = whereQuery.and(
                    (qDctnInfo.wordNm.containsIgnoreCase(keyword))
                            .or(qDctnInfo.wordExpln.containsIgnoreCase(keyword))
                            .or(qDctnInfo.abbrnm.containsIgnoreCase(keyword))
                            .or(qDctnInfo.engnm.containsIgnoreCase(keyword)));
        } else {
            whereQuery = whereQuery.and((qDctnInfo.wordNm.equalsIgnoreCase(keyword)));
        }

        return commonRepository.findByFilters(qDctnInfo, whereQuery, pageable, qDctnInfo.cmncdInfo,
                qDctnInfo.apprvPtcls);

    }

}
