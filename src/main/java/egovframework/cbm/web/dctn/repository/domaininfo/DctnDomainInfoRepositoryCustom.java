package egovframework.cbm.web.dctn.repository.domaininfo;

import egovframework.cbm.web.dctn.model.entity.DctnDomainInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Query Dsl 메소드 작성
 *
 * @author 이상민
 * @since  2024.05.21 16:30
 */
public interface DctnDomainInfoRepositoryCustom {

    Page<DctnDomainInfo> findByFilters(String keyword,String classification, Pageable pageable);

}
