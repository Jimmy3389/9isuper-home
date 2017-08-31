package com.isuper.soft.home.domain.system.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.isuper.soft.home.domain.common.DataEntity;

/**
 * 
 * @ClassName: SystemUser
 * @Description: 用户登陆表
 * @author Jimmy YongLe.Chow@9isuper.com
 * @date 2017年8月2日 下午1:05:47
 *
 */
@Entity
@Table(name = "SYSTEM_USER")
public class SystemUser extends DataEntity implements UserDetails {

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

	/** 用户的图片信息 */
	@Column(length = 512)
	private String userPic;

	/** 会员生日 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date birthDay;

	// 配置用户与角色多对多关系
	// @ManyToMany(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "SYSTEM_USER_SYSTEM_GROUPS", joinColumns = { @JoinColumn(name = "SYSTEM_USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "SYSTEM_GROUPS_ID") })
	private List<SystemGroup> systemGroups;

	@Transient
	private String userGroups;

	// 将用户的角色作为权限
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		List<SystemGroup> groups = this.getSystemGroups();
		if (CollectionUtils.isNotEmpty(groups)) {
			for (SystemGroup group : groups) {
				if (StringUtils.equals(group.getGroupCode().toLowerCase(), "admin")) {
					auths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
				}
				if (!group.getDelFlag() && group.getEnableFlag() && CollectionUtils.isNotEmpty(group.getSystemMenus())) {
					for (SystemMenu menu : group.getSystemMenus()) {
						auths.add(new SimpleGrantedAuthority(menu.getRoleTag()));
					}
				}
			}
		}
		// 默认游客权限
		if (CollectionUtils.isEmpty(auths)) {
			auths.add(new SimpleGrantedAuthority("ROLE_GUEST"));
		}
		return auths;
	}

	public Set<SystemMenu> getUserMenu() {

		Set<SystemMenu> menus = new TreeSet<>(new Comparator<SystemMenu>() {
			@Override
			public int compare(SystemMenu o1, SystemMenu o2) {
				return o2.getMenuSort().compareTo(o1.getMenuSort());
			}
		});

		List<SystemGroup> groups = this.getSystemGroups();
		if (CollectionUtils.isNotEmpty(groups)) {
			for (SystemGroup group : groups) {
				if (!group.getDelFlag() && group.getEnableFlag() && CollectionUtils.isNotEmpty(group.getSystemMenus())) {
					for (SystemMenu menu : group.getSystemMenus()) {
						menus.add(menu);
					}
				}
			}
		}
		return menus;
	}

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

	public String getUserPic() {
		return userPic;
	}

	public void setUserPic(String userPic) {
		this.userPic = userPic;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	@Override
	public String getPassword() {
		return loginPwd;
	}

	@Override
	public String getUsername() {
		return loginAccount;
	}

	@Override
	public boolean isAccountNonExpired() {
		return expireDate == null || expireDate.after(new Date());
	}

	@Override
	public boolean isAccountNonLocked() {
		return lockType == null || lockType == 0;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return super.getEnableFlag();
	}

	public List<SystemGroup> getSystemGroups() {
		return systemGroups;
	}

	public void setSystemGroups(List<SystemGroup> systemGroups) {
		this.systemGroups = systemGroups;
	}

	public String getUserGroups() {
		StringBuffer sb = new StringBuffer();
		if (CollectionUtils.isNotEmpty(systemGroups)) {
			for (SystemGroup systemGroup : systemGroups) {
				sb.append(systemGroup.getGroupName()).append(",");
			}
		}
		String groups = sb.toString();
		return StringUtils.isNotBlank(groups) ? groups.substring(0, groups.length() - 1) : "";
	}

	public void setUserGroups(String userGroups) {
		this.userGroups = userGroups;
	}

}
