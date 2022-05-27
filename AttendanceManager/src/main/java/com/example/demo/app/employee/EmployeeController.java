package com.example.demo.app.employee;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
	private final EmployeeService employeeService;
	
	@Autowired
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	@GetMapping
	public String index(Model model) {
		List<Employee> list = employeeService.getAll();
		
		model.addAttribute("empList", list);
		model.addAttribute("title", "社員一覧");
		
		return "employee/emp_index";
	}
	
	@GetMapping("/form")
	public String form(EmployeeForm employeeForm,Model model) {
		model.addAttribute("title", "新規追加");
		return "employee/emp_new";
	}
	
	@PostMapping("/form")
	public String formGoBack(EmployeeForm employeeForm,Model model) {
		model.addAttribute("title", "新規追加");
		return "employee/emp_new";
	}
	
	@PostMapping("/new")
	public String newEmp(@Validated EmployeeForm employeeForm,
			BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes) {
		if(result.hasErrors()) {
			model.addAttribute("title", "従業員追加");
			return "employee/emp_new";
		}
		Employee employee = new Employee();
		employee.setCode(employeeForm.getCode());
		employee.setName(employeeForm.getName());
		employee.setPassword(employeeForm.getPassword());
		employee.setRole(employeeForm.getRole());
		
		employeeService.save(employee);
		redirectAttributes.addFlashAttribute("complete", "従業員の追加が完了しました");
		return "redirect:/employee";
	}
	
	@GetMapping("/{code}")
	public String show(Employee employee,
			@PathVariable String code,
			Model model) {
		Optional<Employee> employeeOpt = employeeService.getEmployee(code);
		
		Optional<Employee> employeeInfoOpt = employeeOpt.map(em -> makeEmployeeInfo(em));
		
		//EmployeeがNullでなければ中身を取り出す
		if(employeeInfoOpt.isPresent()) {
			employee = employeeInfoOpt.get();
			
		}
		
		/*Map<String, String> roleList = new LinkedHashMap<String, String>();
		
		roleList.put(0, "一般");
		roleList.put(1, "管理者");	
		model.addAttribute("roleList", roleList);	*/
		
		model.addAttribute("employeeInfo", employee);
		
		model.addAttribute("selectRole", employee.getRole());
		model.addAttribute("title", "編集");
		return "employee/emp_edit";
	}
	
	@PostMapping("/edit/{oldCode}")
	public String update(EmployeeForm employeeForm,
			BindingResult result,
			Model model,
			@PathVariable String oldCode,
			RedirectAttributes redirectAttributes) {
		//データの格納
		Employee employee = makeEmployee(employeeForm);
		
		if(!result.hasErrors()) {
			
			//更新処理
			employeeService.update(employee, oldCode);
			redirectAttributes.addFlashAttribute("complete", "従業員情報の変更が完了しました");
			return "redirect:/employee/";
		} else {
			model.addAttribute("title", "編集");
			model.addAttribute("employeeInfo", employee);
			return "redirect:/employee/" + oldCode;
		}

	}
	
	private Employee makeEmployee(EmployeeForm employeeForm) {
		Employee employee = new Employee();
		
		employee.setCode(employeeForm.getCode());
		employee.setName(employeeForm.getName());
		employee.setRole(employeeForm.getRole());
		employee.setPassword(null);
		return employee;
	}
	
	
	private Employee makeEmployeeInfo(Employee employee) {
		Employee employeeInfo = new Employee();
		
		employeeInfo.setCode(employee.getCode());
		employeeInfo.setName(employee.getName());
		employeeInfo.setPassword(employee.getPassword());
		employeeInfo.setRole(employee.getRole());
		
		return employeeInfo;
	}

}
