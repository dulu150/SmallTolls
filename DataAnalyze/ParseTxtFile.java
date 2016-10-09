/**
 * 
 */
package keyresouceanalyze;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * @author nickel zhang
 * 此函数功能是读取关键资源信息，然后分析关键信息中比例过大的关键资源
 */
public class keyresouceanalyze {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		keyresouceanalyze mykeyresouceanalyze = new keyresouceanalyze();
		mykeyresouceanalyze.Init();
		mykeyresouceanalyze.test();
	}
	
	/**
	 * initialize the data buffer
	 * the data buffer format as key-value
	 */
	public void Init()
	{
		Analyzeresult = new ArrayList<String>();
	}
	
	/**
	 * Set the Alarm ThreshHold 
	 */
	public void SetAlarmThreshHold()
	{
		System.out.println("Please input the Alarm ThreshHold:");
		Scanner scanner = new Scanner(System.in);		
		AlarmThreshHold = scanner.nextFloat();
		System.out.println("The Alarm ThreshHold is:" + AlarmThreshHold);
		scanner.close();
	}
	
	/**
	 * @param FilePath
	 * @return
	 * Parse the input file into Analyze result
	 * For example:arc 10.0 will be parsed as (arc,10.0)->(Tap, 11.0)
	 *             Tap 11.0
	 */
	public int ParseFile(String FilePath)
	{
		try
		{
			File file = new File(FilePath);
			if(file.isFile() && file.exists())
			{
				InputStreamReader read = new InputStreamReader(new FileInputStream(file));
				BufferedReader bufferedReader = new BufferedReader(read);
				
				//Read the file line by line
				String LineStr = null;
				SetAlarmThreshHold();
				while((LineStr = bufferedReader.readLine()) != null)
				{
                    System.out.println(LineStr);
    				//parse the input line using space
    				String[] ParseResult = LineStr.split("\\s+");
    				
    				try
    				{
    					float percent = Float.parseFloat(ParseResult[2]);
    					if(percent > AlarmThreshHold)
    					{
    						Analyzeresult.add(LineStr);
    					}
    					else
    					{
    						System.out.println("this resource is normal [" + LineStr + "] is not valid!!!");
    					}
    				}catch (Exception e){
    					System.out.println("this line[" + LineStr + "] is not valid!!!");
    				}
                }

                read.close();
			}
			else
			{
				System.out.println("the input file " + FilePath + " doesn't exist!!!");
			}
			
		}catch (Exception e) {
			System.out.println("something is wrong!!!");
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * @param outfilepath
	 * @return
	 * output the data into file
	 */
	public int OutputAnalyzeResult(String outfilepath)
	{
		FileWriter fWriter = null;
	    BufferedWriter writer = null;
	    try {
	        fWriter = new FileWriter(outfilepath, true);
	        writer = new BufferedWriter(fWriter);

	        for(int i = 0; i < Analyzeresult.size(); i++)
	        {
		        writer.append(Analyzeresult.get(i));
		        writer.newLine();	        	
	        }
   
	        writer.close();
			System.out.println("Evertything is done!!!");
	    } catch (Exception e) {
			System.out.println("something is wrong!!!");
			e.printStackTrace();
	    }
		return 0;
	}
	
	/**
	 * just for test
	 */
	public void test()
	{
		Analyzeresult.add("abc");
		Analyzeresult.add("def");
		System.out.println(Analyzeresult.get(0));
		System.out.println(Analyzeresult.get(1));
		ParseFile("D:\\keyresource.txt");
		System.out.println("Everything is done!!!");
		
		OutputAnalyzeResult("D:\\result.txt");
	}
	
	public float AlarmThreshHold = 0;
	public List<String> Analyzeresult;
}
