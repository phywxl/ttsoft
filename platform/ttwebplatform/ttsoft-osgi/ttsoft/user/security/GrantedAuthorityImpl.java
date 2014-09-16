package ttsoft.user.security;

import java.io.Serializable;

import org.springframework.security.core.GrantedAuthority;


public class GrantedAuthorityImpl implements GrantedAuthority, Serializable {
	private String authority;
	
	public GrantedAuthorityImpl(String authority) {
		this.authority = authority;
	}
	
	@Override
	public String getAuthority() {
		return authority;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((authority == null) ? 0 : authority.hashCode());
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
		GrantedAuthorityImpl other = (GrantedAuthorityImpl) obj;
		if (authority == null) {
			if (other.authority != null)
				return false;
		} else if (!authority.equals(other.authority))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.getClass().getName() + "[" + this.authority + "]";
	}
	
	
}
