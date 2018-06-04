package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import constant.Defines;
import dao.ResourceDao;
import entities.Resource;

@Controller
@RequestMapping("admin/resource")
public class AdminResourceController {
	@Autowired
	private Defines defines;
	@Autowired
	private ResourceDao resourceDao;
	@ModelAttribute
	public void addCommonsObject(ModelMap modelMap){
		modelMap.addAttribute("defines", defines);
	}
	@RequestMapping(value="", method = RequestMethod.GET)
	public String index(ModelMap modelMap){
		modelMap.addAttribute("alResource", resourceDao.getResources());
		return "admin.resource.index";
	}
	@RequestMapping("/add")
	public String add(){
		return "admin.resource.add";
	}
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(@ModelAttribute("objResource") Resource objResource, RedirectAttributes redirectAttributes){
		if(resourceDao.addItem(objResource)>0){
			redirectAttributes.addFlashAttribute("msg", "Thêm thành Công");
		} else{
			redirectAttributes.addFlashAttribute("msg", "Xảy ra lỗi trong quá trình xử lý");
		}
		return "redirect:/admin/resource";
	}
	@RequestMapping("/edit/{idResource}")
	public String edit(@PathVariable("idResource") int idResource, ModelMap modelMap){
		modelMap.addAttribute("objResource", resourceDao.getItem(idResource));
		return "admin.resource.edit";
	}
	@RequestMapping(value="/edit/{idResource}", method=RequestMethod.POST)
	public String edit(@ModelAttribute("objResource") Resource objResource,@PathVariable("idResource") int idResource, RedirectAttributes redirectAttributes){
		objResource.setId(idResource);
		if(resourceDao.edit(objResource) > 0){
			redirectAttributes.addFlashAttribute("msg", "Sửa thành công");
		} else{
			redirectAttributes.addFlashAttribute("msg", "Xảy ra lỗi trong quá trình xử lý");
		}
		return "redirect:/admin/resource";
	}
	
	@RequestMapping("/del/{idResource}")
	public String del(@PathVariable("idResource") int idResource, RedirectAttributes redirectAttributes){
		if(resourceDao.del(idResource) > 0){
			redirectAttributes.addFlashAttribute("msg", "Xóa thành công");
		} else{
			redirectAttributes.addFlashAttribute("msg", "Xảy ra lỗi trong quá trình xử lý");
		}
		return "redirect:/admin/resource";
	}
}
