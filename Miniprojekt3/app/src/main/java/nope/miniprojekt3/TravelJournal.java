package nope.miniprojekt3;

import org.joda.time.LocalDate;

/**
 * Created by HenrikBN on 10-04-2015.
 */
public class TravelJournal
{
	private int id;
	private String title, note, address, picture;
	private LocalDate dateVisited;
	private boolean visitAgain;

	public TravelJournal(int id, String title, String note, String address, String picture, String dateVisited, boolean visitAgain)
	{
		this.id = id;
		this.title = title;
		this.note = note;
		this.address = address;
		this.picture = picture;
		this.dateVisited = new LocalDate(dateVisited);
		this.visitAgain = visitAgain;
	}

	public String toString()
	{
		return dateVisited.toString() + " " + title;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getNote()
	{
		return note;
	}

	public void setNote(String note)
	{
		this.note = note;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getPicture()
	{
		return picture;
	}

	public void setPicture(String picture)
	{
		this.picture = picture;
	}

	public LocalDate getDateVisited()
	{
		return dateVisited;
	}

	public void setDateVisited(LocalDate dateVisited)
	{
		this.dateVisited = dateVisited;
	}

	public boolean isVisitAgain()
	{
		return visitAgain;
	}

	public void setVisitAgain(boolean visitAgain)
	{
		this.visitAgain = visitAgain;
	}
}
