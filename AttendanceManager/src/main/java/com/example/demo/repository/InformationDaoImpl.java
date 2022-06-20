package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
	public List<Information> getAll() {
		String sql = "SELECT id,user_id,name,attendance_time, leave_time, attendance_date FROM timestamps";

		List<Map<String,Object>> resultList = jdbcTemplate.queryForList(sql);
		List<Information> list = new ArrayList<Information>();

		for(Map<String,Object> result : resultList) {
			Information information = new Information();
				information.setId((int)result.get("id"));
				information.setUser_id((String)result.get("user_id"));
				information.setName((String)result.get("name"));
				information.setAttendance_time((String)result.get("attendance_time"));
				information.setLeave_time((String)result.get("leave_time"));
				information.setAttendance_date((result.get("attendance_date")).toString());

				list.add(information);
		}
		return list;
	}

	@Override
	public Optional<Information> findById(int id) {
		String sql = "SELECT id,user_id,name,attendance_time, leave_time, attendance_date FROM timestamps WHERE id = ?";

		Map<String,Object> result = jdbcTemplate.queryForMap(sql,id);
		Information information = new Information();
			information.setId((int)result.get("id"));
			information.setUser_id((String)result.get("user_id"));
			information.setName((String)result.get("name"));
			information.setAttendance_time((String)result.get("attendance_time"));
			information.setLeave_time((String)result.get("leave_time"));
			information.setAttendance_date((result.get("attendance_date")).toString());

			Optional<Information> informationOpt = Optional.ofNullable(information);

		return informationOpt;
	}

	@Override
	public void updateInformation(Information information) {
		jdbcTemplate.update("UPDATE  attendance_time = ?, timestamps SET leave_time = ? WHERE id = ?",
				information.getAttendance_time(),information.getLeave_time(),information.getId());
	}
}