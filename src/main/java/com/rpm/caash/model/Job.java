package com.rpm.caash.model;

/**
 * POJO Class for a Job
 * 
 * @author Robert
 *
 */
public class Job {

    private String location;
	private String description;
	private String title;
	private String email_id;
	private int price;

    private Coordinates coordinates;
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
	public String getEmail_id() {
		return email_id;
	}
	
	public void setEmailId(String email_id) {
		this.email_id = email_id;
	}
    

}
