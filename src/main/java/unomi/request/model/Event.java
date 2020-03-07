package unomi.request.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Event {

@SerializedName("eventType")
@Expose
private String eventType;
@SerializedName("scope")
@Expose
private String scope;
@SerializedName("properties")
@Expose
private JsonObject properties;
@SerializedName("source")
@Expose
private JsonObject source;
@SerializedName("target")
@Expose
private JsonObject target;

/**
* No args constructor for use in serialization
* 
*/
public Event() {
}

/**
* 
* @param scope
* @param eventType
* @param source
* @param properties
* @param target
*/
public Event(String eventType, String scope, JsonObject properties, JsonObject source, JsonObject target) {
super();
String eT = "";
switch(eventType) {
case "addtocart":
	eT = "click";
  break;
case "a":
  // code block
  break;
default:
  // code block
	eT = "click";
}
this.eventType = eT;
this.scope = scope;
this.properties = properties;
this.source = source;
this.target = target;
}

public String getEventType() {
return eventType;
}

public void setEventType(String eventType) {
this.eventType = eventType;
}

public String getScope() {
return scope;
}

public void setScope(String scope) {
this.scope = scope;
}

public JsonObject getProperties() {
return properties;
}

public void setProperties(JsonObject properties) {
this.properties = properties;
}

public JsonObject getSource() {
return source;
}

public void setSource(JsonObject source) {
this.source = source;
}

public JsonObject getTarget() {
return target;
}

public void setTarget(JsonObject target) {
this.target = target;
}

}