package egovframework.cbm.web.common.repository.cmncd;

import egovframework.cbm.web.common.model.entity.CmncdInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("cmncdInfoRepository")
public interface CmncdInfoRepository extends JpaRepository<CmncdInfo, Integer>, CmndcdInfoRepositoryCustom {

    List<CmncdInfo> findByGrpCdAndGrpYn(String grpCd, char grpYn);

    Optional<CmncdInfo> findByCmncdInfoSeqAndGrpYn(int seq, char grpYn);

    int countByGrpYnAndGrpCd(char grpYn, String GrpCd);

}
