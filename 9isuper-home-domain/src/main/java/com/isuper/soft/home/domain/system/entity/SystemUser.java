package com.isuper.soft.home.domain.system.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Email;

import com.isuper.soft.home.domain.common.DataEntity;

/**
 * 
 * @ClassName: SystemUserLogin
 * @Description: 用户登陆表
 * @author Jimmy YongLe.Chow@9isuper.com
 * @date 2017年8月2日 下午1:05:47
 *
 */
@Entity
@Table(name = "SYSTEM_USER")
public class SystemUser extends DataEntity implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;

	/** 登陆账号 */
	@Column(nullable = false, length = 450)
	private String loginAccount;

	/** 登录密码 */
	@Column(nullable = false, length = 500)
	private String loginPwd;

	/** 昵称 */
	@Column(length = 300)
	private String nickName;

	/** 真实姓名 */
	@Column(length = 300)
	private String realName;

	/** 国际编码 */
	@Column(length = 5)
	private String countryCode;

	/** 手机号码 */
	@Column(nullable = false, length = 20)
	private String mobile;

	/** 邮箱 */
	@Email
	@Column(length = 200)
	private String email;

	/** 账号失效日期 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date expireDate;

	/** 最后修改密码时间 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyPwdDate;

	/** 用户锁定状态 */
	private Integer lockType;

	/** 上次登陆时间 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLoginTime;

	/** 本次登陆时间 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date currentLoginTime;

	/** 最后登陆的IP */
	@Column(length = 40)
	private String lastLoginIp;

	/** 当前登陆的IP地址 */
	@Column(length = 40)
	private String currentloginIp;

	/** 登陆成功数量 */
	private Integer loginCount;

	public String getLoginAccount() {
		return loginAccount;
	}

	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public Date getModifyPwdDate() {
		return modifyPwdDate;
	}

	public void setModifyPwdDate(Date modifyPwdDate) {
		this.modifyPwdDate = modifyPwdDate;
	}

	public Integer getLockType() {
		return lockType;
	}

	public void setLockType(Integer lockType) {
		this.lockType = lockType;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Date getCurrentLoginTime() {
		return currentLoginTime;
	}

	public void setCurrentLoginTime(Date currentLoginTime) {
		this.currentLoginTime = currentLoginTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getCurrentloginIp() {
		return currentloginIp;
	}

	public void setCurrentloginIp(String currentloginIp) {
		this.currentloginIp = currentloginIp;
	}

	public Integer getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

}