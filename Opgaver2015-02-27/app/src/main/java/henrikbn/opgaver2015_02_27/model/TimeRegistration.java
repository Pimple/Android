package henrikbn.opgaver2015_02_27.model;

import org.joda.time.DateTime;

/**
 * Created by HenrikBN on 04-03-2015.
 */
public class TimeRegistration implements Comparable<TimeRegistration>
{
	private String note;
	private DateTime start, stop;

	public TimeRegistration(String newStart, String newStop, String newNote)
	{
		note = newNote;
		start = DateTime.parse(newStart);
		stop = DateTime.parse(newStop);
	}

	@Override
	public int compareTo(TimeRegistration another)
	{
		return start.compareTo(another.getStart());
	}

	@Override
	public String toString()
	{
		return start + " - " + stop + ": " + note;
	}

	public String getNote()
	{
		return note;
	}

	public void setNote(String note)
	{
		this.note = note;
	}

	public DateTime getStart()
	{
		return start;
	}

	public void setStart(DateTime start)
	{
		this.start = start;
	}

	public DateTime getStop()
	{
		return stop;
	}

	public void setStop(DateTime stop)
	{
		this.stop = stop;
	}
}
