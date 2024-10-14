package egovframework.cbm.web.authip.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 인가 IP 엔티티
 *
 * @author  이상민
 * @since   2024.07.17 12:00
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "AUTHRZ_IP_CTLG")
public class AuthrzIpCtlg implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AUTHRZ_IP_CTLG_SEQ", nullable = false, updatable = false)
    private int authrzIpCtlgSeq;

    @Column(name = "AUTHRZ_IP_CTLG", unique = true, nullable = false, length = 50)
    private String authrzIpCtlg;

    @Column(name = "EXPLN", nullable = false, length = 4000)
    private String expln;

    @Column(name = "REGSTR", nullable = false, updatable = false, length = 50)
    private String regstr;

    @CreationTimestamp
    @Column(name = "RGST_DTTM")
    private LocalDateTime restDttm;

    @Column(name = "MDFR", insertable = false, length = 50)
    private String mdfr;

    @UpdateTimestamp
    @Column(name = "MDFR_DTTM", insertable = false)
    private LocalDateTime mdfrDttm;

}
