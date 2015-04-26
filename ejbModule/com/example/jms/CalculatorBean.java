package com.example.jms;
import java.sql.Timestamp;
import java.util.StringTokenizer;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(
mappedName="hq",
 activationConfig={
			@ActivationConfigProperty(propertyName="acknowledgeMode",propertyValue="Auto-acknowledge"),
			@ActivationConfigProperty(propertyName="destinationType",propertyValue="javax.jms.Queue")
}
)
public class CalculatorBean implements MessageListener
{
	public void onMessage(Message msg)
	{
		try
		{
			TextMessage tmsg=(TextMessage)msg;
			Timestamp sent=new Timestamp(tmsg.getLongProperty("sent"));
			StringTokenizer st=new StringTokenizer(tmsg.getText(),",");
			
			int start=Integer.parseInt(st.nextToken());
			int end=Integer.parseInt(st.nextToken());
			
			double growthrate=Double.parseDouble(st.nextToken());
			double saving =Double.parseDouble(st.nextToken());
			Thread.sleep(1000);
			double result=calculate(start,end,growthrate,saving);
			RecordManager.addRecord(sent,result);
			
			System.out.println("The onMessage is called");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	private double calculate(int start,int end,double growthrate,double saving)
	{
		double tmp=Math.pow(1.+growthrate/12.,12.*(end-start)+1);
		return saving*12.*(tmp-1)/growthrate;
	}
}