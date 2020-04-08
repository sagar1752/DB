package myassessment.DB;

import org.testng.annotations.Test;
import org.testng.annotations.Test;
import org.testng.annotations.Test;
import org.testng.annotations.Test;
import org.testng.annotations.Test;
import org.testng.annotations.Test;
import org.testng.annotations.Test;
import org.testng.annotations.Test;
import org.testng.annotations.Test;
import java.util.ArrayList;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import io.restassured.response.Response;

public class TC03_verifyCountriesBorderingWithAreSameOrNotBetweenAPIAndDB {

	/**********************************************************************************************************************************
	 * @author : Sagar
	 * QC ID   : TC-03
	 * JIRA ID : SUB-3
	 * Date    : April 6,2020
	 * Purpose : Verify Execute the API and check whether country which has maximum number of 
	 * 			 countries bordering with are same or not between API and DB
	 * *******************************************************************************************************************************/


	public class TC01_verifyCapitalDataIsMissingInTable extends FunctionLibrary  implements IObjectRepository{
		
		
		
		@Test(groups={"UAT"})
		public void verifyverifyCountriesBorderingWithAreSameOrNotBetweenAPIAndDB () throws Exception {
			
			
			logger.log(LogStatus.INFO,"Step 1: Run SQL Query 'to get  maximum number of \n" + 
					"	 * 			 countries bordering with are same or not between API and DB");
			logger.log(LogStatus.INFO,"Verify: should get Countries which  has maximum borders ");
			
			ArrayList<String> dbMaxBoderCountriesName =getQueryData(selectAllCountriesBorders,countryColumn);
			
			logger.log(LogStatus.INFO,"Step 2: Hit API ");
			logger.log(LogStatus.INFO,"Verify: should get maximum border country");
			Response response= runRestcountriesAPI();
			String apiMaxBorderCountry =verifyCountriesMaximumBroders(dbMaxBoderCountriesName,response);
			
			verifyDBAndAPIMaxBorderCountries(dbMaxBoderCountriesName,apiMaxBorderCountry);
			
			logger.log(LogStatus.INFO,"Following DB country which has maximum borders     "+dbMaxBoderCountriesName);
			
			logger.log(LogStatus.INFO,"Following API country which has maximum borders     "+apiMaxBorderCountry);
			
			
		}
	}
}
