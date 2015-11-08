package us.bssoft.data;

public class Data {
	private int Id, Level;
	private String Name;
	
	public Data(){}
	
	public Data(int id, String name, int level) {
		this.Id = id;
		this.Name = name;
		this.Level = level;
	}
	
	public void setId(int id) {
		this.Id = id;
	}
	
	public int getId() {
		return this.Id;
	}
	
	public void setName(String name) {
		this.Name = name;
	}
	
	public String getName() {
		return this.Name;
	}
	
	public void setLevel(int level) {
		this.Level = level;
	}
	
	public int getLevel() {
		return this.Level;
	}
}
