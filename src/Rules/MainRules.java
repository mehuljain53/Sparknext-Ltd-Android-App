package Rules;

public class MainRules {
	String Title;
	String rule_id;
	public MainRules(String title, String rule_id) {
		super();
		Title = title;
		this.rule_id = rule_id;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getRule_id() {
		return rule_id;
	}
	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return rule_id+" "+Title;
	}

}
