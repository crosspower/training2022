package com.example.demo.app.login;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
    MessageSource messagesource;
	
	/*
	 * ログイン画面に移動
	 */
	@GetMapping
	public String Login() {
		return "login/index";
	}
	
	/*
	 * ログイン成功時
	 */
	@GetMapping("/success")
	public String LoginSucecess(
			Model model,
			Authentication auth,
			RedirectAttributes redirectAttributes) {
		
		// 成功メッセージを追加
		String message = messagesource.getMessage("M0005", new String[]{"ログイン"}, Locale.JAPAN);
		redirectAttributes.addFlashAttribute("success", message);
		
		//　ログイン情報の引き渡し
		model.addAttribute("code", auth.getName());
		model.addAttribute("name", auth.getDetails());
		model.addAttribute("role", auth.getAuthorities());
		
		// 勤怠打刻画面に遷移
		return "redirect:/AttendanceManagement/timestamp";
	}
	
	/*
	 * ログイン失敗時
	 */
	@GetMapping("/error")
	public String LoginError(
			RedirectAttributes redirectAttributes) {
		
		// メッセージ表示
		String message = messagesource.getMessage("E0003", null, Locale.JAPAN);
		redirectAttributes.addFlashAttribute("warning", message);
		return "redirect:/login";
	}
	

}
