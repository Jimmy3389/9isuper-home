package com.isuper.soft.home.domain.oauth.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CLIENT_DETAILS")
public class ClientDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 255)
	private String appId;

	@Column(length = 255)
	private String resourceIds;

	@Column(length = 255)
	private String appSecret;

	@Column(length = 255)
	private String scope;

	@Column(length = 255)
	private String grantTypes;

	@Column(length = 255)
	private String redirectUrl;

	@Column(length = 255)
	private String authorities;

	/** 登陆成功数量 */
	private Integer accessTokenValidity;

	/** 登陆成功数量 */
	private Integer refreshTokenValidity;

	@Column(length = 4096)
	private String additionalInformation;

	@Column(length = 255)
	private String autoApproveScopes;

	/**
	 * @return the appId
	 */
	public String getAppId() {
		return appId;
	}

	/**
	 * @param appId the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}

	/**
	 * @return the resourceIds
	 */
	public String getResourceIds() {
		return resourceIds;
	}

	/**
	 * @param resourceIds the resourceIds to set
	 */
	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}

	/**
	 * @return the appSecret
	 */
	public String getAppSecret() {
		return appSecret;
	}

	/**
	 * @param appSecret the appSecret to set
	 */
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	/**
	 * @return the scope
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * @param scope the scope to set
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}

	/**
	 * @return the grantTypes
	 */
	public String getGrantTypes() {
		return grantTypes;
	}

	/**
	 * @param grantTypes the grantTypes to set
	 */
	public void setGrantTypes(String grantTypes) {
		this.grantTypes = grantTypes;
	}

	/**
	 * @return the redirectUrl
	 */
	public String getRedirectUrl() {
		return redirectUrl;
	}

	/**
	 * @param redirectUrl the redirectUrl to set
	 */
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	/**
	 * @return the authorities
	 */
	public String getAuthorities() {
		return authorities;
	}

	/**
	 * @param authorities the authorities to set
	 */
	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}

	/**
	 * @return the accessTokenValidity
	 */
	public Integer getAccessTokenValidity() {
		return accessTokenValidity;
	}

	/**
	 * @param accessTokenValidity the accessTokenValidity to set
	 */
	public void setAccessTokenValidity(Integer accessTokenValidity) {
		this.accessTokenValidity = accessTokenValidity;
	}

	/**
	 * @return the refreshTokenValidity
	 */
	public Integer getRefreshTokenValidity() {
		return refreshTokenValidity;
	}

	/**
	 * @param refreshTokenValidity the refreshTokenValidity to set
	 */
	public void setRefreshTokenValidity(Integer refreshTokenValidity) {
		this.refreshTokenValidity = refreshTokenValidity;
	}

	/**
	 * @return the additionalInformation
	 */
	public String getAdditionalInformation() {
		return additionalInformation;
	}

	/**
	 * @param additionalInformation the additionalInformation to set
	 */
	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	/**
	 * @return the autoApproveScopes
	 */
	public String getAutoApproveScopes() {
		return autoApproveScopes;
	}

	/**
	 * @param autoApproveScopes the autoApproveScopes to set
	 */
	public void setAutoApproveScopes(String autoApproveScopes) {
		this.autoApproveScopes = autoApproveScopes;
	}

	
}
