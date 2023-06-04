package com.codigo.demo.domain.entity;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author sandar linn
 * @CreatedAt June 3, 2023
 */
@Entity
@Data
@Table(name = "USER_MASTER")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserMaster implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME", columnDefinition = "TEXT")
	private String name;

	@Column(name = "PASSWORD", columnDefinition = "VARCHAR(100)")
	private String password;

	@Column(name = "LOGIN_NAME", columnDefinition = "VARCHAR(100)", unique = true)
	private String loginName;

	@Column(name = "EMAIL", columnDefinition = "VARCHAR(50)")
	private String email;

	@Column(name = "PHONE", columnDefinition = "VARCHAR(20)")
	private String phone;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_AT", nullable = false)
	private Date createdAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
