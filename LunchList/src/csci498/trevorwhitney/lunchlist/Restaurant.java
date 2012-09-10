package csci498.trevorwhitney.lunchlist;

import java.util.Date;

public class Restaurant {
	
	private String name = "";
	private String address = "";
	private String type = "";
	private Date lastVisited;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getLastVisited() {
		return lastVisited;
	}
	public void setLastVisited(Date lastVisited) {
		this.lastVisited = lastVisited;
	}
	
	public String toString() {
		return getName();
	}
	
}