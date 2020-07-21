package test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertTrue;

public class GetLicence extends BaseTest {
	
	String endPoint1 = "/license/v2/licenses/gcp/950234557";
	String endPoint2 = "/license/v2/licenses/gcp/850234557";
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

	@Test(priority = 3, groups="single")
	public void getSingleLicenseGoodrequest() throws IOException
	{
		headerMap.put("APIKey", APIKey);
		response = given().auth().preemptive().basic(username, password).headers(headerMap).get(endPoint1);
		log.info("API Response - \n"+response.asString());
		log.info("Response:\n"+response.getBody().jsonPath().prettify());
		log.info("StatusCode 1="+response.getStatusCode());
		assertTrue(response.getStatusCode() == STATUS_CODE_200);
		System.out.println("TestCase3-getsinglelicence-Pass");
		System.out.println("***********************************************************************************************");
	}
	
	@Test(priority =4,  groups="single")
	public void getSingleLicenceBadrequest() throws IOException
	{
		headerMap.put("APIKey", APIKey);
		response = given().auth().preemptive().basic(username, password).headers(headerMap).get(endPoint2);
		log.info("API Response - \n"+response.asString());
		//log.info("Response:\n"+response.getBody().jsonPath().prettify());
		log.info("StatusCode 2="+response.getStatusCode());
		assertTrue(response.getStatusCode() == STATUS_CODE_404);
		System.out.println("TestCase4-getsingleLicenceBad-Pass");
		System.out.println("***********************************************************************************************");
	}
}