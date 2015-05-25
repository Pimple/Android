package otherstuff;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import data.DAO;

public class KoerselPagerAdapter extends PagerAdapter
{
	private Context context;
	private Cursor cursor;

	public KoerselPagerAdapter(Context context, Cursor cursor)
	{
		this.cursor = cursor;
	}

	@Override
	public int getCount()
	{
		return cursor.getCount();
	}

	@Override
	public boolean isViewFromObject(View view, Object object)
	{
		return view.equals(object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position)
	{
		return super.instantiateItem(container, position);
	}
}
