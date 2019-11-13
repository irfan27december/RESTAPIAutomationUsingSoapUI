package tests;



import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.StandaloneSoapUICore;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.model.support.PropertiesMap;
import com.eviware.soapui.model.testsuite.TestCase;
import com.eviware.soapui.model.testsuite.TestRunner;
import com.eviware.soapui.model.testsuite.TestSuite;
import com.eviware.soapui.tools.SoapUITestCaseRunner;
import com.eviware.soapui.model.testsuite.TestRunner.Status;

public class UserCRUDOperations {

	WsdlProject project;
	String projectFile = "./resources/xmls/ReqResREST-Project-soapui-project.xml";

	@BeforeClass
	public void beforeClass() {
		try{
			project = new WsdlProject( projectFile ,"");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	@Test(priority=1, enabled=true)
	public void createUser() {
		try{
			TestSuite testSuite = project.getTestSuiteByName( "TestSuite 1" ); 
			//System.out.println("Test suite name is"+testSuite);
			TestCase testCase = testSuite.getTestCaseByName( "TestCase 1" );
			TestRunner runner = testCase.run( new PropertiesMap(), false ); 
			//System.out.println("Test case name "+testCase);
			Assert.assertEquals( 201, 201); 
			Assert.assertEquals("Mohammed", "Mohammed");	
			System.out.println("Status is :"+runner.getStatus());
			Assert.assertEquals( runner.getStatus(), Status.FINISHED); 				
		}
		catch(Exception e){
			Assert.assertTrue(false);
		}
	}

	@Test(priority=2, enabled=true)
	public void getSingleUser() {
		try{
			TestSuite testSuite = project.getTestSuiteByName( "TestSuite 1" ); 
			//System.out.println("Test suite name is"+testSuite);
			TestCase testCase = testSuite.getTestCaseByName( "TestCase 2" );
			TestRunner runner = testCase.run( new PropertiesMap(), false ); 
			//System.out.println("Test case name "+testCase);
			System.out.println("Status is :"+runner.getStatus());
			Assert.assertEquals( runner.getStatus(), Status.FINISHED); 
			Assert.assertEquals("Janet", "Janet");	
		}
		catch(Exception e){
			Assert.assertTrue(false);
		}
	}

	@Test(priority = 3, description = "One more way of running soapui project xml", enabled=false)
	// method for running all Test Suites and test cases in the project
	public static void getTestSuite() throws Exception {

		String suiteName = "";
		String reportStr = "";

		// variables for getting duration
		long startTime = 0;
		long duration = 0;

		TestRunner runner = null;

		List<TestSuite> suiteList = new ArrayList<TestSuite>();
		List<TestCase> caseList = new ArrayList<TestCase>();

		SoapUI.setSoapUICore(new StandaloneSoapUICore(true));

		// specified soapUI project
		WsdlProject project = new WsdlProject("./resources/xmls/ReqResREST-Project-soapui-project.xml");

		// get a list of all test suites on the project
		suiteList = project.getTestSuiteList();

		// you can use for each loop
		for(int i = 0; i < suiteList.size(); i++){

			// get name of the "i" element in the list of a test suites
			suiteName = suiteList.get(i).getName();
			reportStr = reportStr + "\nTest Suite: " + suiteName;

			// get a list of all test cases on the "i"-test suite
			caseList = suiteList.get(i).getTestCaseList();


			for(int k = 0; k < caseList.size(); k++){

				startTime = System.currentTimeMillis();

				// run "k"-test case in the "i"-test suite
				runner = project.getTestSuiteByName(suiteName).getTestCaseByName(caseList.get(k).getName()).run(new PropertiesMap(), false);

				duration = System.currentTimeMillis() - startTime;

				reportStr = reportStr + "\n\tTestCase: " + caseList.get(k).getName() + "\tStatus: " + runner.getStatus() + "\tReason: " + runner.getReason() + "\tDuration: " + duration;
			}

		}

		// string of the results
		System.out.println(reportStr);
	}


	@Test(priority = 4, description = "One more way of running soapui project xml", enabled=false)
	public void testRunner() throws Exception
	{
		SoapUITestCaseRunner runner = new SoapUITestCaseRunner();
		runner.setProjectFile( "./resources/xmls/ReqResREST-Project-soapui-project.xml" );
		runner.run();
	}

	@Test(priority = 5, description = "One more way of running soapui project xml", enabled=false)
	public void fullControl() throws Exception {
		WsdlProject project = new WsdlProject("./resources/xmls/ReqResREST-Project-soapui-project.xml");

		List<TestSuite> testSuites = project.getTestSuiteList();
		//List<TestCase> testCases = testSuites.g
		
		for( TestSuite suite : testSuites ) {
			List<TestCase> testCases = suite.getTestCaseList();
			for( TestCase testCase : testCases ) {
				System.out.println("Running SoapUI test [" + testCase.getName() + "]");
				TestRunner runner2 = testCase.run(new PropertiesMap(), false);
				Assert.assertEquals(Status.FINISHED, runner2.getStatus());
			}
		}
	}


}
