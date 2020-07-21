package test;


import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class DeleteLicenceBatch extends BaseTest {
	
	String endPoint1 = "/license/v2/licenses/GCP/950234559";
	String endPoint3 = "/license/v2/licenses/batch/delete";
	String APIKey = "66a4796330d643d2a0fa6409c33f3fb2";
	String ContentType = "application/json";
	String indiaMoid = "abc";
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

	@BeforeClass(alwaysRun=true)
	public void beforeClass() 
	{
		log = LogManager.getLogger(Test.class);
		headerMap = new HashMap<String,String>();
		username = results.getProperty("userName");
		password = results.getProperty("passWord");
	

	}
	@AfterMethod
	public void afterMethod() {
		headerMap.clear();
	}

	@Test(priority =8 , groups = "batch")
			public void DeleteBatchLicense() throws IOException {
		String file = "BatchLR.json";		
		String jsonBody = generateStringFromResource(file);
		log.info("JSON Body :\n"+jsonBody);
		headerMap.put("APIKey", APIKey);
		headerMap.put("Content-Type",ContentType);
		response = given().auth().preemptive().basic(username, password).headers(headerMap).body(jsonBody)
				.when()
				.post(endPoint3);
		
		System.out.println("API response is - \n"+response.getStatusCode());
		log.info("Response:\n"+response.getBody().jsonPath().prettify());

		assertTrue(response.getStatusCode() == STATUS_CODE_200, "Expected status code is 200.");
		System.out.println("TestCase8-Delete licence batch-Pass");
		System.out.println("***********************************************************************************************");
	}
}