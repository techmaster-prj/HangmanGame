package us.bssoft.history;

public class History {
	String email;
	int id, level, score;
	public History() {}
	public History(int id, String email, int level, int score) {
		this.email = email;
		this.id = id;
		this.level = level;
		this.score = score;
	}
	
	public int getID() {
		return id;
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getScore() {
		return score;
	}
	
	public String getEmail() {
		return email;
	}
}
