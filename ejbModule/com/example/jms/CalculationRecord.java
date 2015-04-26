package com.example.jms;
import java.sql.Timestamp;
public class CalculationRecord
{
	public CalculationRecord(Timestamp sent,Timestamp processed,double result)
	{
		this.sent=sent;
		this.processed=processed;
		this.result=result;
	}
	public Timestamp sent;
	public Timestamp processed;
	public double result;
	
}