package test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

// run this test case after you delete a licence 
public class trial extends BaseTest {
	String endPoint1 = "/license/v2/licenses";
	String endPoint2 = "/license/v2/licenses";
	String endPoint3 = "/license/v2/licenses/batch/add";
	String APIKey = "66a4796330d643d2a0fa6409c33f3fb2";
	String ContentType = "application/json";
	//String indiaMoid = "abc";
	String username;
	String password;
	Map<String,String> headerMap;
	Logger log;
	Response response;

	public String generateStringFromResource(String fileName) throws IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		String filePath = new File(classLoader.getResource("payload//"+fileName).getFile()).getPath();
		return new String(Files.readAllBytes(Paths.get(filePath)));
	}

	@BeforeClass
	public void beforeClass() {;
	//headerMap.clear();
	log = LogManager.getLogger(Test.class);
	headerMap = new HashMap<String,String>();
	username = results.getProperty("userName");
	password = results.getProperty("passWord");
	System.out.println("abc"+username);
	}


	@Test
	public  void Testlicence() throws IOException
	{


		String file = "LR-Exist.json";		
		String jsonBody = generateStringFromResource(file);
		log.info("JSON Body :\n"+jsonBody);
		headerMap.put("APIKey", APIKey);
		headerMap.put("Content-Type",ContentType);
		response = given().auth().preemptive().basic(username, password).headers(headerMap).body(jsonBody).when()
				.post(endPoint1);

		//System.out.println("API response - \n"+response.getStatusCode());
		log.info("Response:\n"+response.getBody().jsonPath().prettify());
		log.info("API response - \n"+response.getStatusLine());

		//Get Response body
		ResponseBody body = response.getBody();

		//Get response body as string
		String bodyStringValue = body.asString();

		//Get JSON path representation from respone body
		//JsonPath jsonPathEvaluator = response.jsonPath();
		JsonPath jp = new JsonPath(response.getBody().asString());
		//Get specific element from json document

		//String type = jsonPathEvaluator.getString("type");
		//String code = jsonPathEvaluator.getString("code");
		//String key = jsonPathEvaluator.getString("key");
		String key = jp.getString("key");

	//	System.out.println("type is "+type);
	//	System.out.println("code is"+code);
		System.out.println("key is" +key);

		//Validate if specific json element is equal to expected value
		//assertTrue(response.getStatusCode() == STATUS_CODE_400, "Expected status code is 400.");
		//assertTrue(type.equalsIgnoreCase("GCP") && code.contains("5"));
		Boolean abc = jp.getString("reason.message").contains( "GCP license ".concat(key).concat(" already exists"));
		//Boolean xyz = jp.getString("reason.message").contains("GCP license 950234557 already exists");
		System.out.println("abc is " +abc);

		System.out.println("TestCase1-LicenceNew-Pass");
		System.out.println("***********************************************************************************************");
	}
}