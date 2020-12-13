package com.example.demo.trySpring;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StickyController{

	@Autowired
	private StickyService stickyService;

	@RequestMapping("/")
	public String index(Model model) {
		return "redirect:/list";
	}

	@GetMapping("/add")
	public String getAddSticky(Model model, @ModelAttribute("opt") int opt) {
		model.addAttribute("status", opt);
		return "add";
	}

	@GetMapping("/add/{opt}")
	public String getAdd(@PathVariable int opt, RedirectAttributes redirectAttributes) {
		redirectAttributes.addAttribute("opt", opt);
		return "redirect:/add";
	}

	@GetMapping("/list")
	public String getStickyList(Model model) {
	    model.addAttribute("list", this.getList(0));
	    model.addAttribute("list1", this.getList(1));
	    model.addAttribute("list2", this.getList(2));
		return "list";
	}

	private List<Sticky> getList(int opt) {
		List<Map<String, Object>> ret = stickyService.selectAll(opt);
		List<Sticky> list = new ArrayList<Sticky>();
		for(Map<String, Object> map: ret) {
	    	Sticky sticky = new Sticky();
	    	sticky.setId((int)map.get("id"));
	    	sticky.setTitle((String)map.get("title"));
	    	sticky.setSummary((String)map.get("summary"));
	        list.add(sticky);
	    }
		return list;
	}

	@PostMapping("/details")
	public String postDbRequest(@RequestParam("text3")String str, Model model) {
		int id = Integer.parseInt(str);
		Sticky sticky = stickyService.findOne(id);
		model.addAttribute("id", sticky.getId());
		model.addAttribute("title", sticky.getTitle());
		model.addAttribute("summary", sticky.getSummary());
		model.addAttribute("status", sticky.getStatus());

		return "details";
	}

	@GetMapping("/details/{id}")
	public String getDetails(@PathVariable int id, Model model) {
		Sticky sticky = stickyService.findOne(id);
		model.addAttribute("id", sticky.getId());
		model.addAttribute("title", sticky.getTitle());
		model.addAttribute("summary", sticky.getSummary());
		model.addAttribute("status", sticky.getStatus());

		return "details";
	}

	@PostMapping(value="/details/{id}",consumes=MediaType.APPLICATION_JSON_VALUE)
	public String postDetails(@PathVariable int id, @RequestBody JsonSticky jsonSticky, Model model) {
		Sticky sticky = new Sticky();
		sticky.setTitle(jsonSticky.getTitle());
		sticky.setSummary(jsonSticky.getSummary());
		sticky.setStatus(Integer.parseInt(jsonSticky.getStatus()));
		stickyService.update(id, sticky);

		return "redirect:/list";
	}

	@PostMapping(value="/insert",consumes=MediaType.APPLICATION_JSON_VALUE)
	public String insertSticky(@RequestBody JsonSticky jsonSticky, Model model) {
		Sticky sticky = new Sticky();
		sticky.setTitle(jsonSticky.getTitle());
		sticky.setSummary(jsonSticky.getSummary());
		sticky.setStatus(Integer.parseInt(jsonSticky.getStatus()));
		//ina todo
		stickyService.insertSticky(sticky);

		return "add";
	}

	@GetMapping(path = "/details/del/{id}")
    //@ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteCustomer(@PathVariable Integer id, Model model) {
		stickyService.delete(id);

		return "redirect:/list";
    }

	@GetMapping("/return")
	public String postReturn() {
		return "redirect:/list";
	}

}