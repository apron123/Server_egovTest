package egovframework.cbm.web.common.service;

import egovframework.cbm.web.common.model.entity.VstrLog;
import egovframework.cbm.web.common.repository.vstr.VstrLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static egovframework.cbm.config.database.DatabaseConstants.BaseDatabase.tx_manager;

@Service
@RequiredArgsConstructor
public class VisitorLogService {

    private final VstrLogRepository vstrLogRepository;

    /**
     * 총 방문자 수 가져오는 메서드
     *
     * @return long 총 방문자수
     */
    @Transactional(transactionManager = tx_manager, readOnly = true)
    public long getVstrCount() {
        return vstrLogRepository.count();
    }

    /**
     * 당일 방문자 수 가져오는 메서드
     *
     * @return long 당일 방문자수
     */
    @Transactional(transactionManager = tx_manager, readOnly = true)
    public long getTodayVstrCount() {
        return vstrLogRepository.countTodayVstr();
    }

    /**
     * 사용자 아이디의 방문 횟수 가져오는 메서드
     *
     * @param  userId 사용자 계정
     * @return long 당일 방문자수
     */
    @Transactional(transactionManager = tx_manager, readOnly = true)
    public long getVstrCountByUserId(String userId) {
        return vstrLogRepository.countByUserId(userId);
    }

    /**
     * 방문자 로그 저장하는 메서드
     *
     * @param userId              유저아이디
     * @param httpServletRequest  http 요청 정보
     */
    @Transactional(transactionManager = tx_manager)
    public void createVstrLog(String userId, HttpServletRequest httpServletRequest, HttpSession session) {
        vstrLogRepository.save(VstrLog.builder()
                .ip(httpServletRequest.getRemoteAddr())
                .brws(httpServletRequest.getHeader(HttpHeaders.USER_AGENT))
                .userId(userId)
                .sessionId(session.getId())
                .build());
    }

}
