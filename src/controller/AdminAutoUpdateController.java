package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import constant.Defines;

@Controller
@RequestMapping("/admin/autoupdate")
public class AdminAutoUpdateController {
	
	@Autowired
	private Defines defines;
	@ModelAttribute
	public void addCommonsObject(ModelMap modelMap){
		modelMap.addAttribute("defines", defines);
	}
	@RequestMapping("/add")
	public String add(){
		return "admin.autoupdate.add";
	}
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(@RequestParam("timeupdate") Long timeupdate, RedirectAttributes redirectAttributes){
		redirectAttributes.addFlashAttribute("msg", "Thêm thành Công");
		return "redirect:/admin/autoupdate/add";
	}
}
