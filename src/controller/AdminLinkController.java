package controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import constant.Defines;
import dao.CatDao;
import dao.LinkDao;
import dao.NewsDao;
import dao.ResourceDao;
import entities.Link;
import entities.News;
import services.AndroidPushNotificationsService;
import utils.UtilString;

@Controller
@RequestMapping("admin/link")
public class AdminLinkController {
	@Autowired
	private Defines defines;
	@Autowired
	private LinkDao linkDao;
	@Autowired
	private ResourceDao resourceDao;
	@Autowired
	private NewsDao newsDao;
	@Autowired
	private UtilString utilString;
	@Autowired
	private CatDao catDao;
	@Autowired
	AndroidPushNotificationsService androidPushNotificationsService;
	@ModelAttribute
	public void addCommonsObject(ModelMap modelMap){
		modelMap.addAttribute("defines", defines);
	}
	@RequestMapping(value="", method = RequestMethod.GET)
	public String index(ModelMap modelMap){
		modelMap.addAttribute("alLink", linkDao.getItems());
		return "admin.link.index";
	}
	@RequestMapping("/add")
	public String add(ModelMap modelMap){
		modelMap.addAttribute("alResource", resourceDao.getResources());
		modelMap.addAttribute("alCat", catDao.getCats());
		return "admin.link.add";
	}
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(@ModelAttribute("objLink") Link objLink, RedirectAttributes redirectAttributes){
		if(linkDao.addItem(objLink)>0){
			redirectAttributes.addFlashAttribute("msg", "Thêm thành Công");
		} else{
			redirectAttributes.addFlashAttribute("msg", "Xảy ra lỗi trong quá trình xử lý");
		}
		return "redirect:/admin/link";
	}
	@RequestMapping("/edit/{idLink}")
	public String edit(@PathVariable("idLink") int idLink, ModelMap modelMap){
		modelMap.addAttribute("alResource", resourceDao.getResources());
		modelMap.addAttribute("alCat", catDao.getCats());
		modelMap.addAttribute("objLink", linkDao.getItem(idLink));
		return "admin.link.edit";
	}
	@RequestMapping(value="/edit/{idLink}", method=RequestMethod.POST)
	public String edit(@ModelAttribute("objLink") Link objLink,@PathVariable("idLink") int idLink, RedirectAttributes redirectAttributes){
		objLink.setId(idLink);
		if(linkDao.editItem(objLink) > 0){
			redirectAttributes.addFlashAttribute("msg", "Sửa thành công");
		} else{
			redirectAttributes.addFlashAttribute("msg", "Xảy ra lỗi trong quá trình xử lý");
		}
		return "redirect:/admin/link";
	}
	
	@RequestMapping("/del/{idLink}")
	public String del(@PathVariable("idLink") int idLink, RedirectAttributes redirectAttributes){
		if(linkDao.delItem(idLink) > 0){
			newsDao.delItemLink(idLink);
			redirectAttributes.addFlashAttribute("msg", "Xóa thành công");
		} else{
			redirectAttributes.addFlashAttribute("msg", "Xảy ra lỗi trong quá trình xử lý");
		}
		return "redirect:/admin/link";
	}
	@RequestMapping(value="/load", method=RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public @ResponseBody String loadMoreHL(@RequestParam("idLink") int idLink){
		Link objLink = linkDao.getItem(idLink);
		int count = 0;
		try {
			Document document = Jsoup.connect(objLink.getLink()).get();
			for(Element element:document.select("item")) {
				String description = element.select("description").text();
				String title = element.select("title").text();
				String link = element.select("link").text();
				String UrlPicture = Jsoup.parse(description).select("img").first().absUrl("src");
				String preview = utilString.getDescription(description);
				Timestamp dateCreated = utilString.convertTimstamp(element.select("pubDate").text());
				if(!"".equals(preview)) {
					preview = Jsoup.parse(preview).text();
					News objNews = new News(0, title, preview, link, UrlPicture, dateCreated, objLink.getIdCat(), "", objLink.getIdResource(), "");
					if(newsDao.check(objNews)) {
						if(newsDao.addItem(objNews)>0) {
							count++;
						}else {
							break;
						}
					}
				}
			}
			if(count > 0) {
				send(count);
			}
		
		} catch (IOException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
		return "";
	}
	public ResponseEntity<String> send(int count) throws JsonIOException {
		JsonObject body = new JsonObject();
		body.addProperty("to", "/topics/news");
		body.addProperty("priority", "high");
 
		JsonObject notification = new JsonObject();
		notification.addProperty("title", "TinHangNgay thông báo");
		notification.addProperty("body", count);
		
		JsonObject data = new JsonObject();
		data.addProperty("count", count);
 
		body.add("notification", notification);
		body.add("data", data);
 
/**
		{
		   "notification": {
		      "title": "TinHangNgay thông báo",
		      "body": "Happy Message!"
		   },
		   "data": {
		      "Key-1": "JSA Data 1",
		      "Key-2": "JSA Data 2"
		   },
		   "to": "/topics/news",
		   "priority": "high"
		}
*/
 
		HttpEntity<String> request = new HttpEntity<>(body.toString());
		System.out.println(body.toString());
		CompletableFuture<String> pushNotification = androidPushNotificationsService.send(request);
		CompletableFuture.allOf(pushNotification).join();
 
		try {
			String firebaseResponse = pushNotification.get();
			return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
 
		return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
	}
}
