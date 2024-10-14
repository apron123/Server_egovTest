package egovframework.cbm.web.authip.repository;

import egovframework.cbm.web.authip.model.entity.AuthrzIpCtlg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Query Dsl 메소드 작성
 *
 * @author 이상민
 * @since  2024.05.21 16:30
 */
public interface AuthrzIpCtlgRepositoryCustom {

    Page<AuthrzIpCtlg> findByFilters(String keyword, String ip, Pageable pageable);

}
