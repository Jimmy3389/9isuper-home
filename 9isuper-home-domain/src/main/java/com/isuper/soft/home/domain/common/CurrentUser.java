package com.isuper.soft.home.domain.common;

import java.util.Date;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import com.isuper.soft.home.domain.system.entity.SystemUser;


public class CurrentUser extends User {

	private static final long serialVersionUID = 1L;

	private SystemUser systemUser;

	public CurrentUser(SystemUser login) {
		super(login.getLoginAccount(),
				// username the username presented to the
				// DaoAuthenticationProvider
				login.getLoginPwd(),
				// password the password that should be presented to the
				// DaoAuthenticationProvider
				login.getEnableFlag() && !login.getDelFlag(),
				// enabled set to true if the user is enabled

				login.getExpireDate() != null && login.getExpireDate().after(new Date()),
				// accountNonExpired set to true if the account has not expired
				true,
				// credentialsNonExpired set to true if the credentials have not
				// expired
				login.getLockType() == null || login.getLockType() == 0,
				// accountNonLocked set to true if the account is not locked
				AuthorityUtils.createAuthorityList("USER")
		// authorities the authorities that should be granted to the caller if
		// they presented the correct username and password and the user is
		// enabled. Not null.
		);
		this.systemUser = login;
	}

	public SystemUser getSystemUser() {
		return systemUser;
	}

	public void setSystemUser(SystemUser systemUser) {
		this.systemUser = systemUser;
	}

}
