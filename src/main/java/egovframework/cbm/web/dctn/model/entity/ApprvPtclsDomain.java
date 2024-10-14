package egovframework.cbm.web.dctn.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 도메인 사전 승인 내역 엔티티
 *
 * @author  이상민
 * @since   2024.07.08 12:00
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "APPRV_PTCLS_DOMAIN")
public class ApprvPtclsDomain implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "APPRV_PTCLS_DOMAIN_SEQ", nullable = false, updatable = false)
    private int apprvPtclsDomainSeq;

    @Column(name = "DCTN_DOMAIN_INFO_SEQ", nullable = false)
    private int dctnDomainInfoSeq;

    @Column(name = "APPRV", length = 50 ,insertable = false)
    private String apprv;

    @UpdateTimestamp
    @Column(name = "APPRV_DTTM", insertable = false)
    private LocalDateTime apprvDttm;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DCTN_DOMAIN_INFO_SEQ", updatable = false, insertable = false)
    private DctnDomainInfo dctnDomainInfo;

    @Transient
    public ApprvPtclsDomain updateApprv(String userId) {
        this.apprv = userId;
        return this;
    }

}
