package com.ledar.mono.domain;

import com.ledar.mono.domain.enumeration.SStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 员工表
 */
@Schema(description = "员工表")
@Entity
@Table(name = "staff")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Staff implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 员工编号
     */
    @Schema(description = "员工编号", required = true)
    @NotNull
    @Column(name = "staff_id", nullable = false, unique = true)
    private Long staffId;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID", required = true)
    @NotNull
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    /**
     * 姓名
     */
    @Schema(description = "姓名", required = true)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 性别
     */
    @Schema(description = "性别")
    @Column(name = "gender")
    private String gender;

    /**
     * 学历
     */
    @Schema(description = "学历")
    @Column(name = "education")
    private String education;

    /**
     * 专业
     */
    @Schema(description = "专业")
    @Column(name = "major")
    private String major;

    /**
     * 职称
     */
    @Schema(description = "职称")
    @Column(name = "title")
    private String title;

    /**
     * 电话号
     */
    @Schema(description = "电话号")
    @Column(name = "phone_num")
    private String phoneNum;

    /**
     * 联系地址
     */
    @Schema(description = "联系地址")
    @Column(name = "address")
    private String address;

    /**
     * 出生日期
     */
    @Schema(description = "出生日期")
    @Column(name = "birthday")
    private Instant birthday;

    /**
     * 身份证号
     */
    @Schema(description = "身份证号")
    @Column(name = "id_num")
    private Long idNum;

    /**
     * 所属科室
     */
    @Schema(description = "所属科室", required = true)
    @NotNull
    @Column(name = "department_num", nullable = false)
    private String departmentNum;

    /**
     * 员工状态
     */
    @Schema(description = "员工状态")
    @Enumerated(EnumType.STRING)
    @Column(name = "s_status")
    private SStatus sStatus;

    /**
     * 政治面貌
     */
    @Schema(description = "政治面貌")
    @Column(name = "political_affiliation")
    private String politicalAffiliation;

    /**
     * 民族
     */
    @Schema(description = "民族")
    @Column(name = "nationality")
    private String nationality;

    /**
     * 小组ID
     */
    @Schema(description = "小组ID")
    @Column(name = "group_id")
    private Integer groupId;

    /**
     * 是否住院系统导入
     */
    @Schema(description = "是否住院系统导入", required = true)
    @NotNull
    @Column(name = "from_hospital_system", nullable = false)
    private Boolean fromHospitalSystem;

    /**
     * 登录名
     */
    @Schema(description = "登录名")
    @Column(name = "login_name", unique = true)
    private String loginName;

    /**
     * 登录密码
     */
    @Schema(description = "登录密码")
    @Column(name = "login_password")
    private String loginPassword;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Staff id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStaffId() {
        return this.staffId;
    }

    public Staff staffId(Long staffId) {
        this.setStaffId(staffId);
        return this;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public Staff userId(Long userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return this.name;
    }

    public Staff name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return this.gender;
    }

    public Staff gender(String gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEducation() {
        return this.education;
    }

    public Staff education(String education) {
        this.setEducation(education);
        return this;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getMajor() {
        return this.major;
    }

    public Staff major(String major) {
        this.setMajor(major);
        return this;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getTitle() {
        return this.title;
    }

    public Staff title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoneNum() {
        return this.phoneNum;
    }

    public Staff phoneNum(String phoneNum) {
        this.setPhoneNum(phoneNum);
        return this;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAddress() {
        return this.address;
    }

    public Staff address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Instant getBirthday() {
        return this.birthday;
    }

    public Staff birthday(Instant birthday) {
        this.setBirthday(birthday);
        return this;
    }

    public void setBirthday(Instant birthday) {
        this.birthday = birthday;
    }

    public Long getIdNum() {
        return this.idNum;
    }

    public Staff idNum(Long idNum) {
        this.setIdNum(idNum);
        return this;
    }

    public void setIdNum(Long idNum) {
        this.idNum = idNum;
    }

    public String getDepartmentNum() {
        return this.departmentNum;
    }

    public Staff departmentNum(String departmentNum) {
        this.setDepartmentNum(departmentNum);
        return this;
    }

    public void setDepartmentNum(String departmentNum) {
        this.departmentNum = departmentNum;
    }

    public SStatus getsStatus() {
        return this.sStatus;
    }

    public Staff sStatus(SStatus sStatus) {
        this.setsStatus(sStatus);
        return this;
    }

    public void setsStatus(SStatus sStatus) {
        this.sStatus = sStatus;
    }

    public String getPoliticalAffiliation() {
        return this.politicalAffiliation;
    }

    public Staff politicalAffiliation(String politicalAffiliation) {
        this.setPoliticalAffiliation(politicalAffiliation);
        return this;
    }

    public void setPoliticalAffiliation(String politicalAffiliation) {
        this.politicalAffiliation = politicalAffiliation;
    }

    public String getNationality() {
        return this.nationality;
    }

    public Staff nationality(String nationality) {
        this.setNationality(nationality);
        return this;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Integer getGroupId() {
        return this.groupId;
    }

    public Staff groupId(Integer groupId) {
        this.setGroupId(groupId);
        return this;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Boolean getFromHospitalSystem() {
        return this.fromHospitalSystem;
    }

    public Staff fromHospitalSystem(Boolean fromHospitalSystem) {
        this.setFromHospitalSystem(fromHospitalSystem);
        return this;
    }

    public void setFromHospitalSystem(Boolean fromHospitalSystem) {
        this.fromHospitalSystem = fromHospitalSystem;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public Staff loginName(String loginName) {
        this.setLoginName(loginName);
        return this;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPassword() {
        return this.loginPassword;
    }

    public Staff loginPassword(String loginPassword) {
        this.setLoginPassword(loginPassword);
        return this;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Staff)) {
            return false;
        }
        return id != null && id.equals(((Staff) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Staff{" +
            "id=" + getId() +
            ", staffId=" + getStaffId() +
            ", userId=" + getUserId() +
            ", name='" + getName() + "'" +
            ", gender='" + getGender() + "'" +
            ", education='" + getEducation() + "'" +
            ", major='" + getMajor() + "'" +
            ", title='" + getTitle() + "'" +
            ", phoneNum='" + getPhoneNum() + "'" +
            ", address='" + getAddress() + "'" +
            ", birthday='" + getBirthday() + "'" +
            ", idNum=" + getIdNum() +
            ", departmentNum='" + getDepartmentNum() + "'" +
            ", sStatus='" + getsStatus() + "'" +
            ", politicalAffiliation='" + getPoliticalAffiliation() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", groupId=" + getGroupId() +
            ", fromHospitalSystem='" + getFromHospitalSystem() + "'" +
            ", loginName='" + getLoginName() + "'" +
            ", loginPassword='" + getLoginPassword() + "'" +
            "}";
    }
}
