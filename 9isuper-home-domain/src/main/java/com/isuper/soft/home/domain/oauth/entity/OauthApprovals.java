package com.isuper.soft.home.domain.oauth.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.isuper.soft.home.domain.common.IdEntity;

@Entity
@Table(name = "OAUTH_APPROVALS")
public class OauthApprovals extends IdEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(length = 255)
	private String userId;

	@Column(length = 255)
	private String clientId;

	@Column(length = 255)
	private String scope;

	@Column(length = 10)
	private String status;

	@Temporal(TemporalType.TIMESTAMP)
	private Date expiresAt;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedAt;

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the clientId
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * @param clientId
	 *            the clientId to set
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/**
	 * @return the scope
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * @param scope
	 *            the scope to set
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the expiresAt
	 */
	public Date getExpiresAt() {
		return expiresAt;
	}

	/**
	 * @param expiresAt
	 *            the expiresAt to set
	 */
	public void setExpiresAt(Date expiresAt) {
		this.expiresAt = expiresAt;
	}

	/**
	 * @return the lastModifiedAt
	 */
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * @param lastModifiedAt
	 *            the lastModifiedAt to set
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

}
