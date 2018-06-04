package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import constant.Defines;
import dao.CatDao;
import dao.NewsDao;
import dao.ResourceDao;
import entities.Category;
import entities.News;
import entities.Resource;

@Controller
@RequestMapping("rest")
public class MainRestController {

	@Autowired
	private ResourceDao resourceDao;
	@Autowired
	private CatDao catDao;
	@Autowired
	private NewsDao newsDao;
	@Autowired
	private Defines defines;
	@RequestMapping(value="getcat", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public List<Category> getCat() {
		List<Category> listCat = catDao.getCats(); 
		return listCat;
	}
	@RequestMapping(value="getresource", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public List<Resource> getResource() {
		List<Resource> listResource = resourceDao.getResources(); 
		return listResource;
	}
	
	@RequestMapping(value="getnews", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public List<News> getNews(@RequestParam(value="page", defaultValue="1") int page) {
		int row_count = defines.getROW_COUNT_ADMIN();
		int offset = (page - 1)*row_count;
		List<News> listNews = newsDao.getItems(offset, row_count);
		return listNews;
	}
	@RequestMapping(value="getnewsslide", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public List<News> getNewsSlide() {
		List<News> listNews = newsDao.getItemsSlide();
		return listNews;
	}
	@RequestMapping(value="getnewsbyidresource/{idResource}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public List<News> getNewsByIdResource(@PathVariable("idResource") int id, @RequestParam(value="page", defaultValue="1") int page){
		int row_count = defines.getROW_COUNT_ADMIN();
		int offset = (page - 1)*row_count;
		List<News> listNews = newsDao.getNewsByIdResource(id, offset, row_count);
		return listNews;
	}
	
	@RequestMapping(value="getnewsbynamecat/{nameCat}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public List<News> getNewsByNameCat(@PathVariable("nameCat") String nameCat, @RequestParam(value="page", defaultValue="1") int page){
		int row_count = defines.getROW_COUNT_ADMIN();
		int offset = (page - 1)*row_count;
		List<News> listNews = newsDao.getNewsByNameCat(nameCat, offset, row_count);
		return listNews;
	}
	@RequestMapping(value="getnewslimit/{limit}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseBody
	public List<News> getNewsLimit(@PathVariable("limit") int limit){
		List<News> listNews = newsDao.getNewsLimit(limit);
		return listNews;
	}
		 
}
