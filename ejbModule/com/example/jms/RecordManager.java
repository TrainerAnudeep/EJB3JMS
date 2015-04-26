package com.example.jms;
import java.sql.Timestamp;
import java.util.ArrayList;
public class RecordManager
{
	public RecordManager()
	{}
	private static int maxsize=1000;
	private static ArrayList<CalculationRecord> crs=new ArrayList<CalculationRecord>();
	public static void addRecord(Timestamp sent ,double result)
	{
	
		if(crs.size()>maxsize)
		{
				crs.remove(0);
			}
				Timestamp processed=new Timestamp(System.currentTimeMillis());
				crs.add(new CalculationRecord(sent,processed,result));
		}
		public static CalculationRecord getRecord(long sent)
		{
			for(int i=0;i<crs.size();i++)
			{
				CalculationRecord cr=crs.get(i);
				if(cr.sent.equals(new Timestamp(sent))){
					return cr;
				}
				
			}
			return null;
		}
	}
