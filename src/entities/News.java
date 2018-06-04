package entities;

import java.sql.Timestamp;

public class News {

	private int id;
	private String name;
	private String preview;
	private String link;
	private String picture;
	private Timestamp date_created;
	private int id_category;
	private String nameCat;
	private int id_resource;
	private String nameResource;
	public News(int id, String name, String preview, String link, String picture, Timestamp date_created,
			int id_category, String nameCat, int id_resource, String nameResource) {
		super();
		this.id = id;
		this.name = name;
		this.preview = preview;
		this.link = link;
		this.picture = picture;
		this.date_created = date_created;
		this.id_category = id_category;
		this.nameCat = nameCat;
		this.id_resource = id_resource;
		this.nameResource = nameResource;
	}
	public News() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPreview() {
		return preview;
	}
	public void setPreview(String preview) {
		this.preview = preview;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public Timestamp getDate_created() {
		return date_created;
	}
	public void setDate_created(Timestamp date_created) {
		this.date_created = date_created;
	}
	public int getId_category() {
		return id_category;
	}
	public void setId_category(int id_category) {
		this.id_category = id_category;
	}
	public String getNameCat() {
		return nameCat;
	}
	public void setNameCat(String nameCat) {
		this.nameCat = nameCat;
	}
	public int getId_resource() {
		return id_resource;
	}
	public void setId_resource(int id_resource) {
		this.id_resource = id_resource;
	}
	public String getNameResource() {
		return nameResource;
	}
	public void setNameResource(String nameResource) {
		this.nameResource = nameResource;
	}
}
