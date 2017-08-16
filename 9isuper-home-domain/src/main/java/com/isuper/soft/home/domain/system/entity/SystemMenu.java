package com.isuper.soft.home.domain.system.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.isuper.soft.home.domain.common.DataEntity;

@Entity
@Table(name = "SYSTEM_MENU")
public class SystemMenu extends DataEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 菜单名称 */
	@Column(length = 300)
	private String menuName;

	/** 鼠标悬停显示 */
	@Column(length = 300)
	private String menuTile;

	/** 权限标识 */
	@Column(length = 300)
	private String roleTag;

	/** 菜单对应的系统ID */
	@Column(length = 20)
	private String systemId;

	/** 菜单对应的url */
	@Column(length = 300)
	private String sourceUrl;

	/** 是否显示菜单 */
	@org.hibernate.annotations.Type(type = "yes_no")
	private Boolean isShow;

	/** 目录的排序 */
	private Integer menuSort;

	/** 父级ID */
	@Column(length = 40)
	private String parentId;
	
	@Column(length = 10)
	private String menuIco;

	/** 所有子目录 */
	@Transient
	private List<SystemMenu> childMenus;

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuTile() {
		return menuTile;
	}

	public void setMenuTile(String menuTile) {
		this.menuTile = menuTile;
	}

	public String getRoleTag() {
		return roleTag;
	}

	public void setRoleTag(String roleTag) {
		this.roleTag = roleTag;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public Boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

	public Integer getMenuSort() {
		return menuSort;
	}

	public void setMenuSort(Integer menuSort) {
		this.menuSort = menuSort;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the menuIco
	 */
	public String getMenuIco() {
		return menuIco;
	}

	/**
	 * @param menuIco the menuIco to set
	 */
	public void setMenuIco(String menuIco) {
		this.menuIco = menuIco;
	}

	public List<SystemMenu> getChildMenus() {
		return childMenus;
	}

	public void setChildMenus(List<SystemMenu> childMenus) {
		this.childMenus = childMenus;
	}

}
