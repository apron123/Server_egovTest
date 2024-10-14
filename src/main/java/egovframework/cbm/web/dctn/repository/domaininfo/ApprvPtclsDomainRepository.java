package egovframework.cbm.web.dctn.repository.domaininfo;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import egovframework.cbm.web.dctn.model.entity.ApprvPtclsDomain;

/**
 * Entity, JpaRepository 상속받아 간단하게 구현.
 *
 * @author 이상민
 * @since 2024.05.21 16:30
 */
@Repository("apprvPtclsDomainRepository")
public interface ApprvPtclsDomainRepository extends JpaRepository<ApprvPtclsDomain, Integer> {

    Optional<ApprvPtclsDomain> findByDctnDomainInfoSeq(int dctnInfoSeq);

    Set<ApprvPtclsDomain> findAllByDctnDomainInfoSeqIn(Set<Integer> dctnInfoList);

}
