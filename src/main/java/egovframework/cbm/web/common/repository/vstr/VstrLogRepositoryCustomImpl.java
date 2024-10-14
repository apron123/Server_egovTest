package egovframework.cbm.web.common.repository.vstr;

import com.querydsl.jpa.impl.JPAQueryFactory;
import egovframework.cbm.web.common.model.entity.QVstrLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
public class VstrLogRepositoryCustomImpl implements VstrLogRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private static final QVstrLog qVstrLog = QVstrLog.vstrLog;

    public long countTodayVstr() {
        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = LocalDateTime.now().toLocalDate().atTime(23, 59, 59);

        Long count = queryFactory.select(qVstrLog.count())
                .from(qVstrLog)
                .where(qVstrLog.vstdt.between(startOfDay, endOfDay))
                .fetchOne();

        return count != null ? count : 0;
    }

}
