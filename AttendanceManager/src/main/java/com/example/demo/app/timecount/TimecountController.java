package com.example.demo.app.timecount;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Timecount;
import com.example.demo.service.TimecountService;

@Controller
@RequestMapping("/timecount")
public class TimecountController {
	
	private final TimecountService timecountService;
	
	@Autowired
	public TimecountController (TimecountService timecountService) {
		this.timecountService = timecountService;
	}
	
	@GetMapping
	public String timecount(Model model,
			Authentication auth,
			TimecountForm timecountForm) {
		//フォームから年月取得
		int year = timecountForm.getYear();
		int month = timecountForm.getMonth();
		
		//初回アクセス時、現在年月を取得
		if(year == 0 || month == 0) {
			LocalDate date = (LocalDate.now());
			year = date.getYear();
			month = date.getMonthValue();
			timecountForm.setYear(year);
			timecountForm.setMonth(month);
		}
		
		//月が１ケタの場合、MMの形に変更する
		StringBuffer sb = new StringBuffer();
		if(month<10) {
			sb.append('0');
		}
		sb.append(month);
		
		//勤怠情報の取得
		List<Timecount> list = timecountService.getRecord(auth.getName(), year, sb.toString());
		
		//List<Map<String int>> dateList = null;
		
		model.addAttribute("list", list);
		model.addAttribute("timecountForm", timecountForm);
		model.addAttribute("title", "集計結果");
		// ログイン情報
		model.addAttribute("code", auth.getName());
		model.addAttribute("name", auth.getDetails());
		model.addAttribute("role", auth.getAuthorities());
		
		return "timecount/timecount";
		
	}

}
