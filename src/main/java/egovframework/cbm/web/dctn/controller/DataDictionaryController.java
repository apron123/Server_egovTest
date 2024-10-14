package egovframework.cbm.web.dctn.controller;

import egovframework.cbm.web.common.model.ResponseVO;
import egovframework.cbm.web.common.model.dto.FrontVaildateDto;
import egovframework.cbm.web.common.model.dto.IdsDto;
import egovframework.cbm.web.common.service.CommonService;
import egovframework.cbm.web.dctn.model.dto.DctnDomainInfoDto;
import egovframework.cbm.web.dctn.model.dto.DctnInfoDto;
import egovframework.cbm.web.dctn.service.DataDictionaryService;
import egovframework.utils.Constants;
import egovframework.utils.SwaggerResponse;
import egovframework.utils.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("dctn")
@Tag(name = "DataDictionaryController", description = "데이터 사전 RestController")
public class DataDictionaryController {

        private final DataDictionaryService dataDictionaryService;
        private final CommonService commonService;

        /**
         * Main Page
         */
        @GetMapping("/list")
        @Operation(summary = "데이터 사전 목록 조회", description = "데이터 사전 목록을 조회한다", tags = {
                        "DataDictionaryController" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponsePageDctnInfo.class))),
                                        @ApiResponse(responseCode = "500", description = "조회 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
                        })
        public ResponseVO<Page<DctnInfoDto>> searchPage(
                        @RequestParam(defaultValue = "") @Schema(description = "검색 키워드", example = "사전") String keyword,
                        @RequestParam(defaultValue = "") @Schema(description = "분류 코드", pattern = Constants.REGEXP_CLSFCT, example = Constants.DEFAULT_CLSFCT) String classification,
                        @RequestParam(name = "eq_yn", defaultValue = "N") @Schema(description = "단어명 일치 조회 여부", example = "N") char eqYn,
                        @RequestParam(defaultValue = "1") @Schema(description = "페이지 번호", example = "1") int page)
                        throws Exception {
                return new ResponseVO<>(dataDictionaryService.getPage(keyword, classification, eqYn,
                                Utils.getPageable(page, "dctnInfoSeq", Sort.Direction.ASC)));
        }

        @GetMapping("/{id}")
        @Operation(summary = "데이터 사전 용어 조회", description = "데이터 사전 용어를 단건 조회한다", tags = {
                        "DataDictionaryController" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseDctnInfo.class))),
                                        @ApiResponse(responseCode = "500", description = "조회 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
                        })
        public ResponseVO<DctnInfoDto> searchInfo(
                        @Parameter(description = "데이터 사전 ID", in = ParameterIn.PATH, example = "1") @PathVariable int id)
                        throws Exception {
                return new ResponseVO<>(dataDictionaryService.getDctnInfo(id));
        }

        @PostMapping("/create")
        @Operation(summary = "데이터 사전 용어 등록", description = "데이터 사전 용어를 등록한다", tags = {
                        "DataDictionaryController" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "등록 성공"),
                                        @ApiResponse(responseCode = "500", description = "등록 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseVaildError.class)))
                        })
        public ResponseVO<String> create(
                        @Valid @RequestBody @Schema(implementation = SwaggerResponse.CreateDctnInfo.class) DctnInfoDto dctnInfoDto)
                        throws Exception {
                dataDictionaryService.upsertDctnInfo(0, dctnInfoDto);
                return new ResponseVO<>("");
        }

        @PutMapping("/{id}")
        @Operation(summary = "데이터 사전 용어 수정", description = "데이터 사전 용어를 수정한다.", tags = {
                        "DataDictionaryController" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "수정 성공"),
                                        @ApiResponse(responseCode = "500", description = "수정 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseVaildError.class)))
                        })
        public ResponseVO<String> update(
                        @Parameter(description = "데이터 사전 ID", in = ParameterIn.PATH, example = "1") @PathVariable int id,
                        @Valid @RequestBody DctnInfoDto dctnInfoDto) throws Exception {
                dataDictionaryService.upsertDctnInfo(id, dctnInfoDto);
                return new ResponseVO<>("");
        }

        @DeleteMapping("/{id}")
        @Operation(summary = "데이터 사전 용어 삭제", description = "데이터 사전 용어를 단건 삭제한다", tags = {
                        "DataDictionaryController" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "삭제 성공"),
                                        @ApiResponse(responseCode = "500", description = "삭제 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
                        })
        public ResponseVO<String> delete(
                        @Parameter(description = "데이터 사전 ID", in = ParameterIn.PATH, example = "1") @PathVariable int id)
                        throws Exception {
                dataDictionaryService.deleteDctnInfo(id);
                return new ResponseVO<>("");
        }

        @DeleteMapping("/delete")
        @Operation(summary = "데이터 사전 용어 리스트 삭제", description = "데이터 사전 용어를 다건 삭제한다", tags = {
                        "DataDictionaryController" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "삭제 성공"),
                                        @ApiResponse(responseCode = "500", description = "삭제 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
                        })
        public ResponseVO<String> deleteList(@Valid @RequestBody IdsDto ids) throws Exception {
                dataDictionaryService.deleteDctnInfoList(ids.getIds());
                return new ResponseVO<>("");
        }

        @PutMapping("/approve/{id}")
        @Operation(summary = "데이터 사전 용어 승인", description = "데이터 사전 용어를 승인한다", tags = {
                        "DataDictionaryController" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "승인 성공"),
                                        @ApiResponse(responseCode = "500", description = "승인 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
                        })
        public ResponseVO<String> approve(
                        @Parameter(description = "데이터 사전 id", in = ParameterIn.PATH, example = "1") @PathVariable int id,
                        @Valid @RequestBody @Schema(implementation = SwaggerResponse.IdsOnlyUserId.class) IdsDto idsDto)
                        throws Exception {
                dataDictionaryService.upsertApprvPtcls(idsDto.getUserId(), id);
                return new ResponseVO<>("");
        }

        @PutMapping("/approve")
        @Operation(summary = "데이터 사전 용어 승인", description = "데이터 사전 용어를 다건 승인한다", tags = {
                        "DataDictionaryController" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "승인 성공"),
                                        @ApiResponse(responseCode = "500", description = "승인 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
                        })
        public ResponseVO<String> approveList(
                        @Valid @RequestBody IdsDto idsDto) throws Exception {
                dataDictionaryService.upsertApprvPtclsList(idsDto);
                return new ResponseVO<>("");
        }

        @GetMapping("/valid")
        @Operation(summary = "데이터 사전 검증 리스트 생성", description = "데이터 사전 검증 데이터를 생성한다", tags = {
                        "DataDictionaryController" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "생성 성공", content = @Content(array = @ArraySchema(schema = @Schema(implementation = FrontVaildateDto.class)))),
                                        @ApiResponse(responseCode = "500", description = "생성 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
                        })
        public ResponseVO<Map<String, FrontVaildateDto>> createValidateList() throws Exception {
                return new ResponseVO<>(commonService.createFrontValidateList(DctnInfoDto.class));
        }

        /**
         * Domain Page
         */
        @GetMapping("/domain/list")
        @Operation(summary = "데이터 사전 목록 조회", description = "데이터 사전 도메인 목록을 조회한다", tags = {
                        "DataDictionaryController" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponsePageDctnDomainInfo.class))),
                                        @ApiResponse(responseCode = "500", description = "조회 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
                        })
        public ResponseVO<Page<DctnDomainInfoDto>> searchDomainPage(
                        @RequestParam(defaultValue = "") @Schema(description = "검색 키워드", example = "사전") String keyword,
                        @RequestParam(defaultValue = "") @Schema(description = "분류 코드", pattern = Constants.REGEXP_CLSFCT, example = Constants.DEFAULT_CLSFCT) String classification,
                        @RequestParam(defaultValue = "1") @Schema(description = "페이지 번호", example = "1") int page)
                        throws Exception {
                return new ResponseVO<>(dataDictionaryService.getPage(keyword, classification,
                                Utils.getPageable(page, "dctnDomainInfoSeq", Sort.Direction.ASC)));
        }

        @GetMapping("/domain/{id}")
        @Operation(summary = "데이터 사전 도메인 조회", description = "데이터 사전 도메인을 단건 조회한다", tags = {
                        "DataDictionaryController" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseDctnDomainInfoDto.class))),
                                        @ApiResponse(responseCode = "500", description = "조회 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
                        })
        public ResponseVO<DctnDomainInfoDto> searchDomainInfo(
                        @Parameter(description = "데이터 사전 도메인 ID", in = ParameterIn.PATH, example = "1") @PathVariable int id)
                        throws Exception {
                return new ResponseVO<>(dataDictionaryService.getDctnDomainInfo(id));
        }

        @PostMapping("/domain/create")
        @Operation(summary = "데이터 사전 도메인 등록", description = "데이터 사전 도메인을 등록한다", tags = {
                        "DataDictionaryController" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "등록 성공"),
                                        @ApiResponse(responseCode = "500", description = "등록 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseVaildError.class)))
                        })
        public ResponseVO<String> createDomain(
                        @Valid @RequestBody @Schema(implementation = SwaggerResponse.CreateDctnDomainInfoDto.class) DctnDomainInfoDto dctnDomainInfoDto)
                        throws Exception {
                dataDictionaryService.upsertDctnDomainInfo(0, dctnDomainInfoDto);
                return new ResponseVO<>("");
        }

        @PutMapping("/domain/{id}")
        @Operation(summary = "데이터 사전 도메인 수정", description = "데이터 사전 도메인을 수정한다.", tags = {
                        "DataDictionaryController" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "수정 성공"),
                                        @ApiResponse(responseCode = "500", description = "수정 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseVaildError.class)))
                        })
        public ResponseVO<String> update(
                        @Parameter(description = "데이터 사전 도메인 ID", in = ParameterIn.PATH, example = "1") @PathVariable int id,
                        @Valid @RequestBody DctnDomainInfoDto dctnDomainInfoDto) throws Exception {
                dataDictionaryService.upsertDctnDomainInfo(id, dctnDomainInfoDto);
                return new ResponseVO<>("");
        }

        @DeleteMapping("/domain/{id}")
        @Operation(summary = "데이터 사전 도메인 삭제", description = "데이터 사전 도메인을 단건 삭제한다", tags = {
                        "DataDictionaryController" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "삭제 성공"),
                                        @ApiResponse(responseCode = "500", description = "삭제 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
                        })
        public ResponseVO<String> deleteDomain(
                        @Parameter(description = "데이터 사전 도메인 ID", in = ParameterIn.PATH, example = "1") @PathVariable int id)
                        throws Exception {
                dataDictionaryService.deleteDctnDomainInfo(id);
                return new ResponseVO<>("");
        }

        @DeleteMapping("/domain/delete")
        @Operation(summary = "데이터 사전 도메인 리스트 삭제", description = "데이터 사전 도메인을 다건 삭제한다", tags = {
                        "DataDictionaryController" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "삭제 성공"),
                                        @ApiResponse(responseCode = "500", description = "삭제 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
                        })
        public ResponseVO<String> deleteDomainList(@Valid @RequestBody IdsDto ids) throws Exception {
                dataDictionaryService.deleteDctnDomainInfoList(ids.getIds());
                return new ResponseVO<>("");
        }

        @PutMapping("/domain/approve/{id}")
        @Operation(summary = "도메인 사전 용어 승인", description = "도메인 사전 용어를 승인한다", tags = {
                        "DataDictionaryController" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "승인 성공"),
                                        @ApiResponse(responseCode = "500", description = "승인 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
                        })
        public ResponseVO<String> approveDomain(
                        @Parameter(description = "도메인 사전 id", in = ParameterIn.PATH, example = "1") @PathVariable int id,
                        @Valid @RequestBody @Schema(implementation = SwaggerResponse.IdsOnlyUserId.class) IdsDto idsDto)
                        throws Exception {
                dataDictionaryService.upsertApprvPtclsDomain(idsDto.getUserId(), id);
                return new ResponseVO<>("");
        }

        @PutMapping("/domain/approve")
        @Operation(summary = "도메인 사전 용어 승인", description = "도메인 사전 용어를 다건 승인한다", tags = {
                        "DataDictionaryController" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "승인 성공"),
                                        @ApiResponse(responseCode = "500", description = "승인 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
                        })
        public ResponseVO<String> approveDomainList(
                        @Valid @RequestBody IdsDto idsDto) throws Exception {
                dataDictionaryService.upsertApprvPtclsDomainList(idsDto);
                return new ResponseVO<>("");
        }

        @GetMapping("/domain/valid")
        @Operation(summary = "데이터 사전 도메인 검증 리스트 생성", description = "데이터 사전 도메인 검증 데이터를 생성한다", tags = {
                        "DataDictionaryController" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "생성 성공", content = @Content(array = @ArraySchema(schema = @Schema(implementation = FrontVaildateDto.class)))),
                                        @ApiResponse(responseCode = "500", description = "생성 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
                        })
        public ResponseVO<Map<String, FrontVaildateDto>> createDomainVaildateList() throws Exception {
                return new ResponseVO<>(commonService.createFrontValidateList(DctnDomainInfoDto.class));
        }

        /*
         * 미승인 리스트
         * 
         */

        @GetMapping("/appry/list/domain")
        public ResponseVO<List<DctnDomainInfoDto>> appryDomainList()
                        throws Exception {
                return new ResponseVO<>(dataDictionaryService.appryDomainList());
        }

        @GetMapping("/appry/list/dctn")
        public ResponseVO<List<DctnInfoDto>> appryDctnList()
                        throws Exception {
                return new ResponseVO<>(dataDictionaryService.appryDctnList());
        }

}
