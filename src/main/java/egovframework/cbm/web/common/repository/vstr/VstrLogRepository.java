package egovframework.cbm.web.common.repository.vstr;

import egovframework.cbm.web.common.model.entity.VstrLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("vstrLogRepository")
public interface VstrLogRepository extends JpaRepository<VstrLog, Integer>, VstrLogRepositoryCustom {

    long count();

    long countByUserId(String userId);

}
