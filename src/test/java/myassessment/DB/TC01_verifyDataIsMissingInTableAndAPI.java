package myassessment.DB;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import io.restassured.response.Response;

/**********************************************************************************************************************************
 * @author : Sagar
 * QC ID   : TC-01
 * JIRA ID : SUB-1
 * Date    : April 6,2020
 * Purpose : VerifyAll Countries for which Capital data is missing in table
 * *******************************************************************************************************************************/


public class TC01_verifyDataIsMissingInTableAndAPI extends FunctionLibrary  implements IObjectRepository{
	
	
	
	@Test(groups={"regression"})
	public void verifyAllCountriesforWhichCapitalDataIsMissingInTable () throws Exception {
		
		
		logger.log(LogStatus.INFO,"Step 1: Run SQL Query 'select capital from countries'");
		logger.log(LogStatus.INFO,"Verify: all capital should get fetched from countries table");
		
		ArrayList<String> CountriesCapitalName =getQueryData(selectAllCountriesCapital,countryColumn);
		
		logger.log(LogStatus.INFO,"Step 2: verify All Countries for which Capital data is missing in table");
		logger.log(LogStatus.INFO,"Verify: missing data should be display in report  and fail");
		Response response= runRestcountriesAPI();
		ArrayList <String> ar =verifyCountriesCapital(CountriesCapitalName,response);
		
		logger.log(LogStatus.INFO,"Following Capitals Are missing"+ar+ "of following countries:"+CountriesCapitalName);
		
	}
	
	//To check Retry works, uncomment below method
	
	/*
	@Test
	public void verifyRetryWorks()
	{
		Assert.assertEquals(false, true);
	}*/
	
}
	
	

	



