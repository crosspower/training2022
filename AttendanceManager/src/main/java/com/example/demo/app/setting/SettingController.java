package com.example.demo.app.setting;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.service.SettingNotFoundException;
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
		
		// タイトルの引き渡し
		model.addAttribute("title", "個人設定");

		// ログイン情報の引き渡し
		model.addAttribute("code", auth.getName());
		model.addAttribute("name", auth.getDetails());
		model.addAttribute("role", auth.getAuthorities().toString());

		return "setting/form";
	}

	// フォームの入力を確認し、登録
	@PostMapping
	public String registerPass(Authentication auth, @Validated SettingForm settingForm, BindingResult result,
			Model model, RedirectAttributes redirectAttributes) {

		// タイトルの引き渡し
		model.addAttribute("title", "個人設定");

		// ログイン情報の引き渡し
		model.addAttribute("code", auth.getName());
		model.addAttribute("name", auth.getDetails());
		model.addAttribute("role", auth.getAuthorities().toString());		
		
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

	// データ更新時エラー時、エラー画面を表示
	@ExceptionHandler(SettingNotFoundException.class)
	public String handleException(SettingNotFoundException e, Model model) {
		model.addAttribute("errorMessage", e);
		return "error/SettingErrorPage";
	}

}
