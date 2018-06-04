package dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import entities.Category;

@Repository
public class CatDao {
 
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Category> getCats(){
		return jdbcTemplate.query("SELECT * FROM categories", new BeanPropertyRowMapper<Category>(Category.class));
	}

	public int addItem(Category objCat) {
		return jdbcTemplate.update("INSERT INTO categories(name) VALUES(?)", new Object[] {objCat.getName()});
	}

	public Category getItem(int idCat) {
		return jdbcTemplate.queryForObject("SELECT * FROM categories WHERE id = ?", new Object[] {idCat}, new BeanPropertyRowMapper<Category>(Category.class));
	}

	public int edit(Category objCat) {
		return jdbcTemplate.update("UPDATE categories SET name = ? WHERE id = ?", new Object[] {objCat.getName(), objCat.getId()});
	}

	public int del(int idCat) {
		return jdbcTemplate.update("DELETE FROM categories WHERE id = ?", new Object[] {idCat});
	}
}
