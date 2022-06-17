package com.example.demo.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Timecount;
import com.example.demo.entity.Workdate;

@Repository
public class TimecountDaoImpl implements TimecountDao {
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public TimecountDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Timecount> count(String code, int year, String month) {
		
		String strYear = String.valueOf(year);
		String yearMonth = strYear + "-" + month + "-%";
		//SQL文
		String sql ="SELECT attendance_time, leave_time, attendance_date FROM timestamps "
				+ "WHERE user_id = ? AND attendance_date LIKE ?";
		//抽出結果をListに詰める
		List<Map<String, Object>>resultList = jdbcTemplate.queryForList(sql, code, yearMonth);
		
		List<Workdate> records = new ArrayList<Workdate>();
		
		for (Map<String, Object> result:resultList) {
			
			Workdate workdate = new Workdate();
			workdate.setAttendanceTime((String)result.get("attendance_time"));
			workdate.setLeaveTime((String)result.get("leave_time"));
			workdate.setAttendanceDate((result.get("attendance_date")).toString());
			
			records.add(workdate);
		}
		
		long total_work_time = 0;
		int count_days = 0;
		
		Date attendanceTime = null;
		Date leaveTime = null;
		
		for (int i = 0; i < records.size(); i++) {
			//退勤時刻がnullの時はその行を削除する。
			if(records.get(i).getLeaveTime() == null) {
				//退勤時刻がnull、空白の場合
				records.remove(i);
			} else {
				try {
					SimpleDateFormat sdFormat = new SimpleDateFormat("HH:mm:ss.SSS");
					attendanceTime = sdFormat.parse(records.get(i).getAttendanceTime());
					leaveTime = sdFormat.parse(records.get(i).getLeaveTime());
					
				}catch(ParseException e) {
		            e.printStackTrace();
		        }
				//総労働時間の算出。
				//Duration time = Duration.between(attendanceTime, leaveTime);←これはlocaldatetime
				long time = (leaveTime.getTime() - attendanceTime.getTime())/ 1000 / 60 / 60;
				total_work_time = total_work_time + time;
				//System.out.println(total_work_time);
				//労働時間の算出回数を出勤日数にする。
				count_days = i + 1;
				//System.out.println(count_days);
			}
		}
		
		List<Timecount> list = new ArrayList<Timecount>();
		
		Timecount timecount = new Timecount();
		timecount.setWorkday(count_days);
		timecount.setWorktime(total_work_time);			
		
		//返却するListに詰める
		list.add(timecount);

		//返却
		return list;
	}

}
