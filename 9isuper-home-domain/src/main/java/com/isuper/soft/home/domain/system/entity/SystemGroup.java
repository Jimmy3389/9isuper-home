package com.isuper.soft.home.domain.system.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.isuper.soft.home.domain.common.DataEntity;

@Entity
@Table(name = "SYSTEM_GROUP")
public class SystemGroup extends DataEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 群组名称 */
	@Column(length = 300)
	private String groupName;

	/** 群组英文名称（代号） */
	@Column(length = 300)
	private String groupCode;

	// 配置用户与角色多对多关系
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "SYSTEM_GROUP_SYSTEM_MENUS", joinColumns = { @JoinColumn(name = "SYSTEM_GROUP_ID") }, inverseJoinColumns = { @JoinColumn(name = "SYSTEM_MENUS_ID") })
	@OrderBy("menuSort")
	private List<SystemMenu> systemMenus;

	// 配置用户与角色多对多关系
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "systemGroups",fetch=FetchType.LAZY)
	private List<SystemUser> systemUsers;
	
	@Transient
	private String menus;
	
	@Transient
	private String users;
	
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<SystemMenu> getSystemMenus() {
		return systemMenus;
	}

	public void setSystemMenus(List<SystemMenu> systemMenus) {
		this.systemMenus = systemMenus;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public List<SystemUser> getSystemUsers() {
		return systemUsers;
	}

	public void setSystemUsers(List<SystemUser> systemUsers) {
		this.systemUsers = systemUsers;
	}

	public String getMenus() {
		StringBuffer sb = new StringBuffer();
		if (CollectionUtils.isNotEmpty(this.systemMenus)) {
			for (SystemMenu systemMenu : this.systemMenus) {
				sb.append(systemMenu.getMenuName());
				if(StringUtils.isNotBlank(systemMenu.getRoleTag())) {
					sb.append("("+systemMenu.getRoleTag()+")");
				}
				sb.append(",");
			}
		}
		String users = sb.toString();
		return StringUtils.isNotBlank(users) ? users.substring(0, users.length() - 1) : "";
	}

	public void setMenus(String menus) {
		this.menus = menus;
	}

	public String getUsers() {
		StringBuffer sb = new StringBuffer();
		if (CollectionUtils.isNotEmpty(this.systemUsers)) {
			for (SystemUser systemUser : this.systemUsers) {
				sb.append(systemUser.getNickName());
				if(StringUtils.isNotBlank(systemUser.getRealName())) {
					sb.append("("+systemUser.getRealName()+")");
				}
				sb.append(",");
			}
		}
		String users = sb.toString();
		return StringUtils.isNotBlank(users) ? users.substring(0, users.length() - 1) : "";
	}

	public void setUsers(String users) {
		this.users = users;
	}

	
	
}
