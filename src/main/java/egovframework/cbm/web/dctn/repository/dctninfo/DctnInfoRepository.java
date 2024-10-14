package egovframework.cbm.web.dctn.repository.dctninfo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import egovframework.cbm.web.dctn.model.entity.DctnInfo;

/**
 * Entity, JpaRepository 상속받아 간단하게 구현.
 *
 * @author 이상민
 * @since 2024.05.21 16:30
 */
@Repository("dctnInfoRepository")
public interface DctnInfoRepository extends JpaRepository<DctnInfo, Integer>, DctnInfoRepositoryCustom {

    List<DctnInfo> findByApprvPtcls_ApprvIsNullAndApprvPtcls_ApprvDttmIsNull();
}
