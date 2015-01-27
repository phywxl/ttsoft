package ttsoft.user.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ttsoft.user.RoleVo;
import ttsoft.user.UserVo;
import ttsoft.util.CommonUtil;


public class UserDetailsImpl implements UserDetails, Serializable {
	private String username;
	private String password;
	private String temped;
	private long startDate;
	private long endDate;
	private String enabled;
	private String locked;
	private List<String> roleCodes;
	private Collection<GrantedAuthority> grantedAuthorities;
	
	public UserDetailsImpl(UserVo user) {
		this(user.getUserLoginName(), user.getUserPassword(), user.getUserTemped()
				, user.getUserStartDate() == null ? 0 : user.getUserStartDate().getTime()
				, user.getUserEndDate() == null ? Long.MAX_VALUE : user.getUserEndDate().getTime()
				, user.getUserEnabled(), user.getUserLocked(), null);
		
		List<String> roleCodes = new ArrayList<String>();
		
		if (user.getRoleVos() != null) {
			for (RoleVo roleVo : user.getRoleVos()) {
				if (roleVo.getRoleCode() != null && !roleVo.getRoleCode().trim().equals("")) {
					roleCodes.add(roleVo.getRoleCode().trim());
				}
			}
		}
		this.setRoleCodes(roleCodes);
	}
	public UserDetailsImpl(String username, String password, String temped, long startDate, long endDate, String enabled, String locked, List<String> roleCodes) {
		this.username = username;
		this.password = password;
		this.temped = temped;
		this.startDate = startDate;
		this.endDate = endDate;
		this.enabled = enabled;
		this.locked = locked;
		this.roleCodes = roleCodes;
		grantedAuthorities = new HashSet<GrantedAuthority>();
		if (roleCodes != null) {
			for (String code : roleCodes) {
				if (code != null && !code.trim().equals(""))
					grantedAuthorities.add(new GrantedAuthorityImpl(code.trim()));
			}
		}
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return grantedAuthorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		if (CommonUtil.isEnabled(this.temped)) {
			long current = System.currentTimeMillis();
			return (startDate < current && current < endDate);
		}
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !CommonUtil.isEnabled(this.locked);
	}

	@Override
	public boolean isCredentialsNonExpired() {
		if (CommonUtil.isEnabled(this.temped)) {
			long current = System.currentTimeMillis();
			return (startDate < current && current < endDate);
		}
		return true;
	}

	@Override
	public boolean isEnabled() {
		return CommonUtil.isEnabled(this.enabled);
	}
	
	private void setRoleCodes(List<String> roleCodes) {
		this.roleCodes = roleCodes;
		if (this.grantedAuthorities == null) {
			grantedAuthorities = new HashSet<GrantedAuthority>();
		}
		if (roleCodes != null) {
			for (String code : roleCodes) {
				if (code != null && !code.trim().equals(""))
					grantedAuthorities.add(new GrantedAuthorityImpl(code.trim()));
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDetailsImpl other = (UserDetailsImpl) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return this.getClass().getName() + "[" + this.username + ", " + this.roleCodes + "]";
	}
}
