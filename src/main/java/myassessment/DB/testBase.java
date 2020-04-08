package myassessment.DB;



import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;


public class testBase {
	static ExtentTest logger;
	static ExtentReports extent;
	@BeforeSuite(alwaysRun = true)
	public static void startTest()
	{
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd-HH_mm_ss_SSS");
		Date now = new Date();
		String strDate = sdfDate.format(now);

		extent = new ExtentReports(System.getProperty("user.dir")+"\\"+strDate+".html");
	}
		@BeforeMethod(alwaysRun = true)
		public void startMethod(Method method) {
			Test test = method.getAnnotation(Test.class);
			if (test == null) {
				return;
			}
			String class_name = this.getClass().getName();
			logger = extent.startTest("Class_Name : " + class_name + "</br>" + "Test_Name : " + method.getName() + "</br>"
					+ "Test_Desc : " + test.description());
			
		
	}
	
	@AfterClass
	public static void endTest()
	{
		extent.endTest(logger);
		extent.flush();
	}
}
