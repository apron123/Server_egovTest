package egovframework.cbm.web.dctn.repository.dctninfo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import egovframework.cbm.web.dctn.model.entity.DctnInfo;

/**
 * Query Dsl 메소드 작성
 *
 * @author 이상민
 * @since 2024.05.21 16:30
 */
public interface DctnInfoRepositoryCustom {

    Page<DctnInfo> findByFilters(String keyword, String classification, char eqYn, Pageable pageable);

}
