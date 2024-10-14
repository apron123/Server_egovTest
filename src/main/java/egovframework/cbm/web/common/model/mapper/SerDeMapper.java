package egovframework.cbm.web.common.model.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Json & Xml & Map 등 매핑을 처리하는 객체
 *
 * @author  이상민
 * @since   2024.07.03 16:30
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class SerDeMapper {

    private final ObjectMapper objectMapper;

    /**
     * 객체를 JSON 문자열로 변환하는 메서드
     * @param object 변환할 객체
     * @return String 변환된 JSON 문자열
     */
    public String toJson(Object object) throws JsonProcessingException{ return objectMapper.writeValueAsString(object); }

    /**
     * JSON 문자열을 객체로 변환하는 메서드
     * @param json 변환할 JSON 문자열
     * @param clazz 변환할 객체의 클래스 타입
     * @param <T> 객체의 타입
     * @return T 변환된 객체
     */
    public <T> T fromJson(String json, Class<T> clazz) throws JsonProcessingException{ return objectMapper.readValue(json, clazz); }

    /**
     * DTO를 Map<String, Object>으로 변환하는 메서드
     * @param dto 변환할 DTO 객체
     * @return Map<String, Object> 변환된 Map 데이터
     */
    public <T> Map<String, Object> dtoToMap(T dto) throws IllegalArgumentException { return objectMapper.convertValue(dto, new TypeReference<Map<String, Object>>() {}); }

}
