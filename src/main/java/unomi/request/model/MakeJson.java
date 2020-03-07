package unomi.request.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MakeJson {

	@SerializedName("sessionId")
	@Expose
	private String sessionId;
	@SerializedName("events")
	@Expose
	private List<Event> events = null;
	
	/**
	* No args constructor for use in serialization
	* 
	*/
	public MakeJson() {
	}
	
	/**
	* 
	* @param sessionId
	* @param events
	*/
	public MakeJson(String sessionId, List<Event> events) {
		super();
		this.sessionId = sessionId;
		this.events = events;
	}
	
	public String getSessionId() {
		return sessionId;
	}
	
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public List<Event> getEvents() {
		return events;
	}
	
	public void setEvents(List<Event> events) {
		this.events = events;
	}

}