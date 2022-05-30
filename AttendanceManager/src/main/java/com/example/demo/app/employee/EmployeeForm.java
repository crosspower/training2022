package com.example.demo.app.employee;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;


public class EmployeeForm {
	
	/* 疑問点①
	 * String message = messageSource.getMessage("E0006", "社員番号", Locale.JAPAN);
	 * のようにメッセージの{0}に「社員番号」などの文言を渡したいが、
	 * @NotBlank(message=message)
	 * としても意図したエラーメッセージが表示されない。
	 * 
	 * 疑問点②
	 * 社員番号は主キーであるが、編集・追加で主キーを任意の文字列に指定したものの、
	 * 既存の社員番号と重複した際にWhitelabel Error Pageが出る。
	 * 対処法を探しても見つからない。
	 */
	@Size(max=10, message="{E0005}")
	@NotBlank(message = "{E0006}")
	private String code;
	
	@NotBlank(message = "{E0006}")
	private String name;
	
	@NotBlank(message = "{E0006}")
	private String password;
	
	@NotBlank(message = "{E0006}")
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

	@AssertTrue(message = "パスワードが一致しません")
	public boolean isPasswordValid() {
		if (password == null || password.isEmpty()) {
            return true;
        }

        return password.equals(passwordConfirm);
	}
	

}
