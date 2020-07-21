package test;


import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class DeleteLicence extends BaseTest {
	
	String endPoint1 = "/license/v2/licenses/GCP/950234559";
	String APIKey = "66a4796330d643d2a0fa6409c33f3fb2";
	String indiaMoid = "abc";
	String username;
	String password;
	Map<String,String> headerMap;
	Logger log;
	Response response;


	@BeforeClass(alwaysRun=true)
	public void beforeClass() {
		log = LogManager.getLogger(Test.class);
		headerMap = new HashMap<String,String>();
		username = results.getProperty("userName");
		password = results.getProperty("passWord");
	
	
	}
	@AfterMethod
	public void afterMethod() {
		headerMap.clear();
	}

	@Test(priority =5,groups="single")
			public void DeleteSingleLicense() {
		headerMap.put("APIKey", APIKey);
		response = given().auth().preemptive().basic(username, password).headers(headerMap).delete(endPoint1);
		log.info("Response:\n"+response.getBody().jsonPath().prettify());
		log.info("StatusCode:\n"+response.getStatusCode());
				assertTrue(response.getStatusCode() == STATUS_CODE_200);
				System.out.println("TestCase5-Licence deleted successfully-Pass");
				System.out.println("***********************************************************************************************");
	}
}