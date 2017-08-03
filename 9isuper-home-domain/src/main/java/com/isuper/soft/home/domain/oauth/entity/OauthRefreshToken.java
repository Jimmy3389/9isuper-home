package com.isuper.soft.home.domain.oauth.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "OAUTH_REFRESH_TOKEN")
public class OauthRefreshToken implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(length = 255)
	private String tokenId;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(columnDefinition = "BLOB")
	private String token;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(columnDefinition = "BLOB")
	private String authentication;

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
	 * @return the authentication
	 */
	public String getAuthentication() {
		return authentication;
	}

	/**
	 * @param authentication
	 *            the authentication to set
	 */
	public void setAuthentication(String authentication) {
		this.authentication = authentication;
	}

}
