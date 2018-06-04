package dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import entities.News;

@Repository
public class NewsDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public List<News> getNewsByIdResource(int id, int offset, int row_count) {
		return jdbcTemplate.query("SELECT n.id, n.name, n.preview, n.link, n.picture, n.date_created, c.id As idCat,c.name AS nameCat, r.id AS idResource, r.name AS nameResource FROM news AS n INNER JOIN resources AS r ON n.id_resource = r.id INNER JOIN categories AS c on n.id_category = c.id WHERE id_resource = ? ORDER BY n.id DESC LIMIT ?, ?",
				new Object[] { id, offset, row_count }, new BeanPropertyRowMapper<News>(News.class));
	}

	public List<News> getItems(int offset, int row_count) {
		return jdbcTemplate.query(
				"SELECT n.id, n.name, n.preview, n.link, n.picture, n.date_created, c.id As idCat,c.name AS nameCat, r.id AS idResource, r.name AS nameResource FROM news AS n INNER JOIN resources AS r ON n.id_resource = r.id INNER JOIN categories AS c on n.id_category = c.id ORDER BY n.id DESC LIMIT ?, ?",
				new Object[] { offset, row_count }, new BeanPropertyRowMapper<News>(News.class));
	}
	public List<News> getItemsSlide() {
		return jdbcTemplate.query(
				"SELECT n.id, n.name, n.preview, n.link, n.picture, n.date_created, c.id As idCat,c.name AS nameCat, r.id AS idResource, r.name AS nameResource FROM news AS n INNER JOIN resources AS r ON n.id_resource = r.id INNER JOIN categories AS c on n.id_category = c.id ORDER BY RAND() LIMIT 3",
				 new BeanPropertyRowMapper<News>(News.class));
	}
	public int  getSumAll() {
		return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM news AS n INNER JOIN resources AS r ON n.id_resource = r.id INNER JOIN categories AS c on n.id_category = c.id", Integer.class);
	}

	public int delItem(int idNews) {
		return jdbcTemplate.update("DELETE FROM news WHERE id = ?", new Object[] {idNews});
	}
	public int delItemLink(int idLink) {
		return jdbcTemplate.update("DELETE FROM news WHERE id_resource = ?", new Object[] {idLink});
	}

	public boolean check(News objNews) {
		try {
			jdbcTemplate.queryForObject("SELECT * FROM news WHERE link = ?", new Object[] {objNews.getLink()}, new BeanPropertyRowMapper<News>(News.class));
			return false;
		} catch(Exception e) {
			return true;
		}
	}
	public int addItem(News objNews) {
		return jdbcTemplate.update("INSERT INTO news(name, preview, link, picture, date_created, id_category, id_resource) VALUES(?, ?, ?, ?, ?, ?, ?)", new Object[] {objNews.getName(), objNews.getPreview(), objNews.getLink(), objNews.getPicture(), objNews.getDate_created(), objNews.getId_category(), objNews.getId_resource()});
	}

	public List<News> getNewsByNameCat(String nameCat, int offset, int row_count) {
		return jdbcTemplate.query("SELECT n.id, n.name, n.preview, n.link, n.picture, n.date_created, c.id As idCat,c.name AS nameCat, r.id AS idResource, r.name AS nameResource FROM news AS n INNER JOIN resources AS r ON n.id_resource = r.id INNER JOIN categories AS c on n.id_category = c.id WHERE c.name = ? ORDER BY n.id DESC LIMIT ?, ?",
				new Object[] { nameCat, offset, row_count }, new BeanPropertyRowMapper<News>(News.class));
	}

	public List<News> getNewsLimit(int limit) {
		return jdbcTemplate.query(
				"SELECT n.id, n.name, n.preview, n.link, n.picture, n.date_created, c.id As idCat,c.name AS nameCat, r.id AS idResource, r.name AS nameResource FROM news AS n INNER JOIN resources AS r ON n.id_resource = r.id INNER JOIN categories AS c on n.id_category = c.id ORDER BY n.id DESC LIMIT ?",
				new Object[] { limit }, new BeanPropertyRowMapper<News>(News.class));
	}
}
