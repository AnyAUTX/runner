package com.AnyAUT;
import java.io.File;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;
public class CallDriverScripts {


	public static void WaitTillRunSetUpFileExists(String filepath)
	{
		File file = new File(filepath);
		int i=0;
		  while (true)
		  {
		    boolean exists = file.exists();  
		    if (exists) 
		    {  
		    	return;  
		    }
		    else
		    {
		      try {
				Thread.sleep(1000);
				i++;
				if(i==5){
				    System.out.println("File Not Found - Please check it");
				    return;
				    
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    }
		  }
	}

	public static void runDriverScripts(String strTestRunName) {
		// TODO Auto-generated method stub
			
		String strTestFileLocation = null;
		
		TestListenerAdapter listener = new TestListenerAdapter();
		TestNG prepRun = new TestNG();
		TestNG execTest = new TestNG();
		prepRun.setTestClasses(new Class[]{PrepareRun.class});
		execTest.setTestClasses(new Class[]{ExecuteTest.class});
		
		//prepRun.addListener(listener);
		System.out.println("Running prepare run");
		prepRun.run();
		
		
		strTestFileLocation = System.getProperty("user.dir") + "/Results/TestRun_" + strTestRunName + "/" + "TestRun_" + strTestRunName + ".xls";
		System.out.println(strTestFileLocation);
		WaitTillRunSetUpFileExists(strTestFileLocation);
		
		//execTest.addListener(listener);
		System.out.println("Running execute");
		execTest.run();
		prepRun = null;
		execTest = null;
		listener = null;
		
	}

}
