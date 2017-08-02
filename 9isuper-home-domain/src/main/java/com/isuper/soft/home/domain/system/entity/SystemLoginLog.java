package com.isuper.soft.home.domain.system.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.isuper.soft.home.domain.common.IdEntity;

/**
 * 
 * @ClassName: SystemLoginLog
 * @Description: 用户登陆日志表
 * @author Jimmy YongLe.Chow@9isuper.com
 * @date 2017年8月2日 下午1:05:47
 *
 */
@Entity
@Table(name = "SYSTEM_LOGIN_LOG")
public class SystemLoginLog extends IdEntity implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;

	/** 登陆账号 */
	@Column(nullable = false, length = 450)
	@Max(value = 400)
	@Min(value = 3)
	private String loginStr;

	/** 用户ID号码 */
	private Long UserId;

	/** 国际编码 */
	@Column(length = 5)
	private String countryCode;

	/** 本次登陆时间 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date currentLoginTime;

	/** 当前登陆的IP地址 */
	@Column(length = 40)
	private String currentloginIp;

	@org.hibernate.annotations.Type(type = "yes_no")
	private Boolean loginResult;

	/** 登陆成功数量 */
	private Integer loginCount;

	public String getLoginStr() {
		return loginStr;
	}

	public void setLoginStr(String loginStr) {
		this.loginStr = loginStr;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public Date getCurrentLoginTime() {
		return currentLoginTime;
	}

	public void setCurrentLoginTime(Date currentLoginTime) {
		this.currentLoginTime = currentLoginTime;
	}

	public String getCurrentloginIp() {
		return currentloginIp;
	}

	public void setCurrentloginIp(String currentloginIp) {
		this.currentloginIp = currentloginIp;
	}

	public Boolean getLoginResult() {
		return loginResult;
	}

	public void setLoginResult(Boolean loginResult) {
		this.loginResult = loginResult;
	}

	public Integer getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

	public Long getUserId() {
		return UserId;
	}

	public void setUserId(Long userId) {
		this.UserId = userId;
	}

}
