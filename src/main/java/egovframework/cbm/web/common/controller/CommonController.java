package egovframework.cbm.web.common.controller;

import egovframework.cbm.web.common.model.ResponseVO;
import egovframework.cbm.web.common.model.dto.CmncdInfoDto;
import egovframework.cbm.web.common.service.CommonService;
import egovframework.cbm.web.common.service.VisitorLogService;
import egovframework.utils.SwaggerResponse;
import egovframework.utils.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Tag(name = "CommonController", description = "플랫폼 공통 RestController")
public class CommonController {

    private final CommonService commonService;

    private final VisitorLogService visitorLogService;

    @GetMapping("/cmnd/grp/list")
    @Operation(summary = "공통코드 그룹 목록 조회", description = "공통코드 그룹 목록을 조회한다", tags = {
            "CommonController" }, responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponsePageCmncdInfo.class))),
            @ApiResponse(responseCode = "500", description = "조회 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
    })
    public ResponseVO<Page<CmncdInfoDto>> searchGrpPage(
            @RequestParam(defaultValue = "") @Schema(description = "검색 키워드") String keyword,
            @RequestParam(defaultValue = "cmncdInfoSeq") @Schema(description = "정렬 기준 컬럼", example = "cmncdInfoSeq") String sortCol,
            @RequestParam(defaultValue = "asc") @Schema(description = "정렬 방향", example = "asc") String sortDirection,
            @RequestParam(defaultValue = "1") @Schema(description = "페이지 번호", example = "1") int page)
            throws Exception {
        return new ResponseVO<>(commonService.getGrpPage(keyword, Utils.getPageable(page, sortCol, Sort.Direction.fromString(sortDirection.toUpperCase()))));
    }

    @GetMapping("/cmnd/list/{grpCd}")
    @Operation(summary = "공통코드 그룹의 하위코드 목록 조회", description = "공통코드 그룹의 하위코드 목록 조회", tags = {
            "CommonController" }, responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponsePageCmncdInfo.class))),
            @ApiResponse(responseCode = "500", description = "조회 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
    })
    public ResponseVO<Page<CmncdInfoDto>> searchPage(
            @Parameter(description = "공통코드 그룹코드", in = ParameterIn.PATH, example = "TEST") @PathVariable String grpCd,
            @RequestParam(defaultValue = "") @Schema(description = "검색 키워드") String keyword,
            @RequestParam(defaultValue = "cmncdInfoSeq") @Schema(description = "정렬 기준 컬럼", example = "cmncdInfoSeq") String sortCol,
            @RequestParam(defaultValue = "asc") @Schema(description = "정렬 방향", example = "asc") String sortDirection,
            @RequestParam(defaultValue = "1") @Schema(description = "페이지 번호", example = "1") int page)
            throws Exception {
        return new ResponseVO<>(commonService.getPage(grpCd, keyword, Utils.getPageable(page, sortCol, Sort.Direction.fromString(sortDirection.toUpperCase()))));
    }

    @GetMapping("/cmnd/grp/{seq}")
    @Operation(summary = "공통코드 그룹 단건 조회", description = "공통코드 그룹 단건 조회", tags = {
            "CommonController" }, responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = CmncdInfoDto.class))),
            @ApiResponse(responseCode = "500", description = "조회 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
    })
    public ResponseVO<CmncdInfoDto> searchGrpInfo(
            @Parameter(description = "공통코드 시퀀스", in = ParameterIn.PATH, example = "1") @PathVariable int seq)
            throws Exception {
        return new ResponseVO<>(commonService.getCmncdInfo(seq));
    }

    @PostMapping("/cmnd/grp/create")
    @Operation(summary = "공통코드 그룹 등록", description = "공통코드 그룹 등록", tags = {
            "CommonController" }, responses = {
            @ApiResponse(responseCode = "200", description = "등록 성공"),
            @ApiResponse(responseCode = "500", description = "등록 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
    })
    public ResponseVO<String> createGrpCmncd(@Valid @RequestBody CmncdInfoDto cmncdInfoDto) throws Exception {
        commonService.craateGrpCmncd(cmncdInfoDto);
        return new ResponseVO<>("성공이다롱~");
    }

    @PutMapping("/cmnd/grp/{seq}")
    @Operation(summary = "공통코드 그룹 수정", description = "공통코드 그룹 수정", tags = {
            "CommonController" }, responses = {
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "500", description = "수정 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
    })
    public ResponseVO<String> updateGrpCmncd(
            @Parameter(description = "공통코드 시퀀스", in = ParameterIn.PATH, example = "1") @PathVariable int seq,
            @Valid @RequestBody CmncdInfoDto cmncdInfoDto)
            throws Exception {
        commonService.updateGrpCmncd(seq, cmncdInfoDto);
        return new ResponseVO<>("성공이다롱~");
    }

    @DeleteMapping("/cmnd/grp/{grpCd}")
    @Operation(summary = "공통코드 그룹 삭제", description = "공통코드 그룹 삭제", tags = {
            "CommonController" }, responses = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "500", description = "삭제 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
    })
    public ResponseVO<String> deleteGrpCmncd(
            @Parameter(description = "공통코드 그룹 코드", in = ParameterIn.PATH, example = "TEST") @PathVariable String grpCd)
            throws Exception {
        commonService.deleteGrpCmncd(grpCd);
        return new ResponseVO<>("성공이다롱~");
    }

    @PostMapping("/cmnd/create")
    @Operation(summary = "공통코드 등록", description = "공통코드 등록", tags = {
            "CommonController" }, responses = {
            @ApiResponse(responseCode = "200", description = "등록 성공"),
            @ApiResponse(responseCode = "500", description = "등록 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
    })
    public ResponseVO<String> createCmncd(@Valid @RequestBody @Schema(implementation = SwaggerResponse.CreateCmnCdInfo.class) CmncdInfoDto cmncdInfoDto) throws Exception {
        commonService.upsertCmncd(0, cmncdInfoDto);
        return new ResponseVO<>("성공이다롱~");
    }

    @PutMapping("/cmnd/{seq}")
    @Operation(summary = "공통코드 수정", description = "공통코드 수정", tags = {
            "CommonController" }, responses = {
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "500", description = "수정 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
    })
    public ResponseVO<String> updateCmncd(
            @Parameter(description = "공통코드 시퀀스", in = ParameterIn.PATH, example = "1") @PathVariable int seq,
            @Valid @RequestBody @Schema(implementation = SwaggerResponse.CreateCmnCdInfo.class) CmncdInfoDto cmncdInfoDto)
            throws Exception {
        commonService.upsertCmncd(seq, cmncdInfoDto);
        return new ResponseVO<>("성공이다롱~");
    }

    @DeleteMapping("/cmnd/{seq}")
    @Operation(summary = "공통코드 삭제", description = "공통코드 삭제", tags = {
            "CommonController" }, responses = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "500", description = "삭제 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
    })
    public ResponseVO<String> deleteCmncd(
            @Parameter(description = "공통코드 시퀀스", in = ParameterIn.PATH, example = "1") @PathVariable int seq)
            throws Exception {
        commonService.deleteCmncd(seq);
        return new ResponseVO<>("성공이다롱~");
    }

    @Operation(summary = "총 방문자 수 조회", description = "총 방문자 수 조회", tags = { "CommonController" }, responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseLong.class))),
            @ApiResponse(responseCode = "500", description = "조회 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
    })
    @GetMapping("/vstr")
    public ResponseVO<Long> getVstrCount() {
        return new ResponseVO<>(visitorLogService.getVstrCount());
    }

    @Operation(summary = "당일 방문자 수 조회", description = "당일 방문자 수 조회", tags = { "CommonController" }, responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseLong.class))),
            @ApiResponse(responseCode = "500", description = "조회 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
    })
    @GetMapping("/vstr/today")
    public ResponseVO<Long> getTodayVstrCount() {
        return new ResponseVO<>(visitorLogService.getTodayVstrCount());
    }

    @Operation(summary = "사용자 아이디의 방문 횟수 조회", description = "사용자 아이디의 방문 횟수 조회", tags = { "CommonController" }, responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseLong.class))),
            @ApiResponse(responseCode = "500", description = "조회 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
    })
    @GetMapping("/vstr/{userId}")
    public ResponseVO<Long> getUserIdVstrCount(@Parameter(description = "사용자 아이디", in = ParameterIn.PATH, example = "TEST") @PathVariable String userId) {
        return new ResponseVO<>(visitorLogService.getVstrCountByUserId(userId));
    }

}
