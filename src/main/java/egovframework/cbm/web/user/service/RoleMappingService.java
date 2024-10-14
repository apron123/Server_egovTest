package egovframework.cbm.web.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.cbm.web.user.model.dto.RoleMappingDto;
import egovframework.cbm.web.user.model.entity.RoleMapping;
import egovframework.cbm.web.user.model.mapper.UserMapper;
import egovframework.cbm.web.user.repository.rolemapping.RoleMappingRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service("roleMappingService")
public class RoleMappingService {
    @Autowired
    private RoleMappingRepository roleMappingRepository;

    @Autowired
    private UserMapper mapper;

    /**
     * DB에서 URL과 해당 권한을 가져옴.
     */
    public Map<String, List<String>> getUrlToRoleMappings() {
        List<RoleMapping> permissions = roleMappingRepository.findAll();
        Map<String, List<String>> urlRoleMap = new HashMap<>();
        List<RoleMappingDto> dtoList = mapper.toDto(permissions);

        for (RoleMappingDto dto : dtoList) {
            if (dto.getUserRoleCode() != null || dto.getSystemCode() != null) {
                String role = dto.getUserRoleCode();
                String system = "/" + dto.getSystemCode() + "/**";

                // 해당 role이 맵에 없으면 새로운 리스트 생성
                urlRoleMap.putIfAbsent(role, new ArrayList<>());

                // 해당 role에 system code 추가
                urlRoleMap.get(role).add(system);
            }
        }
        return urlRoleMap;
    }

    /*
     * URL 목록
     */
    public boolean urlAllList(String requestURI, String roleCode) {

        var roleList = roleMappingRepository.findByUserRoleCode(roleCode);
        var roleAllList = roleMappingRepository.findAll();

        List<RoleMappingDto> dtoList = mapper.toDto(roleList);
        List<RoleMappingDto> dtoAllList = mapper.toDto(roleAllList);

        for (RoleMappingDto dto : dtoList) {
            if (dto.getUserRoleCode() != null || dto.getSystemCode() != null) {
                String system = "/" + dto.getSystemCode() + "/.*";

                if (requestURI.matches(system)) {
                    return true;
                }
            }
        }
        int i = 0;
        for (RoleMappingDto dto : dtoAllList) {
            String system = "/" + dto.getSystemCode() + "/.*";
            if (requestURI.matches(system)) {
                return false;
            }
            i++;
        }

        if (i == dtoAllList.size()) {
            return true;
        }

        return false;

    }

    public boolean urlList(String requestURI) {
        var roleAllList = roleMappingRepository.findAll();
        int i = 0;
        List<RoleMappingDto> dtoAllList = mapper.toDto(roleAllList);
        for (RoleMappingDto dto : dtoAllList) {
            String system = "/" + dto.getSystemCode() + "/.*";
            if (requestURI.matches(system)) {
                return false;
            }
            i++;
        }

        if (i == dtoAllList.size()) {
            return true;
        }

        return false;
    }

    /**
     * 데이터를 저장 또는 수정하는 메서드
     *
     * @param id             role Mapping ID
     * @param roleMappingDto roleMapping 데이터
     */
    public void saveRoleMapping(int id, RoleMappingDto roleMappingDto) throws Exception {
        roleMappingRepository.save(mapper.toEntity(roleMappingDto.upsertFlag(id)));
    }

}
