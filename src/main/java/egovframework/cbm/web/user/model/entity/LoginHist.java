package egovframework.cbm.web.user.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 로그인 이력 엔티티
 *
 * @author 이상민
 * @since 2024.07.08 12:00
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "LOGIN_HIST")
public class LoginHist implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOGIN_HIST_SEQ", nullable = false, updatable = false)
    private int id;

    @Column(name = "USER_ID", nullable = false, length = 50)
    private String userId;

    @Column(name = "LOGIN_DTTM", nullable = false, length = 500)
    private LocalDateTime loginDttm;

    @Column(name = "IP_CTLG", nullable = false, length = 500)
    private String ipAddress;

}
