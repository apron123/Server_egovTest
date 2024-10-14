package egovframework.cbm.web.common.service;

import egovframework.cbm.web.common.model.dto.CmncdInfoDto;
import egovframework.cbm.web.common.model.dto.FrontVaildateDto;
import egovframework.cbm.web.common.model.mapper.CommonMapper;
import egovframework.cbm.web.common.repository.cmncd.CmncdInfoRepository;
import egovframework.utils.Constants;
import egovframework.utils.Utils;
import egovframework.utils.enums.Yn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static egovframework.cbm.config.database.DatabaseConstants.BaseDatabase.tx_manager;

@Service
@RequiredArgsConstructor
public class CommonService {

    private final CmncdInfoRepository cmncdInfoRepository;

    private final CommonMapper mapper;

    /**
     * validateDto 생성하는  메서드
     *
     * @param  clazz                         생성할 dto class
     * @return Map<String, FrontVaildateDto> Validate Map 데이터
     */
    @Transactional(transactionManager = tx_manager, readOnly = true)
    public Map<String, FrontVaildateDto> createFrontValidateList(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Schema.class))
                .map(field -> {
                    Schema schema = field.getAnnotation(Schema.class);
                    FrontVaildateDto frontVaildateDto = FrontVaildateDto.builder()
                            .key(Utils.camelToSnake(field.getName()))
                            .name(schema.description())
                            .build()
                            .checkNotNull(field.isAnnotationPresent(NotBlank.class)
                                    || field.isAnnotationPresent(NotNull.class)
                                    || field.isAnnotationPresent(NotEmpty.class))
                            .checkSize(field.getAnnotation(Size.class))
                            .checkReadOnly(schema.accessMode() == Schema.AccessMode.READ_ONLY);
                    Optional.ofNullable(field.getAnnotation(Pattern.class))
                            .ifPresent(pattern -> {
                                if (pattern.regexp().equals(Constants.REGEXP_YN)) {
                                    Arrays.stream(Yn.values()).forEach(
                                            yn -> frontVaildateDto.addCmncd(String.valueOf(yn.getC()), yn.getName()));
                                } else if (schema.format() != null) {
                                    cmncdInfoRepository.findByGrpCdAndGrpYn(schema.format(), Yn.N.getC())
                                            .forEach(cmncdInfo -> frontVaildateDto.addCmncd(cmncdInfo.getCd(),
                                                    cmncdInfo.getCdNm()));
                                }
                            });
                    return frontVaildateDto;
                })
                .collect(Collectors.toMap(FrontVaildateDto::getKey, frontVaildateDto -> frontVaildateDto));
    }

    /**
     * 검색 필터 데이터 기준으로 페이지 객체 생성하는 메서드
     *
     * @param keyword             검색 키워드
     * @return Page<CmncdInfoDto> 페이지 데이터
     */
    @Transactional(transactionManager = tx_manager, readOnly = true)
    public Page<CmncdInfoDto> getGrpPage(String keyword, Pageable pageable) throws Exception {
        return cmncdInfoRepository.findByFilters(keyword, pageable)
                .map(cmncdInfo -> mapper.toDto(cmncdInfo)
                        .setCdCnt(cmncdInfoRepository.countByGrpYnAndGrpCd(Yn.N.getC(), cmncdInfo.getGrpCd())));
    }

    /**
     * 검색 필터 데이터 기준으로 페이지 객체 생성하는 메서드
     *
     * @param grpCd               그룹 코드
     * @return Page<CmncdInfoDto> 페이지 데이터
     */
    @Transactional(transactionManager = tx_manager, readOnly = true)
    public Page<CmncdInfoDto> getPage(String grpCd, String keyword, Pageable pageable) throws Exception {
        return cmncdInfoRepository.findByFilters(grpCd, keyword, pageable)
                .map(mapper::toDto);
    }

    /**
     * 공통코드 그룹을 id값 기준으로 조회하는 메서드
     *
     * @param id 데이터 ID
     * @return CmncdInfoDto CmncdInfo 데이터
     */
    @Transactional(transactionManager = tx_manager, readOnly = true)
    public CmncdInfoDto getCmncdInfo(int id) throws Exception {
        return cmncdInfoRepository.findByCmncdInfoSeqAndGrpYn(id, Yn.Y.getC())
                .map(mapper::toDto)
                .orElse(null);
    }

    /**
     * 공통코드 그룹을 저장하는 메서드
     *
     * @param cmncdInfoDto cmncdInfoDto 데이터
     */
    @Transactional(transactionManager = tx_manager)
    public void craateGrpCmncd(CmncdInfoDto cmncdInfoDto) throws Exception {
        cmncdInfoRepository.save(mapper.toEntity(cmncdInfoDto.upsertGrpFlag(0)));
    }

    /**
     * 공통코드 그룹을 수정하는 메서드
     *
     * @param seq          시퀀스
     * @param cmncdInfoDto cmncdInfoDto 데이터
     */
    @Transactional(transactionManager = tx_manager)
    public void updateGrpCmncd(int seq, CmncdInfoDto cmncdInfoDto) throws Exception {
        cmncdInfoRepository.findById(seq)
                .ifPresent(entity ->
                    Optional.of(cmncdInfoRepository.save(mapper.toEntity(cmncdInfoDto.upsertGrpFlag(seq))))
                            .filter(updatedEntity -> entity.getCd().equals(updatedEntity.getGrpCd()))
                            .ifPresent(updatedEntity -> cmncdInfoRepository.updateByGrpCd(entity.getCd(), updatedEntity.getGrpCd()))
                );
    }

    /**
     * 공통코드 그룹을 삭제하는 메서드
     *
     * @param grpCd 그룹 코드
     */
    @Transactional(transactionManager = tx_manager)
    public void deleteGrpCmncd(String grpCd) throws Exception {
        cmncdInfoRepository.deleteByGrpCd(grpCd);
    }

    /**
     * 공통코드를 저장 또는 수정하는 메서드
     *
     * @param seq          시퀀스
     * @param cmncdInfoDto cmncdInfoDto 데이터
     */
    @Transactional(transactionManager = tx_manager)
    public void upsertCmncd(int seq, CmncdInfoDto cmncdInfoDto) throws Exception {
        cmncdInfoRepository.save(mapper.toEntity(cmncdInfoDto.upsertFlag(seq)));
    }

    /**
     * 공통코드를 삭제하는 메서드
     *
     * @param seq 시퀀스
     */
    @Transactional(transactionManager = tx_manager)
    public void deleteCmncd(int seq) throws Exception {
        cmncdInfoRepository.deleteById(seq);
    }

}
