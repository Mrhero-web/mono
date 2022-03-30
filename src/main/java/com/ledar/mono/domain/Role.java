package com.ledar.mono.domain;

import com.ledar.mono.domain.enumeration.RoleName;
import com.ledar.mono.domain.enumeration.Status;
import com.ledar.mono.domain.enumeration.WebOrApp;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 角色表
 */
@Schema(description = "角色表")
@Entity
@Table(name = "sys_role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 角色编号
     */
    @Schema(description = "角色编号", required = true)
    @NotNull
    @Column(name = "role_id", nullable = false, unique = true)
    private Long roleId;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称", required = true)
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role_type_name", nullable = false)
    private RoleName roleTypeName;

    /**
     * 角色状态
     */
    @Schema(description = "角色状态")
    @Enumerated(EnumType.STRING)
    @Column(name = "role_status")
    private Status roleStatus;

    /**
     * 操作平台
     */
    @Schema(description = "操作平台")
    @Enumerated(EnumType.STRING)
    @Column(name = "web_or_app")
    private WebOrApp webOrApp;

    /**
     * 角色编号,形如 ROLE_ADMIN,必须以ROLE_作为前缀(SpringSecurity要求)
     */
    @Schema(description = "角色编号,形如 ROLE_ADMIN,必须以ROLE_作为前缀(SpringSecurity要求)", required = true)
    @NotNull
    @Column(name = "role_code", nullable = false, unique = true)
    private String roleCode;
    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getRoleCode() {
        return this.roleCode;
    }

    public Role roleCode(String roleCode) {
        this.setRoleCode(roleCode);
        return this;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
    public Long getId() {
        return this.id;
    }

    public Role id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return this.roleId;
    }

    public Role roleId(Long roleId) {
        this.setRoleId(roleId);
        return this;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public RoleName getRoleTypeName() {
        return this.roleTypeName;
    }

    public Role roleTypeName(RoleName roleTypeName) {
        this.setRoleTypeName(roleTypeName);
        return this;
    }

    public void setRoleTypeName(RoleName roleTypeName) {
        this.roleTypeName = roleTypeName;
    }

    public Status getRoleStatus() {
        return this.roleStatus;
    }

    public Role roleStatus(Status roleStatus) {
        this.setRoleStatus(roleStatus);
        return this;
    }

    public void setRoleStatus(Status roleStatus) {
        this.roleStatus = roleStatus;
    }

    public WebOrApp getWebOrApp() {
        return this.webOrApp;
    }

    public Role webOrApp(WebOrApp webOrApp) {
        this.setWebOrApp(webOrApp);
        return this;
    }

    public void setWebOrApp(WebOrApp webOrApp) {
        this.webOrApp = webOrApp;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Role)) {
            return false;
        }
        return id != null && id.equals(((Role) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Role{" +
            "id=" + getId() +
            ", roleId=" + getRoleId() +
            ", roleTypeName='" + getRoleTypeName() + "'" +
            ", roleStatus='" + getRoleStatus() + "'" +
            ", webOrApp='" + getWebOrApp() + "'" +
            "}";
    }
}
