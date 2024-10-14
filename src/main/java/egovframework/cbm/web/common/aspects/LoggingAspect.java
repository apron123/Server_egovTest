package egovframework.cbm.web.common.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @Description
 *  Springboot & Jpa 버전의 AOP 구현
 *  Controller, Service, Repository, 클레스 로깅만 처리
 *  트렌잭션 처리는 Service 에서 @Transactional 사용
 *
 * @link
 *  https://congsong.tistory.com/25
 *  https://velog.io/@k2hyun4/Spring-AOP%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-logging
 *
 */
@Slf4j
@Aspect
@Component
public class LoggingAspect {

    // PointCut : 적용할 지점 또는 범위 선택
    @Pointcut("execution(* egovframework.cbm.web..*Controller..*(..))")
    private void cbmWebControllerTarget() { }

    //@Pointcut("execution(* egovframework.cbm.web.*.*(..))")
    //private void cbmWebTarget() { }

    @Before("cbmWebControllerTarget()")
    public void beforeRequest(JoinPoint joinPoint){
        log.info("### Start request {}", joinPoint.getSignature().toShortString());
        if(joinPoint.getArgs() == null) return;
        for(Object arg : joinPoint.getArgs()){
            log.info("[arg] : {}", arg.toString());
        }
    }

    @AfterReturning(pointcut = "cbmWebControllerTarget()", returning = "returnValue")
    public void afterReturningLogging(JoinPoint joinPoint, Object returnValue) {
        log.info("### End return {}", joinPoint.getSignature().toShortString());
        if (returnValue == null) return;
        log.info("\t{}", returnValue.toString());
    }

}
