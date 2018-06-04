package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import constant.Defines;
import dao.NewsDao;
import utils.UtilString;

@Controller
@RequestMapping("admin")
public class AdminNewsController {

	@Autowired
	private Defines defines;
	@Autowired
	private NewsDao newsDao;
	@Autowired
	private UtilString utilString;
	@ModelAttribute
	public void addCommonsObject(ModelMap modelMap){
		modelMap.addAttribute("defines", defines);
		modelMap.addAttribute("utilString", utilString);
	}
	@RequestMapping("")
	public String index(@RequestParam(value="page", defaultValue="1") int currentPage, ModelMap modelMap, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("page", currentPage);
		int row_count = defines.getROW_COUNT_ADMIN();
		int totalPage = (int)Math.ceil((float)newsDao.getSumAll()/row_count);
		int numLink = (int)Math.floor((float) defines.getPAGE_NUM_ADMIN()/2);
		int offset = (currentPage - 1)*row_count;
		modelMap.addAttribute("alNews", newsDao.getItems(offset, row_count));
		modelMap.addAttribute("sumPage", totalPage);
		modelMap.addAttribute("currentPage", currentPage);
		modelMap.addAttribute("numLink", numLink);
		modelMap.addAttribute("pageNum", defines.getPAGE_NUM_ADMIN());
		return "admin.news.index";
	}
	@RequestMapping("/del/{idNews}")
	public String del(@PathVariable("idNews") int idNews, RedirectAttributes redirectAttributes, HttpServletRequest request){
		HttpSession session = request.getSession();
		int page = (int) session.getAttribute("page");
		if(newsDao.delItem(idNews) > 0){
			redirectAttributes.addFlashAttribute("msg", "Xóa thành công");
		} else{
			redirectAttributes.addFlashAttribute("msg", "Xảy ra lỗi trong quá trình xử lý");
		}
		return "redirect:/admin?page="+page;
	}
	@RequestMapping("/del")
	public String del(RedirectAttributes redirectAttributes, HttpServletRequest request){
		HttpSession session = request.getSession();
		int page = (int) session.getAttribute("page");
		String idItems[] = request.getParameterValues("idNews");
		for(int i = 0; i<idItems.length; i++) {
			int idNews = Integer.parseInt(idItems[i]);
			if(newsDao.delItem(idNews) > 0){
				
			} else{
				redirectAttributes.addFlashAttribute("msg", "Xảy ra lỗi trong quá trình xử lý");
			}
		}
		redirectAttributes.addFlashAttribute("msg", "Xóa thành công");
		return "redirect:/admin?page="+page;
	}
}
