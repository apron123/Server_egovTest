package egovframework.cbm.web.dctn.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import egovframework.cbm.web.common.model.entity.CmncdInfo;
import egovframework.utils.enums.Yn;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 데이터 사전 도메인 정보 엔티티
 *
 * @author 이상민
 * @since 2024.07.17 12:00
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "DCTN_DOMAIN_INFO")
public class DctnDomainInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DCTN_DOMAIN_INFO_SEQ", nullable = false, updatable = false)
    private int dctnDomainInfoSeq;

    @Column(name = "DOMAIN_NM", nullable = false, unique = true, length = 50)
    private String domainNm;

    @Column(name = "DOMAIN_CLSFCT_WORD", nullable = false, length = 50)
    private String domainClsfctWord;

    @Column(name = "LOGIC_DATA_TYPE", nullable = false, length = 15)
    private String logicDataType;

    @Column(name = "DOMAIN_TYPE", nullable = false, length = 15)
    private String domainType;

    @Column(name = "RPST_YN", nullable = false, length = 1)
    private char rpstYn;

    @Column(name = "CLSFCT", nullable = false, length = 15)
    private String clsfct;

    @Column(name = "WRTR", nullable = false, updatable = false, length = 50)
    private String wrtr;

    @CreationTimestamp
    @Column(name = "CRT_DTTM", nullable = false, updatable = false)
    private LocalDateTime crtDttm;

    @UpdateTimestamp
    @Column(name = "MDFC_DTTM", insertable = false)
    private LocalDateTime mdfcDttm;

    @Column(name = "MDFR", insertable = false, length = 50)
    private String mdfr;

    @Column(name = "MDFR_RSN", length = 500)
    private String mdfrRsn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOGIC_DATA_TYPE", referencedColumnName = "CD", updatable = false, insertable = false)
    private CmncdInfo logicDataTypeInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOMAIN_TYPE", referencedColumnName = "CD", updatable = false, insertable = false)
    private CmncdInfo domainTypeInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLSFCT", referencedColumnName = "CD", updatable = false, insertable = false)
    private CmncdInfo cmncdInfo;

    @OneToOne(mappedBy = "dctnDomainInfo")
    private ApprvPtclsDomain apprvPtclsDomain;

    @PrePersist
    @PreUpdate
    public void checkYn() {
        Yn.checkYn(this.rpstYn);
    }

}
