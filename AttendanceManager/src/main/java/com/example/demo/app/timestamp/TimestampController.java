package com.example.demo.app.timestamp;

import java.util.Locale;

import javax.swing.JOptionPane;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Timestamp;
import com.example.demo.service.TimestampService;

@Controller
@RequestMapping("/AttendanceManagement")
public class TimestampController {
	@Autowired
    MessageSource messagesource;
	
	private final TimestampService timestampService;
	
	public TimestampController(TimestampService timestampService) {
		this.timestampService = timestampService;
	}

	@GetMapping("/timestamp")
	public String timestamp(Model model, Authentication auth) {
		model.addAttribute("title", "timestamp");
		String attendance_status = timestampService.getAttendance_status(auth.getName());
		// 条件分岐
		// 出勤前
		if (attendance_status == "attend") {
			model.addAttribute("attendance_status", false);
		} else if (attendance_status == "leave") {
			model.addAttribute("attendance_status", true);
		} else if (attendance_status == "complete") {
			model.addAttribute("attendance_status", "complete");
		}
		// ログイン情報
		model.addAttribute("code", auth.getName());
		model.addAttribute("name", auth.getDetails());
		model.addAttribute("role", auth.getAuthorities());
		

		return "timestamp/timestamp";
	}
	
	@PostMapping("/timestamp")
	public String timestamp(@Valid @ModelAttribute TimestampForm timestampForm,
	        BindingResult result,
	        Model model) {
		Timestamp timestamp = new Timestamp();
		timestamp.setUser_id(timestampForm.getUser_id());
		timestamp.setName(timestampForm.getName());
		timestamp.setAttendance_status(timestampForm.getAttendance_status());

        if (!result.hasErrors()) {
        	//TimestampFormのデータをtimestampsに格納
        	timestampService.Insert(timestamp);
        }else {
    		// メッセージ表示
    		String message = messagesource.getMessage("E0002", new String[]{"打刻処理"}, Locale.JAPAN);
    		JOptionPane.showMessageDialog(null, message);
        }
        return "redirect:/AttendanceManagement/timestamp";
	}
	

	
}