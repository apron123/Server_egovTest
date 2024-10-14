package egovframework.cbm.web.user.model.entity;

import java.io.Serializable;

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
 * 토큰 정보 엔티티
 *
 * @author 이상민
 * @since 2024.07.08 12:00
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "TOKEN_INFO")
public class TokenInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TOKEN_INFO_SEQ", nullable = false, updatable = false)
    private int id;

    @Column(name = "USER_ID", nullable = false, length = 50)
    private String userId;

    @Column(name = "REFRESH_TOKEN", nullable = false, length = 500)
    private String refreshToken;

    public TokenInfo updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

}
