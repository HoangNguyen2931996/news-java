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
import dao.CatDao;
import entities.Category;

@Controller
@RequestMapping("admin/cat")
public class AdminCatController {
	@Autowired
	private Defines defines;
	@Autowired
	private CatDao catDao;
	@ModelAttribute
	public void addCommonsObject(ModelMap modelMap){
		modelMap.addAttribute("defines", defines);
	}
	@RequestMapping(value="", method = RequestMethod.GET)
	public String index(ModelMap modelMap){
		modelMap.addAttribute("alCat", catDao.getCats());
		return "admin.cat.index";
	}
	@RequestMapping("/add")
	public String add(){
		return "admin.cat.add";
	}
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(@ModelAttribute("objCat") Category objCat, RedirectAttributes redirectAttributes){
		if(catDao.addItem(objCat)>0){
			redirectAttributes.addFlashAttribute("msg", "Thêm thành Công");
		} else{
			redirectAttributes.addFlashAttribute("msg", "Xảy ra lỗi trong quá trình xử lý");
		}
		return "redirect:/admin/cat";
	}
	@RequestMapping("/edit/{idCat}")
	public String edit(@PathVariable("idCat") int idCat, ModelMap modelMap){
		modelMap.addAttribute("objCat", catDao.getItem(idCat));
		return "admin.cat.edit";
	}
	@RequestMapping(value="/edit/{idCat}", method=RequestMethod.POST)
	public String edit(@ModelAttribute("objCat") Category objCat,@PathVariable("idCat") int idCat, RedirectAttributes redirectAttributes){
		objCat.setId(idCat);
		if(catDao.edit(objCat) > 0){
			redirectAttributes.addFlashAttribute("msg", "Sửa thành công");
		} else{
			redirectAttributes.addFlashAttribute("msg", "Xảy ra lỗi trong quá trình xử lý");
		}
		return "redirect:/admin/cat";
	}
	
	@RequestMapping("/del/{idCat}")
	public String del(@PathVariable("idCat") int idCat, RedirectAttributes redirectAttributes){
		if(catDao.del(idCat) > 0){
			redirectAttributes.addFlashAttribute("msg", "Xóa thành công");
		} else{
			redirectAttributes.addFlashAttribute("msg", "Xảy ra lỗi trong quá trình xử lý");
		}
		return "redirect:/admin/cat";
	}
}
