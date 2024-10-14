package egovframework.cbm.web.user.repository.loginhist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import egovframework.cbm.web.user.model.entity.LoginHist;

@Repository("loginHistRepository")
public interface LoginHistRepository extends JpaRepository<LoginHist, Integer> {

}
