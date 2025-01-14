package com.ewhale.points.controller.timer;

import javax.ejb.Asynchronous;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Timer;

/**
 * @author Ayman Yosry
 *         Session Bean implementation class EventTimer
 */
@Singleton
@LocalBean
@Asynchronous
public class InvoiceTimer
{
	@Schedule(dayOfMonth = "01", hour="00", minute="00", second="00")
	public void execute(Timer timer)
	{
		//IMP_Ayman what is the business method to call
		//System.out.println("Executing InvoiceTimer ...");
		//System.out.println("Execution InvoiceTimer Time : " + new Date());
		//System.out.println("____________________________________________");
	}

}
