package com.example.demo.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "User_Role")
public class User_Role {
	
	@Id
	@Column(name = "role_id", nullable = false)
	private Integer role_id;
	@Column(name = "user_id", nullable = false)
	private Integer user_id;
	public Integer getRole_id() {
		return role_id;
	}
	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
}
