package egovframework.cbm.web.authip.repository;

import egovframework.cbm.web.authip.model.entity.AuthrzIpCtlg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Entity, JpaRepository 상속받아 간단하게 구현.
 *
 * @author 이상민
 * @since  2024.05.21 16:30
 */
@Repository("AuthrzIpCtlgRepository")
public interface AuthrzIpCtlgRepository extends JpaRepository<AuthrzIpCtlg, Integer>, AuthrzIpCtlgRepositoryCustom {
}
