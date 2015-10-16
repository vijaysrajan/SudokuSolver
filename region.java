//set class for cells in a region
public class region extends set
{
	public int  regionID;
	
	//board creates region
	public region (int rowcolWidth, int ID, board b)
	{
	    // call super set()  with relevant params
		super (rowcolWidth, b);
		regionID = ID;
	}
	
	void addCellRefToRegion(cell cl) throws generalException
	{
		if (index < (rowcolWidth) )
		{
			c[index] = cl;
			if (c[index].state.getState() != cellState.EMPTY)
			{
				if (this.map.get(c[index].getValue()))
				{
					//Ouchh!!!! dont fool the system
					throw new generalException("region::addCellRefToregion detected duplicate in region");
				}
				this.map.set(c[index].getValue());
			}
			index++;
		}
	}
	
	//This method will be used in the analysis phase
	boolean checkRegionConstraint()
	{
		//for each cell in region, check if number is repeated
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
	
	void applyRegionReduction() 
	{
		int i = 0;
		smallMap s = new smallMap(rowcolWidth,false);
		//for each fixed or resolved cell in region, find the values  and create a map 
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