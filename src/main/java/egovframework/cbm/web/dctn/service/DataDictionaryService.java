package egovframework.cbm.web.dctn.service;

import static egovframework.cbm.config.database.DatabaseConstants.BaseDatabase.tx_manager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.cbm.web.common.model.dto.IdsDto;
import egovframework.cbm.web.dctn.model.dto.DctnDomainInfoDto;
import egovframework.cbm.web.dctn.model.dto.DctnInfoDto;
import egovframework.cbm.web.dctn.model.entity.ApprvPtcls;
import egovframework.cbm.web.dctn.model.entity.ApprvPtclsDomain;
import egovframework.cbm.web.dctn.model.entity.DctnInfo;
import egovframework.cbm.web.dctn.model.mapper.DataDictionaryMapper;
import egovframework.cbm.web.dctn.repository.dctninfo.ApprvPtclsRepository;
import egovframework.cbm.web.dctn.repository.dctninfo.DctnInfoRepository;
import egovframework.cbm.web.dctn.repository.domaininfo.ApprvPtclsDomainRepository;
import egovframework.cbm.web.dctn.repository.domaininfo.DctnDomainInfoRepository;
import lombok.RequiredArgsConstructor;

/**
 * 데이터 사전 서비스 로직
 *
 * @author 이상민
 * @since 2024.07.09 12:00
 */
@RequiredArgsConstructor
@Service("dataDictionaryService")
public class DataDictionaryService {

    private final DctnInfoRepository dctnInfoRepository;

    private final ApprvPtclsRepository apprvPtclsRepository;

    private final ApprvPtclsDomainRepository apprvPtclsDomainRepository;

    private final DctnDomainInfoRepository dctnDomainInfoRepository;

    private final DataDictionaryMapper mapper;

    /**
     * 검색 필터 데이터 기준으로 페이지 객체 생성하는 메서드
     *
     * @param keyword        검색 키워드
     * @param classification 분류 키워드
     * @param pageable       페이지 정보 데이터
     * @return Page<DctnInfoDto> 페이지 데이터
     */
    @Transactional(transactionManager = tx_manager, readOnly = true)
    public Page<DctnInfoDto> getPage(String keyword, String classification, char eqYn, Pageable pageable)
            throws Exception {
        return dctnInfoRepository.findByFilters(keyword, classification, eqYn, pageable)
                .map(mapper::toDto);
    }

    /**
     * 검색 필터 데이터 기준으로 페이지 객체 생성하는 메서드
     *
     * @param keyword 검색 키워드
     * @return Page<DctnDomainInfoDto> 페이지 데이터
     */
    @Transactional(transactionManager = tx_manager, readOnly = true)
    public Page<DctnDomainInfoDto> getPage(String keyword, String classification, Pageable pageable) throws Exception {
        return dctnDomainInfoRepository.findByFilters(keyword, classification, pageable)
                .map(mapper::toDto);
    }

    /**
     * 데이터를 id값 기준으로 조회하는 메서드
     *
     * @param id 데이터 ID
     * @return DctnInfoDto DctnInfo 데이터
     */
    @Transactional(transactionManager = tx_manager, readOnly = true)
    public DctnInfoDto getDctnInfo(int id) throws Exception {
        return dctnInfoRepository.findById(id)
                .map(mapper::toDto)
                .orElse(null);
    }

    /**
     * 데이터를 id값 기준으로 조회하는 메서드
     *
     * @param id 데이터 ID
     * @return DctnInfoDto DctnInfo 데이터
     */
    @Transactional(transactionManager = tx_manager, readOnly = true)
    public DctnDomainInfoDto getDctnDomainInfo(int id) throws Exception {
        return dctnDomainInfoRepository.findById(id)
                .map(mapper::toDto)
                .orElse(null);
    }

    /**
     * 데이터를 저장 또는 수정하는 메서드
     *
     * @param id          데이터 ID
     * @param dctnInfoDto DctnInfo 데이터
     */
    @Transactional(transactionManager = tx_manager)
    public void upsertDctnInfo(int id, DctnInfoDto dctnInfoDto) throws Exception {
        DctnInfo savedEntity = dctnInfoRepository.save(mapper.toEntity(dctnInfoDto.upsertFlag(id)));
        apprvPtclsRepository.save(
                apprvPtclsRepository.findByDctnInfoSeq(savedEntity.getDctnInfoSeq())
                        .orElseGet(() -> ApprvPtcls.builder()
                                .dctnInfoSeq(savedEntity.getDctnInfoSeq())
                                .build()));
    }

    /**
     * 데이터를 저장 또는 수정하는 메서드
     *
     * @param id                데이터 ID
     * @param dctnDomainInfoDto DctnDomainInfo 데이터
     */
    @Transactional(transactionManager = tx_manager)
    public void upsertDctnDomainInfo(int id, DctnDomainInfoDto dctnDomainInfoDto) throws Exception {
        dctnDomainInfoRepository.save(mapper.toEntity(dctnDomainInfoDto.upsertFlag(id)));
    }

    /**
     * 데이터 사전 용어를 사용 승인하는 메서드
     *
     * @param apprv 승인자
     * @param id    DctnInfo ID
     */
    @Transactional(transactionManager = tx_manager)
    public void upsertApprvPtcls(String apprv, int id) throws Exception {
        apprvPtclsDomainRepository.save(
                apprvPtclsDomainRepository.findByDctnDomainInfoSeq(id).orElseGet(() -> ApprvPtclsDomain.builder()
                        .dctnDomainInfoSeq(id)
                        .build())
                        .updateApprv(apprv));

    }

    /**
     * 데이터 사전 용어를 다건 사용 승인하는 메서드
     *
     * @param idsDto 다건 처리 데이터
     */
    @Transactional(transactionManager = tx_manager)
    public void upsertApprvPtclsDomainList(IdsDto idsDto) throws Exception {
        Set<Integer> dctnDomainInfoSeqs = new HashSet<>();
        Set<ApprvPtclsDomain> apprvPtclsDomainList = apprvPtclsDomainRepository
                .findAllByDctnDomainInfoSeqIn(idsDto.getIds()).stream()
                .map(entity -> {
                    dctnDomainInfoSeqs.add(entity.getDctnDomainInfoSeq());
                    return entity.updateApprv(idsDto.getUserId());
                })
                .collect(Collectors.toSet());
        if (idsDto.getIds().size() > dctnDomainInfoSeqs.size()) {
            idsDto.getIds().stream()
                    .filter(id -> !dctnDomainInfoSeqs.contains(id))
                    .map(id -> ApprvPtclsDomain.builder()
                            .dctnDomainInfoSeq(id)
                            .apprv(idsDto.getUserId())
                            .build())
                    .forEach(apprvPtclsDomainList::add);
        }
        apprvPtclsDomainRepository.saveAll(apprvPtclsDomainList);
    }

    /**
     * 도메인 사전 용어를 사용 승인하는 메서드
     *
     * @param apprv 승인자
     * @param id    DctnDomainInfo ID
     */
    @Transactional(transactionManager = tx_manager)
    public void upsertApprvPtclsDomain(String apprv, int id) throws Exception {
        apprvPtclsRepository.save(
                apprvPtclsRepository.findByDctnInfoSeq(id).orElseGet(() -> ApprvPtcls.builder()
                        .dctnInfoSeq(id)
                        .build())
                        .updateApprv(apprv));

    }

    /**
     * 데이터 사전 용어를 다건 사용 승인하는 메서드
     *
     * @param idsDto 다건 처리 데이터
     */
    @Transactional(transactionManager = tx_manager)
    public void upsertApprvPtclsList(IdsDto idsDto) throws Exception {
        Set<Integer> dctnInfoSeqs = new HashSet<>();
        Set<ApprvPtcls> apprvPtclsList = apprvPtclsRepository.findAllByDctnInfoSeqIn(idsDto.getIds()).stream()
                .map(entity -> {
                    dctnInfoSeqs.add(entity.getDctnInfoSeq());
                    return entity.updateApprv(idsDto.getUserId());
                })
                .collect(Collectors.toSet());
        if (idsDto.getIds().size() > dctnInfoSeqs.size()) {
            idsDto.getIds().stream()
                    .filter(id -> !dctnInfoSeqs.contains(id))
                    .map(id -> ApprvPtcls.builder()
                            .dctnInfoSeq(id)
                            .apprv(idsDto.getUserId())
                            .build())
                    .forEach(apprvPtclsList::add);
        }
        apprvPtclsRepository.saveAll(apprvPtclsList);
    }

    /**
     * 데이터를 삭제하는 메서드
     *
     * @param id 데이터 ID
     */
    @Transactional(transactionManager = tx_manager)
    public void deleteDctnInfo(int id) throws Exception {
        dctnInfoRepository.deleteById(id);
    }

    @Transactional(transactionManager = tx_manager)
    public void deleteDctnDomainInfo(int id) throws Exception {
        dctnDomainInfoRepository.deleteById(id);
    }

    /**
     * 데이터를 다건 삭제하는 메서드
     *
     * @param idList 데이터 ID List
     */
    @Transactional(transactionManager = tx_manager)
    public void deleteDctnInfoList(Set<Integer> idList) throws Exception {
        dctnInfoRepository.deleteAllByIdInBatch(idList);
    }

    @Transactional(transactionManager = tx_manager)
    public void deleteDctnDomainInfoList(Set<Integer> idList) throws Exception {
        dctnDomainInfoRepository.deleteAllByIdInBatch(idList);
    }

    /**
     * 미승인 도메인 사전 리스트
     */
    @Transactional(transactionManager = tx_manager, readOnly = true)
    public List<DctnDomainInfoDto> appryDomainList() throws Exception {

        return mapper.toDtoList(dctnDomainInfoRepository
                .findByApprvPtclsDomain_ApprvIsNullAndApprvPtclsDomain_ApprvDttmIsNull());
    }

    @Transactional(transactionManager = tx_manager, readOnly = true)
    public List<DctnInfoDto> appryDctnList() throws Exception {

        return mapper.toDto(dctnInfoRepository
                .findByApprvPtcls_ApprvIsNullAndApprvPtcls_ApprvDttmIsNull());
    }
}
