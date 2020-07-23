package test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;


public class AddLicence extends BaseTest{

	String endPoint1 = "/license/v2/licenses";
	//String endPoint2 = "/license/v2/licenses";
	//String endPoint3 = "/license/v2/licenses/batch/add";
	String APIKey = "66a4796330d643d2a0fa6409c33f3fb2";
	String ContentType = "application/json";
	String username;
	String password;
	Map<String,String> headerMap;
	Logger log;
	Response response;

	RequestSpecification httpRequest;
	String filePath;
	String jsonBody;

	public String generateStringFromResource(String fileName) throws IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		String filePath = new File(classLoader.getResource("payload//"+fileName).getFile()).getPath();
		return new String(Files.readAllBytes(Paths.get(filePath)));
	}

	@BeforeClass(alwaysRun=true)
	public void beforeClass() {

		log = LogManager.getLogger(Test.class);
		headerMap = new HashMap<String,String>();
		username = results.getProperty("userName");
		password = results.getProperty("passWord");
	}

	@Test(priority =1,groups="single") 

	public void licensenew() throws IOException {


		String file = "LR-New.json"; 
		String jsonBody =generateStringFromResource(file); 
		//log.info("JSON Body :\n"+jsonBody);
		headerMap.put("APIKey", APIKey); 
		headerMap.put("Content-Type",ContentType);
		response = given().auth().preemptive().basic(username,password).headers(headerMap).body(jsonBody).when() .post(endPoint1);

		log.info("Response:\n"+response.getBody().jsonPath().prettify());
		log.info("API response - \n"+response.getStatusLine());

		JsonPath jp = new JsonPath(response.getBody().asString()); //Validate if
		// specific json element is equal to expected value

		assertTrue(response.getStatusCode() == STATUS_CODE_201,"Expected status code is 201.");
		assertTrue(jp.getString("type").equalsIgnoreCase("GCP") && jp.getString("code").contains("1"));
		boolean condition = true;
		Assert.assertTrue(condition);
		log.info(condition ? "Assert PASS" :"Assert FAIL");

		System.out.println("TestCase1-LicenceNew-Pass"); 
		System.out.println( "***********************************************************************************************" ) ;
	}


	@Test(priority = 2,groups="single")
	public void licenseexist() throws IOException
	{
		String file = "LR-Exist.json";		
		String jsonBody = generateStringFromResource(file);
		log.info("JSON Body :\n"+jsonBody);
		headerMap.put("APIKey", APIKey);
		headerMap.put("Content-Type",ContentType);
		response = given().auth().preemptive().basic(username, password).headers(headerMap).body(jsonBody).when()
				.post(endPoint1);

		log.info("API response - \n"+response.getStatusLine());
		log.info("Response:\n"+response.getBody().jsonPath().prettify());

		JsonPath jp = new JsonPath(response.getBody().asString());
		//Validate if specific json element is equal to expected value
		String key = jp.getString("key");
		
		assertTrue(response.getStatusCode() == STATUS_CODE_400, "Expected status code is 400.");
		assertTrue(jp.getString("reason.message").contains( "GCP license ".concat(key).concat(" already exists")), "licence already exist");
		assertTrue(jp.getString("code").contains("5"), "code for existing licence");

		System.out.println("TestCase2-LicenceExist-Pass");
		System.out.println("***********************************************************************************************");
	}
}
