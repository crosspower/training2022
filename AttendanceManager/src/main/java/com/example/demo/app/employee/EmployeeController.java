package com.example.demo.app.employee;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
    MessageSource messagesource;
	
	private final EmployeeService employeeService;
	
	@Autowired
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	@GetMapping
	public String index(Model model, Authentication auth) {
		List<Employee> list = employeeService.getAll();
		
		model.addAttribute("empList", list);
		model.addAttribute("title", "社員一覧");
		
		// ログイン情報
		model.addAttribute("code", auth.getName());
		model.addAttribute("name", auth.getDetails());
		model.addAttribute("role", auth.getAuthorities().toString());
		
		return "employee/emp_index";
	}
	
	@GetMapping("/form")
	public String form(EmployeeForm employeeForm, Model model,
			Authentication auth) {
		model.addAttribute("title", "新規追加");
		
		// ログイン情報
		model.addAttribute("code", auth.getName());
		model.addAttribute("name", auth.getDetails());
		model.addAttribute("role", auth.getAuthorities().toString());
				
		return "employee/emp_new";
	}
	
	@PostMapping("/form")
	public String formGoBack(EmployeeForm employeeForm,Model model,
			Authentication auth) {
		model.addAttribute("title", "新規追加");
		
		// ログイン情報
		model.addAttribute("code", auth.getName());
		model.addAttribute("name", auth.getDetails());
		model.addAttribute("role", auth.getAuthorities().toString());
		
		return "employee/emp_new";
	}
	
	@PostMapping("/new")
	public String newEmp(@Validated EmployeeForm employeeForm,
			BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes,
			Authentication auth) {
		
		// ログイン情報
		model.addAttribute("code", auth.getName());
		model.addAttribute("name", auth.getDetails());
		model.addAttribute("role", auth.getAuthorities().toString());
				
		
		if(result.hasErrors()) {
			model.addAttribute("title", "新規追加");
			return "employee/emp_new";
		} 
		Employee employee = new Employee();
		employee.setCode(employeeForm.getCode());
		employee.setName(employeeForm.getName());
		employee.setPassword(employeeForm.getPassword());
		employee.setRole(employeeForm.getRole());
		
		try {
			employeeService.save(employee);
		} catch (DuplicateKeyException d) {
			String codeErrorMessage = messagesource.getMessage("E0006", null, Locale.JAPAN);
			model.addAttribute("error", codeErrorMessage);
			model.addAttribute("title", "新規追加");
			return "employee/emp_new";
		}
		
		String message = messagesource.getMessage("M0005", new String[]{"従業員の追加"}, Locale.JAPAN);
		redirectAttributes.addFlashAttribute("complete", message);
		return "redirect:/employee";
	}
	
	@GetMapping("/{code}")
	public String show(EmployeeForm employeeForm,
			@PathVariable String code,
			Model model,
			Authentication auth) {
		Optional<Employee> employeeOpt = employeeService.getEmployee(code);
		
		Optional<EmployeeForm> employeeFormOpt = employeeOpt.map(em -> makeEmployeeForm(em));
		
		//EmployeeがNullでなければ中身を取り出す
		if(employeeFormOpt.isPresent()) {
			employeeForm = employeeFormOpt.get();	
		}
		model.addAttribute("employeeForm", employeeForm);
		
		model.addAttribute("selectRole", employeeForm.getRole());
		model.addAttribute("title", "編集");
		
		// ログイン情報
		model.addAttribute("code", auth.getName());
		model.addAttribute("name", auth.getDetails());
		model.addAttribute("role", auth.getAuthorities().toString());
		
		return "employee/emp_edit";
	}
	
	@PostMapping("/edit/{oldCode}")
	public String update(@Validated EmployeeForm employeeForm,
			BindingResult result,
			Model model,
			@PathVariable String oldCode,
			RedirectAttributes redirectAttributes,
			Authentication auth) {
		
		// ログイン情報
		model.addAttribute("code", auth.getName());
		model.addAttribute("name", auth.getDetails());
		model.addAttribute("role", auth.getAuthorities().toString());
		
		if(result.hasErrors()) {
			model.addAttribute("title", "編集");
			model.addAttribute("employeeForm", employeeForm);
			model.addAttribute("oldCode", oldCode);
			return "employee/emp_reEdit";
		}
		Employee employee = makeEmployee(employeeForm);
		
		try {
		employeeService.update(employee, oldCode);
		} catch (DuplicateKeyException d) {
			String codeErrorMessage = messagesource.getMessage("E0006", null, Locale.JAPAN);
			redirectAttributes.addFlashAttribute("error", codeErrorMessage);
			redirectAttributes.addFlashAttribute("title", "編集");
			return "redirect:/employee/" + oldCode;
		}
		
		String message = messagesource.getMessage("M0005", new String[]{"従業員情報の編集"}, Locale.JAPAN);
		redirectAttributes.addFlashAttribute("complete", message);
		return "redirect:/employee/";
	}
	
	private Employee makeEmployee(EmployeeForm employeeForm) {
		Employee employee = new Employee();
		
		employee.setCode(employeeForm.getCode());
		employee.setName(employeeForm.getName());
		employee.setRole(employeeForm.getRole());
		employee.setPassword(null);
		return employee;
	}
	
	
	private EmployeeForm makeEmployeeForm(Employee employee) {
		EmployeeForm employeeForm = new EmployeeForm();
		
		employeeForm.setCode(employee.getCode());
		employeeForm.setName(employee.getName());
		employeeForm.setPassword("dummy");
		employeeForm.setPasswordConfirm("dummy");
		employeeForm.setRole(employee.getRole());
		employeeForm.setNewEmployee(false);
		
		return employeeForm;
	}

}
