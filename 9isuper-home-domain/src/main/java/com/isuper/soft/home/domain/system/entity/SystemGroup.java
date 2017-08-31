package com.isuper.soft.home.domain.system.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

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
	@ManyToMany(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	private List<SystemMenu> systemMenus;

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

	
}
