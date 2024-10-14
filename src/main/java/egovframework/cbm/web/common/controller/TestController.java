package egovframework.cbm.web.common.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import egovframework.cbm.web.common.model.ResponseVO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 지음지식서비스 개발 대장 이상민
 * @version 1.0
 * @see
 * @since 2024.06.21
 */

@Slf4j
@RestController
@RequestMapping("test")
public class TestController {

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "500", description = "조회 성공")
    })
    @GetMapping("/test")
    public ResponseVO List(Pageable pageable) {
        List<Map<String, Object>> testList = new ArrayList();
        Map testMap = new HashMap<>();

        testMap.put("DCTN_INFO_SEQ", 2);
        testMap.put("ABBRNM", "0");
        testMap.put("WORD_NM", "0");
        testMap.put("WORD_EXPLN", "숫자 0");
        testMap.put("ENGNM", "ZERO");
        testMap.put("CLSFCT", "ND");
        testMap.put("WRTR", "김주현");
        testMap.put("CRT_DTTM", "2024-07-08 10:55:55");
        testMap.put("DCTN_INFO_SEQ", "2024-07-08 10:55:55");

        testList.add(testMap);

        return new ResponseVO(testList);
    }

    @GetMapping(value = "/page")
    public ModelAndView page() {
        // view 페이지 경로
        ModelAndView mv = new ModelAndView("/common/pagination");
        Map testMap = new HashMap<>();

        testMap.put("page", 1);
        testMap.put("startPage", 1);
        testMap.put("endPage", 10);
        testMap.put("last", 10);

        // Map dataMap = new HashMap();
        // dataMap.put("data",testMap);
        log.info("호츨됨.. page");
        mv.addObject("data", testMap);
        return mv;

    }
}
