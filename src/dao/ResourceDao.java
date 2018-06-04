package dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import entities.Resource;

@Repository
public class ResourceDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Resource> getResources() {
		return jdbcTemplate.query("SELECT * FROM resources", new BeanPropertyRowMapper<Resource>(Resource.class));
	}

	public int addItem(Resource objResource) {
		return jdbcTemplate.update("INSERT INTO resources(name, link) VALUES(?, ?)", new Object[] {objResource.getName(), objResource.getLink()});
	}

	public Resource getItem(int idResource) {
		return jdbcTemplate.queryForObject("SELECT * FROM resources WHERE id = ?",new Object[] {idResource}, new BeanPropertyRowMapper<Resource>(Resource.class));
	}

	public int edit(Resource objResource) {
		return jdbcTemplate.update("UPDATE resources SET name = ?, link = ? WHERE id = ?", new Object[] {objResource.getName(), objResource.getLink(), objResource.getId()});
	}

	public int del(int idResource) {
		return jdbcTemplate.update("DELETE FROM resources WHERE id = ?", new Object[] {idResource});
	}
	
}
