package egovframework.cbm.web.common.model.mapper;

import egovframework.cbm.web.common.model.dto.CmncdInfoDto;
import egovframework.cbm.web.common.model.entity.CmncdInfo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

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
public interface CommonMapper {

    CmncdInfo toEntity(CmncdInfoDto dto);

    CmncdInfoDto toDto(CmncdInfo entity);

    List<CmncdInfoDto> toListDto(List<CmncdInfo> entity);

}
