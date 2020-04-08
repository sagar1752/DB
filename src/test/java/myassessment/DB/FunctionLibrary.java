package myassessment.DB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import myassessment.DB.Pojo.Country;
import myassessment.DB.Pojo.Currency;
public class FunctionLibrary extends testBase {


	String url="jdbc:mysql://localhost:3306/FirstDB";
	String uname="root";
	String pass="s@g@r1752";


	public Response  runRestcountriesAPI ()  {  
		logger.log(LogStatus.INFO, "START: <<<<<<<<<< runRestcountriesAPI() >>>>>>>>> ");
		//URI
		RestAssured.baseURI="https://restcountries.eu/rest/v2/all";

		//Create Connection With server
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.request(Method.GET);
		response.prettyPrint();
		logger.log(LogStatus.INFO, "END: <<<<<<<<<< runRestcountriesAPI() >>>>>>>>> ");
		return response;

	}

	public String getAllcountries(){
		logger.log(LogStatus.INFO, "START: <<<<<<<<<< getAllcountries() >>>>>>>>> ");
		Response response= runRestcountriesAPI();
		JsonPath jsonpath = response.jsonPath();

		List <String> jRoot= jsonpath.getList("name"); 
		ArrayList <String> ar = new ArrayList <String>();

		for(int i=0; i<=jRoot.size()-1; i++) {
			ar.add(jRoot.get(i));

		}
		StringBuffer sb = new StringBuffer();
		for (String s : ar) {
			sb.append(s);
			sb.append(" ");
		}
		String str = sb.toString();
		logger.log(LogStatus.INFO, "END: <<<<<<<<<< getAllcountries() >>>>>>>>> ");
		return str;

	}

	public String getAllCapital(){
		logger.log(LogStatus.INFO, "START: <<<<<<<<<< getAllCapital() >>>>>>>>> ");
		Response response= runRestcountriesAPI();
		JsonPath jsonpath = response.jsonPath();
		List <String> jRoot1= jsonpath.getList("capital"); 

		ArrayList <String> ar = new ArrayList <String>();

		for(int i=0; i<=jRoot1.size()-1; i++) {
			ar.add(jRoot1.get(i));

		}

		StringBuffer sb = new StringBuffer();

		for (String s : ar) {
			sb.append(s);
			sb.append(" ");
		}
		String str = sb.toString();
		logger.log(LogStatus.INFO, "END: <<<<<<<<<< getAllCapital() >>>>>>>>> ");
		return str;
	}


	public ArrayList <String> getAllBorders(){
		logger.log(LogStatus.INFO, "START: <<<<<<<<<< getAllBorders() >>>>>>>>> ");
		Response response= runRestcountriesAPI();
		JsonPath jsonpath = response.jsonPath();

		List <String> jRoot2= jsonpath.getList("$borders"); 

		ArrayList <String> ar = new ArrayList <String>();

		for(int i=0; i<=jRoot2.size()-1; i++) {
			String id  = response.jsonPath().getString("borders[" + i + "]");
			ar.add(id);

		}
		logger.log(LogStatus.INFO, "END: <<<<<<<<<< getAllBorders() >>>>>>>>> ");
		return ar;
	}


	public void verifyTextExist(String parentString, String subString, ExtentTest logger) {
		logger.log(LogStatus.INFO, "START: <<<<<<<<<< verifyTextExist() >>>>>>>>> ");
		try
		{ 
			if ( parentString.toLowerCase().contains(subString.toLowerCase())) {
				logger.log(LogStatus.PASS,subString + " was found in " + parentString);
			} else {
				logger.log(LogStatus.FAIL,
						subString + " was NOT found in " + parentString);
			}
		}
		catch(NullPointerException e) 
		{ 
			logger.log(LogStatus.FAIL,
					subString + " SubString Value is null and it is not found in  " + parentString);
		} 
		logger.log(LogStatus.INFO, "END: <<<<<<<<<< verifyTextExist() >>>>>>>>> ");

	}

	public void verifyTextDoesNotExist(String parentString, String subString) {
		logger.log(LogStatus.INFO, "START: <<<<<<<<<< verifyTextDoesNotExist() >>>>>>>>> ");
		if (parentString.toLowerCase().contains(subString.toLowerCase())) {
			logger.log(LogStatus.FAIL,
					subString + " was found in " + parentString);
		} else {
			logger.log(LogStatus.PASS, subString + " was NOT found in " + parentString);
		}
		logger.log(LogStatus.INFO, "START: <<<<<<<<<< verifyTextDoesNotExist() >>>>>>>>> ");
	}

	public void verifyCapitalData(ArrayList<String> CountriesCapitalName) {
		logger.log(LogStatus.INFO, "START: <<<<<<<<<< verifyCapitalData() >>>>>>>>> ");
		String allCapital =getAllcountries();

		for (int i =0; i<=CountriesCapitalName.size()-1; i++) {

			verifyTextExist(allCapital, CountriesCapitalName.get(i), logger);
		}
		logger.log(LogStatus.INFO, "START: <<<<<<<<<< verifyCapitalData() >>>>>>>>> ");
	}



	public ArrayList<String>   getQueryData (String query, String column)    throws Exception {
		logger.log(LogStatus.INFO, "START: <<<<<<<<<< getQueryData() >>>>>>>>> ");


		ArrayList<String> ar = new ArrayList<String>();

		Class.forName("com.mysql.jdbc.Driver"); 

		Connection  con = DriverManager.getConnection(url,uname,pass);
		Statement st = con.createStatement();
		ResultSet rs=st.executeQuery(query);
		String name ="";
		while(rs.next()) {
			name=rs.getString(column);

			ar.add(name);
		}
		st.close();
		con.close();

		logger.log(LogStatus.INFO, "START: <<<<<<<<<< getQueryData() >>>>>>>>> ");
		return ar;

	}

	public static <K, V> K getKey(Map<K, V> map, V value) {
		for (K key : map.keySet()) {
			if (value.equals(map.get(key))) {
				return key;
			}
		}
		return null;
	}


	public ArrayList<String>  verifyCountriesCapital(ArrayList<String> CountrieslName, Response response ) {
		Country[] jsonBody =response.as(Country[].class,ObjectMapperType.GSON);
		ArrayList<String> ar = new ArrayList<String>();
		HashMap<String, String> mapCapital = new HashMap<String,String>(); 
		for(int i=0; i<jsonBody.length; i++) {
			mapCapital.put(jsonBody[i].getName(), jsonBody[i].getCapital());
		}

		for (int j=0; j<=CountrieslName.size()-1;j++) {
			ar.add(mapCapital.get(CountrieslName.get(j).replaceAll("[\\[\\]]", "")));
		}
		return ar;

	}

	public ArrayList<String>  verifyCountriesCodeMissing(ArrayList<String> CountrieslName, Response response) {
		Country[] jsonBody =response.as(Country[].class,ObjectMapperType.GSON);
		ArrayList<String> ar = new ArrayList<String>();
		HashMap<String, String> mapCountriesCode = new HashMap<String,String>(); 

		for(int i=0; i<jsonBody.length; i++) {

			List<Currency> cur =jsonBody[i].getCurrencies();
			for (Currency cri :cur) {

				mapCountriesCode.put(jsonBody[i].getName(), cri.getCode());

			}
		}
		for (int j=0; j<=CountrieslName.size()-1;j++) {
			ar.add(mapCountriesCode.get(CountrieslName.get(j).replaceAll("[\\[\\]]", "")));
		}
		return ar;

	}


	public String verifyCountriesMaximumBroders(ArrayList<String> CountrieslName, Response response) {
		Country[] jsonBody =response.as(Country[].class,ObjectMapperType.GSON);

		HashMap<String, List<String>> mapborder = new HashMap<String, List<String>>(); 

		HashMap<String, Integer> mapborderSize = new HashMap<String, Integer>(); 

		for(int i=0; i<jsonBody.length; i++) {
			mapborder.put(jsonBody[i].getName(), jsonBody[i].getBorders());

			for (int j=i; j<jsonBody.length;j++) {

				mapborderSize.put(jsonBody[i].getName(), mapborder.get(jsonBody[i].getName()).size());

			}

		}
		
		 Integer maxValue = Collections.max(mapborderSize.values()); 
		
		String maxBorderCountry = getKey(mapborderSize, maxValue);
		
		return maxBorderCountry;

	}
	public void verifyDBAndAPIMaxBorderCountries (ArrayList<String> dbMaxBoderCountriesName, String apiMaxBorderCountry ) {
		for(int i=0; i<dbMaxBoderCountriesName.size()-1; i++) {
			verifyTextExist(dbMaxBoderCountriesName.get(i),apiMaxBorderCountry, logger);
			
		}
	}
	

}
