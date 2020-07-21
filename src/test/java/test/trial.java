package test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

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


public class trial extends BaseTest {
	String endPoint1 = "/license/v2/licenses/gcp/950234557";
	String endPoint2 = "/license/v2/licenses";
	String endPoint3 = "/license/v2/licenses/batch/add";
	String APIKey = "66a4796330d643d2a0fa6409c33f3fb2";
	String ContentType = "application/json";
	//String indiaMoid = "abc";
	String username;
	String password;
	Map<String,String> headerMap;
	Logger log;
	String response;
	
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
		String file = "Batch_dup.json";		
		String jsonBody = generateStringFromResource(file);
		log.info("JSON Body :\n"+jsonBody);
		headerMap.put("APIKey", APIKey);
		headerMap.put("Content-Type",ContentType);
		
	
		response =   given().log().all().auth().preemptive().basic(username, password).headers(headerMap).body(jsonBody)
				.when()
				.post(endPoint3)
	.then().log().all().assertThat().statusCode(STATUS_CODE_200)
			.body("code", is(2))
			.extract().response().asString();
		
		System.out.println("API response is - \n"+response);
}
}