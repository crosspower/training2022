package com.example.demo.app.information;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Information;
import com.example.demo.service.InformationService;

@Controller
@RequestMapping("/info")
public class InformationController {
	private final InformationService informationService;

	@Autowired
	public InformationController(InformationService informationService) {
		this.informationService = informationService;
	}

	@GetMapping
	public String info(Model model, @ModelAttribute("complete") String complete, InformationForm informationForm) {
		List<Information> list = informationService.getAll();

		model.addAttribute("informationList", list);
		model.addAttribute("title", "日次勤怠画面");

		return "information/info";
	}

	@GetMapping("/{id}")
	public String showUpdate(InformationForm informationForm, @PathVariable int id, Model model) {
		// Informationを取得(Optionalでラップ)
		Optional<Information> informationOpt = informationService.getInformation(id);
		Optional<InformationForm> informationFormOpt = informationOpt.map(t -> makeInformationForm(t));

		// Informationがnullでなければ中身を取り出す
		if (informationFormOpt.isPresent()) {
			informationForm = informationFormOpt.get();
		}
		model.addAttribute("informationForm", informationForm);
		List<Information> list = informationService.getAll();
		model.addAttribute("informationList", list);
		model.addAttribute("title", "日次勤怠画面");
		return "information/info";
	}

	@GetMapping("/info_edit/{id}")
	public String info_edit(InformationForm informationForm, Model model, @PathVariable int id) {
		model.addAttribute("title", "日次勤怠編集画面");
		model.addAttribute("id", id); // idを渡す

		return "information/info_edit";
	}

	@PostMapping("/{id}")
	public String info_post(@Validated InformationForm informationForm, BindingResult result, @RequestParam int id,
			Model model, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			model.addAttribute("title", "日次勤怠編集画面");
			model.addAttribute("informationForm", informationForm);
			model.addAttribute("id", id);
			return "information/info_edit";
		}
		Information information = new Information();

		information.setId(id);
		information.setAttendance_time(informationForm.getAttendance_time());
		information.setLeave_time(informationForm.getLeave_time());
		information.setAttendance_date(informationForm.getAttendance_date());
		
		
		informationService.save(information);

		redirectAttributes.addFlashAttribute("complete", "編集が完了しました");
		model.addAttribute("title", "日次勤怠画面");

		return "redirect:/info";

	}

	private InformationForm makeInformationForm(Information information) {
		InformationForm informationForm = new InformationForm();

		informationForm.setId(information.getId());
		informationForm.setAttendance_time(information.getAttendance_time());
		informationForm.setLeave_time(information.getLeave_time());
		informationForm.setAttendance_date(information.getAttendance_date());

		return informationForm;
	}
}