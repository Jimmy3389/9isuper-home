package com.isuper.soft.home.domain.oauth.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "OAUTH_CODE")
public class OauthCode implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(length = 255)
	private String code;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(columnDefinition = "BLOB")
	private String authentication;

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
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
