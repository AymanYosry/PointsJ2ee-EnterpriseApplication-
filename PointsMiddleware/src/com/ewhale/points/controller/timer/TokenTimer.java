package com.ewhale.points.controller.timer;

import java.util.Calendar;
import java.util.Map;

import javax.ejb.Asynchronous;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Timer;

import com.ewhale.points.common.stores.TokentStore;
import com.ewhale.points.common.util.AppConstants;

/**
 * @author Ayman Yosry
 *         Session Bean implementation class EventTimer
 */
@Singleton
@LocalBean
@Asynchronous
public class TokenTimer
{
	@Schedule(hour = "*", minute = "*", second = "00")
	public void execute(Timer timer)
	{
		Calendar currentDate = Calendar.getInstance();
		currentDate.add(Calendar.MINUTE, AppConstants.SecurityConstants.TOKEN_TIME_OUT);

		//System.out.println("Executing TokenTimer ...");
		//System.out.println("Execution TokenTimer Time : " + new Date());
		//System.out.println("____________________________________________");

		for (Map.Entry<String, String[]> entry : TokentStore.TokenMap.entrySet())
		{
			String key = entry.getKey();
			String[] value = entry.getValue();

			long tokenDate = Long.parseLong(value[2]);
			if (tokenDate <= currentDate.getTimeInMillis())
			{
				TokentStore.TokenMap.remove(key);
				//System.out.println("Execution TokenTimer Time : Token removed == " + key);
			}
		}
	}
}
