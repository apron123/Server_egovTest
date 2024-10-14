package egovframework.cbm.web.dctn.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import egovframework.cbm.web.dctn.model.dto.ApprvPtclsDto;
import egovframework.cbm.web.dctn.model.dto.DctnDomainInfoDto;
import egovframework.cbm.web.dctn.model.dto.DctnInfoDto;
import egovframework.cbm.web.dctn.model.entity.ApprvPtcls;
import egovframework.cbm.web.dctn.model.entity.DctnDomainInfo;
import egovframework.cbm.web.dctn.model.entity.DctnInfo;

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
public interface DataDictionaryMapper {

    DctnInfo toEntity(DctnInfoDto dto);

    @Mapping(target = "cdNm", source = "cmncdInfo.cdNm")
    DctnInfoDto toDto(DctnInfo entity);

    List<DctnInfo> toEntity(List<DctnInfoDto> dtoList);

    List<DctnInfoDto> toDto(List<DctnInfo> entityList);

    ApprvPtcls toEntity(ApprvPtclsDto dto);

    ApprvPtclsDto toDto(ApprvPtcls entity);

    DctnDomainInfo toEntity(DctnDomainInfoDto dto);

    @Mapping(target = "logicDataTypeCdNm", source = "logicDataTypeInfo.cdNm")
    @Mapping(target = "domainTypeCdNm", source = "domainTypeInfo.cdNm")
    @Mapping(target = "cdNm", source = "cmncdInfo.cdNm")
    DctnDomainInfoDto toDto(DctnDomainInfo entity);

    List<DctnDomainInfoDto> toDtoList(List<DctnDomainInfo> entityList);

}
