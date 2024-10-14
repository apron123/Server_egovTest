package egovframework.cbm.web.dctn.repository.dctninfo;

import egovframework.cbm.web.dctn.model.entity.ApprvPtcls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

/**
 * Entity, JpaRepository 상속받아 간단하게 구현.
 *
 * @author 이상민
 * @since  2024.05.21 16:30
 */
@Repository("apprvPtclsRepository")
public interface ApprvPtclsRepository extends JpaRepository<ApprvPtcls, Integer> {

    Optional<ApprvPtcls> findByDctnInfoSeq(int dctnInfoSeq);

    Set<ApprvPtcls> findAllByDctnInfoSeqIn(Set<Integer> dctnInfoList);

}
