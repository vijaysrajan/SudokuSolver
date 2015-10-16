
//set class for cells in a row
public class row extends set
{
	public int  rowID;
	
	//board creates row
	public row (int rowcolWidth, int ID, board b)
	{
	    // call super set()  with relevant params
		super (rowcolWidth, b);
		rowID = ID;
	}
	
	void addCellRefToRow(cell cl) throws generalException
	{
		if (index < (rowcolWidth) )
		{
			c[index] = cl;
			if (c[index].state.getState() != cellState.EMPTY)
			{
				if (this.map.get(c[index].getValue()))
				{
					//Ouchh!!!! dont fool the system
					throw new generalException("row::addCellRefToRow detected duplicate in row");
				}
				this.map.set(c[index].getValue());
			}
			index++;
		}
	}
	
	//This method will be used in the analysis phase
	boolean checkRowConstraint()
	{
		//for each cell in row, check if number is repeated
		int i = 0;
		smallMap s = new smallMap(rowcolWidth,false);
		for (i = 0; i < rowcolWidth; i++)
		{
			if (!s.get(c[i].getValue()))
				s.set(c[i].getValue());
			else
				return false;
		}
		return true;
	}
	
	void applyRowReduction()
	{
		int i = 0;
		smallMap s = new smallMap(rowcolWidth,false);
		//for each fixed or resolved cell in row, find the values  and create a map 
		for (i = 0; i < rowcolWidth; i++)
		{
			if (  (c[i].state.getState() == cellState.FIXED) ||
				  (c[i].state.getState() == cellState.RESOLVED) )
				s.set(c[i].getValue());
		}
		//For each empty Cell, apply the reduction to the cell's smalMap
		for (i = 0; i < rowcolWidth; i++)
		{
			if (c[i].state.getState() == cellState.EMPTY)
			{
				super.updatePossibilityList(c[i],s);
			}
		}
	}
}
