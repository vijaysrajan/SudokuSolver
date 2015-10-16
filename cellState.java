//This class has all the possible types or states that a cell can be in.
//This class is more like an enum in C++

import java.lang.Exception ;

public class cellState 
{
	public static final int EMPTY = 0;
	public static final int FIXED = 1;
	public static final int RESOLVED = 2;
	public static final int TEMP = 3;
	private int state;
	public void setState(int v) throws generalException
	{
		if ( (v < EMPTY) || (v > TEMP) )
		{
			throw new generalException ("Cell::setState");
		}
		state = v;
	}
	public int getState()
	{
		return state;
	}
	cellState()
	{
		state = EMPTY;
	}
	cellState (int st) throws generalException
	{
		if ( (st < EMPTY) || (st > TEMP) )
		{
			throw new generalException ("Cell::setState");
		}
		state = st;
	}
};