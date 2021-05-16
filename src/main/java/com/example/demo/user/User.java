package com.example.demo.user;

import javax.persistence.*;



@Entity
@Table(name = "User")
public class User {
	@Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	private String role;
	@Column(nullable = true, length = 64)
    private String photos;
	//String PhotosImagePath;
	@Transient
    public String getPhotosImagePath() {
        if (photos == null || id == null) return null;
         
        return "/user-photos/" + id + "/" + photos;
    }
	public String getPhotos() {
		return photos;
	}
	public void setPhotos(String photos) {
		this.photos = photos;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return id;
	}

	@Column(name = "first_name", nullable = false, length = 20)
	private String first_name;

	@Column(name = "last_name", nullable = false, length = 20)
	private String last_name;

	@Column(name = "email", nullable = false, unique = true, length = 45)
	private String email;

	@Column(name = "password", nullable = false, length = 64)
	private String password;
	
	@Column(name = "enabled")
	private Integer enabled;

	
	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	
	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

