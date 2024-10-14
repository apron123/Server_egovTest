package egovframework.cbm.web.dctn.model.entity;

import egovframework.cbm.web.common.model.entity.CmncdInfo;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 데이터 사전 정보 엔티티
 *
 * @author  이상민
 * @since   2024.07.08 12:00
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "DCTN_INFO")
public class DctnInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = -6877217338108617548L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DCTN_INFO_SEQ", nullable = false, updatable = false)
    private int dctnInfoSeq;

    @Column(name = "ABBRNM", nullable = false, length = 50)
    private String abbrnm;

    @Column(name = "WORD_NM", nullable = false, length = 300)
    private String wordNm;

    @Column(name = "WORD_EXPLN", nullable = false, length = 4000)
    private String wordExpln;

    @Column(name = "ENGNM", nullable = false, length = 300)
    private String engnm;

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

    @Column(name = "MDFR_RSN", insertable = false, length = 500)
    private String mdfrRsn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLSFCT", referencedColumnName = "CD", updatable = false, insertable = false)
    private CmncdInfo cmncdInfo;

    @OneToOne(mappedBy = "dctnInfo")
    private ApprvPtcls apprvPtcls;

}
