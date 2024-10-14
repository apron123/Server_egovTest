package egovframework.cbm.web.user.repository.rolemapping;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import egovframework.cbm.web.user.model.entity.RoleMapping;

public interface RoleMappingRepository extends JpaRepository<RoleMapping, Integer> {

    Optional<RoleMapping> findOneByUserRoleCode(String userId);

    List<RoleMapping> findByUserRoleCode(String roleCode);

}
