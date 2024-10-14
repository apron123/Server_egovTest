package egovframework.cbm.web.user.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import egovframework.cbm.web.user.model.dto.LoginHistDto;
import egovframework.cbm.web.user.model.dto.RoleMappingDto;
import egovframework.cbm.web.user.model.dto.TokenInfoDto;
import egovframework.cbm.web.user.model.dto.UserInfoDto;
import egovframework.cbm.web.user.model.entity.LoginHist;
import egovframework.cbm.web.user.model.entity.RoleMapping;
import egovframework.cbm.web.user.model.entity.TokenInfo;
import egovframework.cbm.web.user.model.entity.UserInfo;

/**
 * DTO & Entity 객체간의 매핑하는 객체
 * componentModel="spring"을 통해서 spring container에 Bean으로 등록
 * unmappedTargetPolicy IGNORE 만약, target class에 매핑되지 않는 필드가 있으면, null로 넣게 되고,
 * 따로 report하지 않음
 *
 * @author 이상민
 * @since 2024.06.13 16:30
 */

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "roleCdNm", source = "cmncdInfo.cdNm")
    UserInfoDto toDto(UserInfo entity);

    UserInfo toEntity(UserInfoDto dto);

    TokenInfoDto toDto(TokenInfo entity);

    TokenInfo toEntity(TokenInfoDto dto);

    LoginHistDto toDto(LoginHist entity);

    LoginHist toEntity(LoginHistDto dto);

    @Mapping(source = "roleMappingSeq", target = "roleMappingSeq")
    @Mapping(source = "userRoleCode", target = "userRoleCode")
    @Mapping(source = "systemCode", target = "systemCode")
    List<RoleMappingDto> toDto(List<RoleMapping> entity);

    @Mapping(source = "roleMappingSeq", target = "roleMappingSeq")
    @Mapping(source = "userRoleCode", target = "userRoleCode")
    @Mapping(source = "systemCode", target = "systemCode")
    List<RoleMapping> toEntity(List<RoleMappingDto> dto);

    RoleMappingDto toDto(RoleMapping entity);

    RoleMapping toEntity(RoleMappingDto dto);

}