package entities;

public class Link {

	private int id;
	private String link;
	private int idResource;
	private String nameResource;
	private int idCat;
	private String nameCat;
	public Link(int id, String link, int idResource, String nameResource, int idCat, String nameCat) {
		super();
		this.id = id;
		this.link = link;
		this.idResource = idResource;
		this.nameResource = nameResource;
		this.idCat = idCat;
		this.nameCat = nameCat;
	}
	public Link() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public int getIdResource() {
		return idResource;
	}
	public void setIdResource(int idResource) {
		this.idResource = idResource;
	}
	public String getNameResource() {
		return nameResource;
	}
	public void setNameResource(String nameResource) {
		this.nameResource = nameResource;
	}
	public int getIdCat() {
		return idCat;
	}
	public void setIdCat(int idCat) {
		this.idCat = idCat;
	}
	public String getNameCat() {
		return nameCat;
	}
	public void setNameCat(String nameCat) {
		this.nameCat = nameCat;
	}
	
}
