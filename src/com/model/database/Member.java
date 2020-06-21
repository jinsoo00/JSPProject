package com.model.database;

public class Member {
	private String id;
	private String pwd;
	private String name;
	private String tel;
	private String email;
	private String dept;
	private String gender;
	private String birth;
	private String introduction;
	
	public Member(String id, String pwd, String name, String tel, String email, String dept,
			String gender, String birth, String introduction) {
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.tel = tel;
		this.email = email;
		this.dept = dept;
		this.gender = gender;
		this.birth = birth;
		this.introduction = introduction;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	
	
}
