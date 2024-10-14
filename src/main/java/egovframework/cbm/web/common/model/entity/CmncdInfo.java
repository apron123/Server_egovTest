package egovframework.cbm.web.common.model.entity;

import egovframework.utils.enums.Yn;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 표준플랫폼 공통코드 정보 엔티티
 *
 * @author  이상민
 * @since   2024.07.08 12:00
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "CMNCD_INFO")
public class CmncdInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = -654001961800511838L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CMNCD_INFO_SEQ", nullable = false, updatable = false)
    private int cmncdInfoSeq;

    @Column(name = "CD", nullable = false, unique = true, length = 15)
    private String cd;

    @Column(name = "CD_DESC", nullable = false, length = 500)
    private String cdDesc;

    @Column(name = "CD_NM", nullable = false, length = 50)
    private String cdNm;

    @CreationTimestamp
    @Column(name = "CRT_DTTM", nullable = false, updatable = false)
    private LocalDateTime crtDttm;

    @UpdateTimestamp
    @Column(name = "MDFC_DTTM", nullable = false, insertable = false)
    private LocalDateTime mdfcDttm;

    @Column(name = "USE_YN", nullable = false, length = 1)
    private char useYn;

    @Column(name = "GRP_CD", nullable = false, length = 15)
    private String grpCd;

    @Column(name = "GRP_YN", nullable = false, length = 1)
    private char grpYn;

    @PrePersist
    @PreUpdate
    public void checkYn() {
        Yn.checkYn(this.useYn);
        Yn.checkYn(this.grpYn);
    }

}
