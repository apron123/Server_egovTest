package egovframework.cbm.web.common.exception;

import egovframework.cbm.web.common.model.ResponseVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.utils.Utils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import javax.persistence.EntityNotFoundException;
import java.security.SignatureException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.MessageFormat;
import java.util.*;

/**
 * @author hdpark
 * @version 1.0 공통 예외처리 초기 버전
 * @see
 */
@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @Resource(name = "egovMessageSource")
    EgovMessageSource egovMessageSource;

    /**
     * 공통 예외 처리
     *
     * @param ex Exception
     * @return ResponseEntity<ResponseVO>
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseVO<String>> handleDataAccessException(Exception ex) {
        log.error("handleDataAccessException: ", ex);
        ResponseVO<String> response = new ResponseVO<>(5000, egovMessageSource.getMessage("fail.common.sql"), "");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * SQL 에러 및 jpa 데이터 바인딩 에러 예외 처리
     *
     * @param ex Exception
     * @return ResponseEntity<ResponseVO>
     */
    @ExceptionHandler({
            NoResourceFoundException.class
    })
    public ResponseEntity<ResponseVO<String>> handleCommonNoResourceFoundException(Exception ex) {
        log.error("handleCommonNoResourceFoundException: {}", ex.getMessage());
        ResponseVO<String> response = new ResponseVO<>(404, (ex.getMessage()), "");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * SQL 에러 및 jpa 데이터 바인딩 에러 예외 처리
     * 
     * @param ex Exception
     * @return ResponseEntity<ResponseVO>
     */
    @ExceptionHandler({
            DataIntegrityViolationException.class,
            SQLIntegrityConstraintViolationException.class,
            SQLException.class,
            EntityNotFoundException.class
    })
    public ResponseEntity<ResponseVO<String>> handleCommonSqlException(Exception ex) {
        log.error("handleCommonSqlException: ", ex);
        ResponseVO<String> response = new ResponseVO<>(5001, getSqlErrorMessage(ex.getMessage()), "");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 데이터 유효성 검사 실패 시 예외 처리
     * 
     * @param ex MethodArgumentNotValidException
     * @return ResponseEntity<ResponseVO>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseVO<Map<String, List<String>>>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        log.error("handleValidationExceptions: ", ex);
        List<String> errorMessageList = new ArrayList<>();
        String validCommonMsg = egovMessageSource.getMessage("valid.common.msg");
        ex.getBindingResult().getFieldErrors()
                .forEach(fieldError -> Optional.ofNullable(fieldError)
                        .ifPresent(error -> errorMessageList.add(
                                Utils.getSchemaDescription(error.getObjectName(), error.getField())
                                        + error.getDefaultMessage())));
        Map<String, List<String>> errorMessage = new HashMap<>();
        errorMessage.put("errorMessage", errorMessageList);
        ResponseVO<Map<String, List<String>>> response = new ResponseVO<>(5002, validCommonMsg, errorMessage);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getSqlErrorMessage(String message) {
        return MessageFormat.format(egovMessageSource.getMessage("fail.common.sql"), message);
    }

    /**
     * SQL 에러 및 jpa 데이터 바인딩 에러 예외 처리
     * 
     * @param ex Exception
     * @return ResponseEntity<ResponseVO>
     */
    @ExceptionHandler({
            ExpiredJwtException.class, // 만료시간
            MalformedJwtException.class, // 토큰에 값이 없음
            UnsupportedJwtException.class, // JWT 형식에 맞지 않음
            SignatureException.class // 복호화 문제
    })
    public ResponseEntity<ResponseVO<String>> handleRfTokenException(Exception ex) {
        log.error("handleCommonSqlException: ", ex);
        ResponseVO<String> response = new ResponseVO<>(401,
                MessageFormat.format(egovMessageSource.getMessage("fail.jwt.error"), ex.getMessage()), "");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

}
