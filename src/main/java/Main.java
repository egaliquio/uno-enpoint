
import static spark.Spark.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.*;
import spark.Request;
import unomi.request.model.MakeJson;
import unomi.request.model.Event;

public class Main {
	private static String rJson;
	private static String inpType;
	private static String scope;
	private static String inpSid;
	private static JsonObject inpPropsObj;
	private static JsonObject inpSourceObj;
	private static JsonObject inpTargetObj;
    public static void main(String[] args) throws IOException {

    	enableCORS("*", "POST", "*");
        post("/unomi", (req, res) -> {
        	
        	Gson gson = new Gson();
        	
        	if (parseRequestJson(req)) {
        		
	        	Event event = new Event(inpType,scope,inpPropsObj,inpSourceObj,inpTargetObj);
	        	List<Event> inpJson = new ArrayList<>();
	        	inpJson.add(event);
	        	MakeJson rJsonObj = new MakeJson(inpSid,inpJson);
	        	rJson = gson.toJson(rJsonObj);
	        	
	        	return sendJson("https://cdp.centrit.com.ar/eventcollector", rJson);
	        	
        	} else {
        		return "Something went wrong with your Json.";
        	}
        	
        });
           
    }

    public static String sendJson(String Url, String json) {
    
        String postUrl = Url;// put in your url
        try {
            URL url = new URL(postUrl);
            String payload = json;
            byte[] postData = payload.getBytes(StandardCharsets.UTF_8);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.write(postData);
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream())); // Error is right here
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();

            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
    }
    
    public static boolean parseRequestJson(Request req) {
    	
    	JsonParser parser = new JsonParser();
    	String json = req.body().toString();
    	JsonElement jsonTree = parser.parse(json);

    	if(jsonTree.isJsonObject()){

    		JsonObject jsonObjectS = jsonTree.getAsJsonObject();
    	    inpSid = jsonObjectS.get("sid").getAsString();
    	    inpType = jsonObjectS.get("type").getAsString();
    	    JsonElement inpObj = jsonObjectS.get("details");
    	    JsonObject jsonObject = inpObj.getAsJsonObject();
    	    JsonElement inpProps = jsonObject.get("props");
    	    inpPropsObj = inpProps.getAsJsonObject();
    	    JsonElement inpSource = jsonObject.get("source");
    	    inpSourceObj = inpSource.getAsJsonObject();
    	    scope = inpSourceObj.get("scope").getAsString();
    	    JsonElement inpTarget = jsonObject.get("target");
    	    inpTargetObj = inpTarget.getAsJsonObject();
    	    return true;
    	    
    	} else {
    		
    		return false;
    		
    	}
    	
    }
    
    // Enables CORS on requests. This method is an initialization method and should be called once.
    private static void enableCORS(final String origin, final String methods, final String headers) {

        options("/*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OKI";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", origin);
            response.header("Access-Control-Request-Method", methods);
            response.header("Access-Control-Allow-Headers", headers);
            // Note: this may or may not be necessary in your particular application
            response.type("application/json");
        });
    }

}