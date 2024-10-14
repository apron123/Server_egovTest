package egovframework.cbm.web.dctn.repository.domaininfo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import egovframework.cbm.web.dctn.model.entity.DctnDomainInfo;

/**
 * Entity, JpaRepository 상속받아 간단하게 구현.
 *
 * @author 이상민
 * @since 2024.05.21 16:30
 */
@Repository("dctnDomainInfoRepository")
public interface DctnDomainInfoRepository
        extends JpaRepository<DctnDomainInfo, Integer>, DctnDomainInfoRepositoryCustom {

    List<DctnDomainInfo> findByApprvPtclsDomain_ApprvIsNullAndApprvPtclsDomain_ApprvDttmIsNull();
}
