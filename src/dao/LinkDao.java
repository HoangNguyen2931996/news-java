package dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import entities.Link;

@Repository
public class LinkDao {
//	public LinkDao() {
//		System.out.println("Linkdao created");
//	}
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public List<Link> getItems(){
		return jdbcTemplate.query("SELECT l.id, l.link, r.id AS idResource, r.name AS nameResource, c.id AS idCat, c.name AS nameCat FROM links AS l INNER JOIN resources AS r ON l.id_resource = r.id " + 
				"INNER JOIN categories AS c ON l.id_category = c.id", new BeanPropertyRowMapper<Link>(Link.class));
	}
	
	public Link getItem(int idLink){
		return jdbcTemplate.queryForObject("SELECT l.id, l.link, r.id AS idResource, r.name AS nameResource, c.id AS idCat, c.name AS nameCat FROM links AS l INNER JOIN resources AS r ON l.id_resource = r.id\r\n" + 
				"INNER JOIN categories AS c ON l.id_category = c.id WHERE l.id = ?",new Object[] {idLink}, new BeanPropertyRowMapper<Link>(Link.class));
	}
	public int addItem(Link objLink) {
		return jdbcTemplate.update("INSERT INTO links(link, id_resource, id_category) VALUES(?, ?, ?)", new Object[] {objLink.getLink(), objLink.getIdResource(), objLink.getIdCat()});
	}
	public int editItem(Link objLink) {
		return jdbcTemplate.update("UPDATE links SET link = ?, id_resource =  ?, id_category = ? WHERE id = ?", new Object[] {objLink.getLink(), objLink.getIdResource(), objLink.getIdCat(), objLink.getId()});
	}

	public int delItem(int idLink) {
		return jdbcTemplate.update("DELETE FROM links WHERE id = ?", new Object[] {idLink});
	}
}
