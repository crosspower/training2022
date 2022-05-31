package com.example.demo.app.employee;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


public class EmployeeForm {
	
	@Size(max=10, message="{Size.code}")
	@NotBlank(message = "{NotBlank.code}")
	private String code;
	
	@Size(max=50, message="{Size.name}")
	@NotBlank(message = "{NotBlank.name}")
	private String name;
	
	@Size(max=20, message="{Size.password}")
	@NotBlank(message = "{NotBlank.password}")
	private String password;
	
	@NotBlank(message = "{NotBlank.passwordConfirm}")
	private String passwordConfirm;
	
	@Max(9)
	@Min(0)
	private int role;
	
	private boolean newEmployee;

	public EmployeeForm() {
	}

	public EmployeeForm(String code, 
			String name,
			String password,
			String passwordConfirm,
			int role,
			boolean newEmployee) {
		this.code = code;
		this.name = name;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
		this.role = role;
		this.newEmployee = newEmployee;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public boolean isNewEmployee() {
		return newEmployee;
	}

	public void setNewEmployee(boolean newEmployee) {
		this.newEmployee = newEmployee;
	}

	@AssertTrue(message = "{E0007}")
	public boolean isPasswordValid() {
		if (password == null || password.isEmpty()) {
            return true;
        }

        return password.equals(passwordConfirm);
	}
	
	/*220530レビュー申請後に追加
	@AssertTrue(message = "{E0006}")
	public boolean isExistCodeValid(EmployeeService employeeService) {　
		if (code == null || code.isEmpty()) {
			return true;
		}
		boolean existCode = employeeService.isExistCode(code);
		return existCode;
	}
	*/

}
