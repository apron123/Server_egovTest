package egovframework.cbm.web.authip.controller;

import egovframework.cbm.web.authip.model.dto.AuthrzIpCtlgDto;
import egovframework.cbm.web.authip.service.AuthorizeIpService;
import egovframework.cbm.web.common.model.ResponseVO;
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
@RequestMapping("authip")
@Tag(name = "AuthorizeIpController", description = "인가 IP RestController")
public class AuthorizeIpController {

        private final AuthorizeIpService authorizeIpService;

        @GetMapping("/list")
        @Operation(summary = "인가 IP 목록 조회", description = "인가 IP 목록을 조회한다", tags = {"AuthorizeIpController"},
                responses = {
                        @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponsePageAuthrzIpCtlg.class))),
                        @ApiResponse(responseCode = "500", description = "조회 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
                }
        )
        public ResponseVO<Page<AuthrzIpCtlgDto>> searchPage(
                @RequestParam(defaultValue = "") @Schema(description = "검색 키워드", example = "허용") String keyword,
                @RequestParam(defaultValue = "") @Schema(description = "IP 키워드", example = "192.168.0.1") String ip,
                @RequestParam(defaultValue = "1") @Schema(description = "페이지 번호", example = "1") int page)  throws Exception {
                return new ResponseVO<>(authorizeIpService.getPage(keyword, ip, Utils.getPageable(page, "authrzIpCtlgSeq", Sort.Direction.ASC)));
        }

        @GetMapping("/{id}")
        @Operation(summary = "인가 IP 조회", description = "인가 IP 를 단건 조회한다", tags = {"AuthorizeIpController"},
                responses = {
                        @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseAuthrzIpCtlg.class))),
                        @ApiResponse(responseCode = "500", description = "조회 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
                }
        )
        public ResponseVO<AuthrzIpCtlgDto> searchInfo(@Parameter(description = "데이터 사전 ID", in = ParameterIn.PATH, example = "1") @PathVariable int id) throws Exception {
                return new ResponseVO<>(authorizeIpService.getAuthrzIpCthlg(id));
        }

        @PostMapping("/create")
        @Operation(summary = "인가 IP 등록", description = "인가 IP를 등록한다", tags = {"AuthorizeIpController"},
                responses = {
                        @ApiResponse(responseCode = "200", description = "등록 성공"),
                        @ApiResponse(responseCode = "500", description = "등록 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseVaildError.class)))
                }
        )
        public ResponseVO<String> create(@Valid @RequestBody @Schema(implementation = SwaggerResponse.CreateAuthrzIpCtlg.class) AuthrzIpCtlgDto authrzIpCtlgDto) throws Exception {
                authorizeIpService.upsertDctnInfo(0, authrzIpCtlgDto);
                return new ResponseVO<>("");
        }

        @PutMapping("/{id}")
        @Operation(summary = "인가 IP 수정", description = "인가 IP를 수정한다.", tags = {"AuthorizeIpController"},
                responses = {
                        @ApiResponse(responseCode = "200", description = "수정 성공"),
                        @ApiResponse(responseCode = "500", description = "수정 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseVaildError.class)))
                }
        )
        public ResponseVO<String> update(
                @Parameter(description = "인가 IP ID", in = ParameterIn.PATH, example = "1") @PathVariable int id,
                @Valid @RequestBody AuthrzIpCtlgDto authrzIpCtlgDto) throws Exception {
                authorizeIpService.upsertDctnInfo(id, authrzIpCtlgDto);
                return new ResponseVO<>("");
        }

        @DeleteMapping("/{id}")
        @Operation(summary = "인가 IP 삭제", description = "인가 IP를 단건 삭제한다", tags = {"AuthorizeIpController"},
                responses = {
                        @ApiResponse(responseCode = "200", description = "삭제 성공"),
                        @ApiResponse(responseCode = "500", description = "삭제 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
                }
        )
        public ResponseVO<String> delete(@Parameter(description = "데이터 사전 ID", in = ParameterIn.PATH, example = "1") @PathVariable int id) throws Exception {
                authorizeIpService.deleteDctnInfo(id);
                return new ResponseVO<>("");
        }

}
