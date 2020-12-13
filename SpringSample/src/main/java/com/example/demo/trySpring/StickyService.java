package com.example.demo.trySpring;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StickyService {
	@Autowired
	private StickyRepository stickyRepository;

	public Sticky findOne(int id) {
		Map<String, Object> map = stickyRepository.findOne(id)
				;
		int sticky_id = (Integer)map.get("id");
		String title = (String)map.get("title");
		String summary = (String)map.get("summary");
		int status = (Integer)map.get("status");

		Sticky sticky = new Sticky();
		sticky.setId(sticky_id);
		sticky.setTitle(title);
		sticky.setSummary(summary);
		sticky.setStatus(status);

		return sticky;
	}

	public List<Map<String, Object>> selectAll(int opt) {
		List<Map<String, Object>> ret = stickyRepository.selectAll(opt);

		return ret;
	}

	public void insertSticky(Sticky sticky) {
		stickyRepository.insertSticky(sticky);
	}

	public void update(int id, Sticky sticky) {
		stickyRepository.update(id, sticky);
	}

	public void delete(int id) {
		stickyRepository.delete(id);
	}

}