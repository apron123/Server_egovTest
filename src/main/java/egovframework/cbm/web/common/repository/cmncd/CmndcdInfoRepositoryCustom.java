package egovframework.cbm.web.common.repository.cmncd;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import egovframework.cbm.web.common.model.entity.CmncdInfo;

public interface CmndcdInfoRepositoryCustom {

    Page<CmncdInfo> findByFilters(String keyword, Pageable pageable);

    Page<CmncdInfo> findByFilters(String grpCd, String keyword, Pageable pageable);

    long updateByGrpCd(String cd, String grpCd);

    long deleteByGrpCd(String grpCd);

}
