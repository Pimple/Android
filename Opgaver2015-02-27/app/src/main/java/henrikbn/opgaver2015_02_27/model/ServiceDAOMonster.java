package henrikbn.opgaver2015_02_27.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HenrikBN on 04-03-2015.
 */
public class ServiceDAOMonster
{
	private static ServiceDAOMonster instance;
	private List<TimeRegistration> home, work, absent;

	private ServiceDAOMonster()
	{
		home = new ArrayList<>();
		work = new ArrayList<>();
		absent = new ArrayList<>();
	}

	public static ServiceDAOMonster getInstance()
	{
		if (instance == null)
			instance = new ServiceDAOMonster();
		return instance;
	}

	public List<TimeRegistration> getHome()
	{
		return home;
	}
	public TimeRegistration addTimeAtHome(String start, String stop, String note)
	{
		TimeRegistration newTime = new TimeRegistration(start, stop, note);
		home.add(newTime);
		return newTime;
	}
	public void removeTimeAtHome(TimeRegistration time)
	{
		home.remove(time);
	}

	public List<TimeRegistration> getWork()
	{
		return work;
	}
	public TimeRegistration addTimeAtWork(String start, String stop, String note)
	{
		TimeRegistration newTime = new TimeRegistration(start, stop, note);
		work.add(newTime);
		return newTime;
	}
	public void removeTimeAtWork(TimeRegistration time)
	{
		work.remove(time);
	}

	public List<TimeRegistration> getAbsent()
	{
		return absent;
	}
	public TimeRegistration addTimeAbsent(String start, String stop, String note)
	{
		TimeRegistration newTime = new TimeRegistration(start, stop, note);
		absent.add(newTime);
		return newTime;
	}
	public void removeTimeAbsent(TimeRegistration time)
	{
		absent.remove(time);
	}
}
