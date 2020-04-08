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
import org.testng.annotations.Test;
import java.util.ArrayList;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import io.restassured.response.Response;

public class TC02_verifyAllCountriesOfWhichDataBetweenTableAndAPMismatch {
	/**********************************************************************************************************************************
	 * @author : Sagar
	 * QC ID   : TC-02
	 * JIRA ID : SUB-2
	 * Date    : April 6,2020
	 * Purpose : Verify All Countries of which data between table and API having mismatch 
	 * 			 (Don’t include countries which do not have any data in table) 
	 * *******************************************************************************************************************************/


	public class TC01_verifyAllCountriesOfWhichDataBetweenTableAndAPMismatch extends FunctionLibrary  implements IObjectRepository{
		
		
		
		@Test(groups={"regression"})
		public void verifyAllCountriesOfWhichDataBetweenTableAndAPMismatch () throws Exception {
			
			
			logger.log(LogStatus.INFO,"Step 1: Run SQL Query ''All Countries of which data between table and API having mismatch ");
			logger.log(LogStatus.INFO,"Verify: should get counties which has missing data");
			
			ArrayList<String> CountriesDataMissing =getQueryData(selectAllCountriesDataMissing,countryColumn);
			
			logger.log(LogStatus.INFO,"Step 2: Hit API");
			logger.log(LogStatus.INFO," should get missing data  countries");
			Response response= runRestcountriesAPI();
			ArrayList <String> missingCountryCapital = verifyCountriesCapital(CountriesDataMissing,response);
			ArrayList <String> missingCountryCode = 	verifyCountriesCodeMissing(CountriesDataMissing,response);
			
			logger.log(LogStatus.INFO,"Following Capitals Are missing     "+missingCountryCapital+ "  of following countries: "+CountriesDataMissing);
			
			logger.log(LogStatus.INFO,"Following Currency Code Are missing   "+missingCountryCode+ "  of following countries: "+CountriesDataMissing);
			
		}
}
}