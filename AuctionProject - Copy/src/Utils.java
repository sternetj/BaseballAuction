import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;

public class Utils {

	
	//private final static String host = "http://sternetj-1.wlan.rose-hulman.edu/api/";
	private final static String host = "http://169.254.85.226:81/api/";
	private final static String USER_AGENT = "Mozilla/5.0";
	
	public static String GETHANDLER(String url) throws IOException, URISyntaxException{
		
		url = host + url.replace(" ", "%20");
		System.out.println(url);
		URL obj = new URI(url).toURL();
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		// optional default is GET
		con.setRequestMethod("GET");
 
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + obj);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		System.out.println(response.toString());
		return response.toString();
	}
	
	public static String GET(final String url){
		String result = null;
		result = AccessController.doPrivileged(
				      new PrivilegedAction<String>() {
				          public String run() {
				              try {
								return GETHANDLER(url);
							} catch (IOException | URISyntaxException e) {
								e.printStackTrace();
							}
				            return "";
				          }
				        }
				     );		
		return result;
	}

}
