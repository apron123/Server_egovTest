package egovframework.cbm.web.common.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 표준플랫폼 방문자 로그 엔티티
 *
 * @author  이상민
 * @since   2024.10.04 12:00
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "VSTR_LOG", indexes = {
        @Index(name = "VSTR_LOG_VSTDT_IDX", columnList = "VSTDT")
})
public class VstrLog implements Serializable {

    @Serial
    private static final long serialVersionUID = -1277760769814171974L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VSTR_LOG_SEQ", nullable = false, updatable = false)
    private int vstrLogSeq;

    @Column(name = "IP", nullable = false, length = 50)
    private String ip;

    @CreationTimestamp
    @Column(name = "VSTDT", nullable = false, updatable = false)
    private LocalDateTime vstdt;

    @Column(name = "BRWS", nullable = false)
    private String brws;

    @Column(name = "USER_ID", length = 15)
    private String userId;

    @Column(name = "SESSION_ID", nullable = false, length = 50)
    private String sessionId;

}
