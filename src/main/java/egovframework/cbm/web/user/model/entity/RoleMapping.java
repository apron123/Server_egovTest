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
@Table(name = "ROLE_MAPPING")
public class RoleMapping implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_MAPPING_SEQ", nullable = false, updatable = false)
    private int roleMappingSeq;

    @Column(name = "USER_ROLE", nullable = false, length = 15)
    private String userRoleCode;

    @Column(name = "SYSTEM", nullable = false, length = 15)
    private String systemCode;

}
