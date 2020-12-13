package com.example.demo.trySpring;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StickyRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Map<String, Object> findOne(int id) {

		String query = "SELECT "
				+ "id,"
				+ "title,"
				+ "summary,"
				+ "status "
				+ " FROM sticky "
				+ "WHERE id = ?";

		Map<String, Object> sticky = jdbcTemplate.queryForMap(query, id);

		return sticky;
	}

	public List<Map<String, Object>> selectAll(int opt) {

		String query = "SELECT "
				+ "id,"
				+ "title,"
				+ "summary, "
				+ "status "
				+ "FROM sticky "
				+ "WHERE status = ? "
				+ "ORDER BY id ";

		List<Map<String, Object>> ret = jdbcTemplate.queryForList(query, opt);
		for (Map<String, Object> map : ret) {
			System.out.println(map.get("id").toString() + "-" + map.get("title").toString());
		}

		return ret;
	}

	public void insertSticky(Sticky sticky) {
		jdbcTemplate.update("INSERT INTO sticky(title,summary,status) Values(?,?,?)",
				sticky.getTitle(),sticky.getSummary(),sticky.getStatus());
	}

	public void update(int id, Sticky sticky) {
		jdbcTemplate.update("UPDATE sticky SET title = ?, summary = ?, status = ? WHERE id = ?",
				sticky.getTitle(), sticky.getSummary(), sticky.getStatus(), id);
	}

	public void delete(int id) {
		jdbcTemplate.update("DELETE FROM sticky WHERE id = ?", id);
	}

}