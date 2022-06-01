package com.example.demo.app.login;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
			Model model,
			RedirectAttributes redirectAttributes,
			HttpSession session) {

		String id_message = messagesource.getMessage("E0008", new String[]{"ID", "10"}, Locale.JAPAN);
		String pass_message = messagesource.getMessage("E0008", new String[]{"パスワード", "20"}, Locale.JAPAN);
		
		// メッセージ表示
		String message = messagesource.getMessage("E0003", null, Locale.JAPAN);
		String error = (String) session.getAttribute("error_msg");
		if (error != "") {
			message = error;
		}
		
		// 文字数関連の場合
		if (message.matches(".*文字数.*")) {
			if (message.matches(".*ID.*")) {
				redirectAttributes.addFlashAttribute("code_val", message);
			} else if (message.matches(".*パスワード.*")) {
				redirectAttributes.addFlashAttribute("pass_val", message);
			} else {
				// 両方文字数を超えた場合
				redirectAttributes.addFlashAttribute("code_val", id_message);
				redirectAttributes.addFlashAttribute("pass_val", pass_message);
			}
		} else {
			redirectAttributes.addFlashAttribute("warning", message);
		}
		
		return "redirect:/login";
	}
	

}
