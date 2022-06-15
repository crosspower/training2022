package com.example.demo.app.setting;

import java.time.LocalDateTime;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.app.login.LoginForm;
import com.example.demo.service.SettingService;

@Controller
@RequestMapping("/setting")
public class SettingController {

	private final SettingService settingService;

	@Autowired
	MessageSource messagesource;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	public SettingController(SettingService settingService) {
		this.settingService = settingService;
	}

	// 個人設定画面を表示
	@GetMapping
	public String setting(Authentication auth, SettingForm settingForm, Model model,
			@ModelAttribute("message") String message) {

		String code = auth.getName(); // 社員番号の取得

		model.addAttribute("code", code);
		model.addAttribute("title", "個人設定");

		return "setting/form";
	}

	// フォームの入力を確認し、登録
	@PostMapping
	public String registerPass(Authentication auth, @Validated SettingForm settingForm, BindingResult result,
			Model model, RedirectAttributes redirectAttributes) {

		model.addAttribute("title", "個人設定");

		String currentPass = settingService.getPass(auth.getName());
		String formCurrentPass = settingForm.getCurrentPass();
		String formNewPass = settingForm.getNewPass();
		String formConfirmPass = settingForm.getConfirmPass();

		if (result.hasErrors()) { // 文字数の確認
			return "setting/form";
		} else if (!(passwordEncoder.matches(formCurrentPass, currentPass))) { // 現在のパスワードの確認
			redirectAttributes.addFlashAttribute("error",
					messagesource.getMessage("E0010", new String[] {}, Locale.getDefault()));
			return "redirect:/setting";
		} else if (!(formNewPass.equals(formConfirmPass))) { // 新しいパスワードの確認
			redirectAttributes.addFlashAttribute("error",
					messagesource.getMessage("E0007", new String[] {}, Locale.getDefault()));
			return "redirect:/setting";
		}

		// 新しいパスワードを登録
		settingService.updatePass(settingForm);
		redirectAttributes.addFlashAttribute("complete",
				messagesource.getMessage("M0003", new String[] { "パスワード" }, Locale.getDefault()));
		return "redirect:/setting";
	}

}
