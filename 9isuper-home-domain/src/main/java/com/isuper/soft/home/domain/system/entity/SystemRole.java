package com.isuper.soft.home.domain.system.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.isuper.soft.home.domain.common.DataEntity;

/**
 * 
 * @ClassName: SystemRole
 * @Description: 用户登陆表
 * @author Jimmy YongLe.Chow@9isuper.com
 * @date 2017年8月2日 下午1:05:47
 *
 */
@Entity
@Table(name = "SYSTEM_ROLE")
public class SystemRole extends DataEntity implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;

	/** 权限名称 */
	@Column(nullable = false, length = 50)
	private String roleName;

	/** 权限中文名称 */
	@Column(nullable = false, length = 500)
	private String roleCnName;

	/** 权限失效日期 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date expireDate;

	/** 权限锁定状态 */
	private Integer lockType;

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName
	 *            the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return the roleCnName
	 */
	public String getRoleCnName() {
		return roleCnName;
	}

	/**
	 * @param roleCnName
	 *            the roleCnName to set
	 */
	public void setRoleCnName(String roleCnName) {
		this.roleCnName = roleCnName;
	}

	/**
	 * @return the expireDate
	 */
	public Date getExpireDate() {
		return expireDate;
	}

	/**
	 * @param expireDate
	 *            the expireDate to set
	 */
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	/**
	 * @return the lockType
	 */
	public Integer getLockType() {
		return lockType;
	}

	/**
	 * @param lockType
	 *            the lockType to set
	 */
	public void setLockType(Integer lockType) {
		this.lockType = lockType;
	}

}
