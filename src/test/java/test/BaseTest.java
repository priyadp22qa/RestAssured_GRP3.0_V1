package test;

import static io.restassured.RestAssured.baseURI;

import java.util.Properties;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import utils.ReadPropertyFile;
import utils.StatusCodes;


public class BaseTest implements StatusCodes{

	String propFile = "config.properties";
	static Properties results;

	public void readConfigFile() {
		ReadPropertyFile prop = new ReadPropertyFile();
		results = prop.ReadAllProperty(propFile);
	}

	@BeforeSuite(alwaysRun=true)
	public void beforeSuite() {
		readConfigFile();
		baseURI = results.getProperty("url");
		


	}

}
