package egovframework.cbm.web.user.model.entity;

import egovframework.cbm.web.common.model.entity.CmncdInfo;
import egovframework.utils.enums.Yn;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 유저 정보 엔티티
 *
 * @author 이상민
 * @since 2024.07.08 12:00
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "USER_INFO")
public class UserInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = -7806065565767808947L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_INFO_SEQ", nullable = false, updatable = false)
    private int userInfoSeq;

    @Column(name = "USER_ID", unique = true, nullable = false, length = 15)
    private String userId;

    @Column(name = "USER_PW", nullable = false, length = 200)
    private String userPw;

    @Column(name = "USER_NM", nullable = false, length = 100)
    private String userNm;

    @Column(name = "USE_YN", nullable = false, length = 1)
    private char useYn;

    @CreationTimestamp
    @Column(name = "CRT_DTTM", nullable = false, updatable = false)
    private LocalDateTime crtDttm;

    @Column(name = "USER_EMAIL", unique = true, nullable = false, length = 100)
    private String userEmail;

    @Column(name = "USER_CNTADR", unique = true, nullable = false, length = 11)
    private String userCntadr;

    @Column(name = "USER_ROLE", nullable = false, length = 15)
    private String userRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ROLE", referencedColumnName = "CD", updatable = false, insertable = false)
    private CmncdInfo cmncdInfo;

    @Transient
    public void updatePw(String password) {
        this.userPw = password;
    }

    @Transient
    public UserInfo updateUseYn(char yn) {
        this.useYn = yn;
        return this;
    }

    @Transient
    @PrePersist
    @PreUpdate
    public void checkYn() {
        Yn.checkYn(this.useYn);
    }

}
