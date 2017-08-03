package com.isuper.soft.home.domain.oauth.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "OAUTH_CLIENT_TOKEN")
public class OauthClientToken implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(length = 255)
	private String tokenId;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(columnDefinition = "BLOB")
	private String token;

	@Id
	@Column(length = 255)
	private String authenticationId;

	@Column(length = 255)
	private String userName;

	@Column(length = 255)
	private String clientId;

	/**
	 * @return the tokenId
	 */
	public String getTokenId() {
		return tokenId;
	}

	/**
	 * @param tokenId
	 *            the tokenId to set
	 */
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the authenticationId
	 */
	public String getAuthenticationId() {
		return authenticationId;
	}

	/**
	 * @param authenticationId
	 *            the authenticationId to set
	 */
	public void setAuthenticationId(String authenticationId) {
		this.authenticationId = authenticationId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
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

}
