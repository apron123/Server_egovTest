package egovframework.utils;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;
import egovframework.utils.enums.SchemaPackage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * @author 지음지식서비스 개발 대장 이상민
 * @since 2024.06.25
 * @version 1.0
 * @see
 * 
 */
@Slf4j
@UtilityClass
public class Utils {

    private static final Random random = new Random();

    /**
     * 랜덤 숫자 6자리 생성
     *
     * @return String number 6자리
     */
    public static String createStrNumber() { return String.valueOf(random.nextInt(900000) + 100000); }

    /**
     * pageable Sort로 페이징 Sort
     *
     * @return
     * @exception Exception null
     */
    public static OrderSpecifier<?>[] getOrderSpecifier(Sort sort, Path<?> entityPath) {
        return sort.stream().map(order -> {
            Path<Object> path = Expressions.path(Object.class, entityPath, order.getProperty());
            return new OrderSpecifier(order.isAscending() ? Order.ASC : Order.DESC, path);
        }).toArray(OrderSpecifier[]::new);
    }

    private static String parseClassName(String packageName, String className) {
        return "egovframework.cbm.web." + packageName + ".model.dto."
                + className.substring(0, 1).toUpperCase() + className.substring(1);
    }

    public static String getSchemaDescription(String className, String fieldName) {
        for (String packageName : SchemaPackage.getSchemaPackages()) {
            try {
                // 클래스 이름을 통해 Class 객체를 얻음
                Class<?> clazz = Class.forName(parseClassName(packageName, className));
                // 필드 이름을 통해 Field 객체를 얻음
                Field field = clazz.getDeclaredField(fieldName);
                // Field 객체에서 Schema 어노테이션을 얻음
                Schema schema = field.getAnnotation(Schema.class);
                if (schema != null) {
                    // Schema 어노테이션의 description 값을 반환
                    return schema.description();
                }
            } catch (ClassNotFoundException e) {
                continue;
            } catch (NoSuchFieldException e) {
                return fieldName;
            }
        }
        return fieldName;
    }

    public static String camelToSnake(String str) {
        StringBuilder result = new StringBuilder();
        for (char character : str.toCharArray()) {
            if (Character.isUpperCase(character)) {
                result.append("_");
                result.append(Character.toLowerCase(character));
            } else {
                result.append(character);
            }
        }
        return result.toString();
    }

    public static Pageable getPageable(int page, String sortKey, Sort.Direction sortDirection) {
        return PageRequest.of(
                page - 1,
                Constants.DEFAULT_ROWS,
                Sort.by(sortDirection, sortKey));
    }

    /**
     * Id 어노테이션으로 id값 가져오는 메서드
     * 
     * @param entityClass Class<?>
     * @return string
     */
    public static String getIdName(Class<?> entityClass) {
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                return field.getName();
            }
        }
        return null; // @Id 애노테이션이 없는 경우
    }

    /**
     * HttpServletRequest로 접속 client의 IP 주소 찾기.
     * 
     * @return String Ip Address
     * @exception Exception null
     */
    public static String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        log.debug("> X-FORWARDED-FOR : " + ip);

        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
            log.debug("> Proxy-Client-IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            log.debug(">  WL-Proxy-Client-IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            log.debug("> HTTP_CLIENT_IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            log.debug("> HTTP_X_FORWARDED_FOR : " + ip);
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
            log.debug("> getRemoteAddr : " + ip);
        }
        log.debug("> Result : IP Address : " + ip);

        return ip;
    }

    /**
     * 금일 날자 yyyy-MM-dd HH:mm:ss 형식의 Timestamp 타입으로 리턴.
     * 
     * @return LocalDateTime
     * @exception Exception null
     */
    public static LocalDateTime sysDate() {
        LocalDateTime now = LocalDateTime.now();

        // LocalDateTime을 Timestamp로 변환
        // Timestamp timestamp = Timestamp.valueOf(now);

        // 날짜 및 시간 포맷터 설정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        now.format(formatter);

        // timestamp.toLocalDateTime().format(formatter);
        return now;
    }

    /**
     * 회원 ID를 *로 가려주는 메소드
     * 4글자 이하 ID는 *로 가리지 않음
     * 
     * @return String masked user Id
     * @exception Exception null
     */
    public static String maskUserId(String userId) {

        int length = userId.length();
        int maskLength = length - 4;
        if (maskLength <= 0) {
            return userId; // 너무 짧은 아이디는 마스킹하지 않음
        }

        StringBuilder maskedUserId = new StringBuilder();
        maskedUserId.append(userId.substring(0, 2)); // 앞의 2글자 유지

        for (int i = 0; i < maskLength; i++) {
            maskedUserId.append('*'); // 중간 부분 마스킹
        }

        maskedUserId.append(userId.substring(length - 2)); // 뒤의 2글자 유지

        return maskedUserId.toString();
    }
}