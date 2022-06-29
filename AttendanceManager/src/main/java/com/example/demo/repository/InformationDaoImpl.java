package com.example.demo.repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Information;

@Repository
public class InformationDaoImpl implements InformationDao {
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public InformationDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Information> getAll(String strDate) {

		String sql = "SELECT id,user_id,name,attendance_time, leave_time,attendance_date FROM timestamps "
				+ "WHERE attendance_date LIKE ?";

		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, strDate);
		List<Information> list = new ArrayList<Information>();

		for (Map<String, Object> result : resultList) {
			Information information = new Information();
			information.setId((int) result.get("id"));
			information.setUser_id((String) result.get("user_id"));
			information.setName((String) result.get("name"));
			information.setAttendance_time((String) result.get("attendance_time"));
			information.setLeave_time((String) result.get("leave_time"));
			// 日付の形式を整えてString型に代入
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd(E)");
			String str = sdf.format(result.get("attendance_date"));
			information.setAttendance_date((String) str);

			list.add(information);
		}
		return list;
	}
/*
	
*/
	@Override
	public void update(Information information) {
		jdbcTemplate.update("UPDATE timestamps SET attendance_time = ? , leave_time = ? WHERE id = ?",
				information.getAttendance_time(), information.getLeave_time(), information.getId());
	}

	
	@Override
	public List<Information> Date(int id) {
				
				String sql = "SELECT id,attendance_date FROM timestamps "
						+ "WHERE id = ?";

				List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql,id);
				List<Information> list = new ArrayList<Information>();

				for (Map<String, Object> result : resultList) {
					Information information = new Information();
					information.setId((int) result.get("id"));
					// 日付の形式を整えてString型に代入
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd(E)");
					String str = sdf.format(result.get("attendance_date"));
					information.setAttendance_date((String) str);

					list.add(information);
				}
				return list;
	}
}