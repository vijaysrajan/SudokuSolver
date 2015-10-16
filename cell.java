public class cell 
{
	private static int DEFAULT_ROW_COL_WIDTH = 9;
	private int value; // 1 .. 9 or 1.. 16 depending on Grid Size of the Sudoku
	private smallMap possiblelist;
	/*private smallMap impossibleList;*/
	private row      r;   // row is a set that has reference to this cell
	private col      c;   // col is a set that has reference to this cell
	private region   reg; // reg is a set that has reference to this cell
	private board    brd; // brd is a container that has array of 2 (n X n) dim cells where n is a whole square
	private int      rcWidth; // the actual row and column width
	public cellState state; 

	public static void copyPossibilityList(cell src, cell dest)
	{
		smallMap.copySmallMap(src.possiblelist, dest.possiblelist);
	}
	
	
	public boolean isCellPossibilityListEmpty()
	{
		return possiblelist.isEmpty();
	}
	
	public void setValue (int val, int st)
	{
		//bookkeeping
		if (brd != null)
		{
			if (st == cellState.FIXED)
				brd.incNumOfFixedCells();
			else if ( (st == cellState.RESOLVED) ||
					  (st == cellState.TEMP) )
			{
				brd.incNumOfResolvedCells();
				brd.setSomethingResolved(true);
			}
		}
	
		if ( (val > 0) && (val <= rcWidth) )
			value = val;
		else
		{
			value = 0;
			//System.out.println ("ERROR!!!!");
		}
		try
		{
			state.setState(st);
		}
		catch (generalException g)
		{
		}
	}
	public int getValue()
	{
		return value;
	}

	public cell(int val, int rowcolWidth, board b)
	{
		//do validation of board size with rowcolWidth
		//set row, col, region & board references.
		setValue(val, cellState.FIXED);
		possiblelist = new smallMap(rowcolWidth, false);
		possiblelist.set(value);
		try 
		{
			state = new cellState(cellState.FIXED);
		}
		catch (generalException g)
		{
		}
		rcWidth = rowcolWidth;
		r = null; c = null; reg = null; 
		brd = b;
	}

	public cell(int rowcolWidth, board b)
	{
		if (rowcolWidth == 0)
			rcWidth = cell.DEFAULT_ROW_COL_WIDTH;
		else
			rcWidth = rowcolWidth;
		//set row, col, region & board references.
		possiblelist = new smallMap(rcWidth, true);
		value = 0;
		try
		{
			state = new cellState(cellState.EMPTY);
		}
		catch (generalException g)
		{
		}
		r = null; c = null; reg = null; 
		brd = b;
	}

	public cell(int val, int rowcolWidth, row prow, col pcol, region preg, 
				board pbrd)
	{
		rcWidth = rowcolWidth;
		
		setValue(val, cellState.FIXED);
		
		possiblelist = new smallMap(rowcolWidth, false);
		possiblelist.set(value);
		try 
		{
			if (value > 0)
				state = new cellState(cellState.FIXED);
			else
				state = new cellState(cellState.EMPTY);
		}
		catch (generalException g)
		{
		}
				
		r = null; c = null; reg = null; brd = null; 

		if (prow != null) r = prow;
		if (pcol != null) c = pcol;
		if (preg != null) reg = preg;
		if (pbrd != null) brd = pbrd;
	}
	
	public row    getCellRow()    {return r;} 
	public col    getCellCol()    {return c;}
	public region getCellRegion() {return reg;}
	public board  getCellBoard()  {return brd;}
	public void   setCellRow(row rw)       {r = rw;} 
	public void   setCellCol(col cl)       {c = cl;}
	public void   setCellRegion(region rg) {reg = rg;}
	public void   setCellBoard(board bd)   {brd = bd;}
	
	public int   setValueIfOnlyOneSet() throws generalException
	{
		int tmp = possiblelist.getOnlyOneSet();
		return tmp;
	}
	
	public void   updatePossibilityList(int i) throws generalException // with an index
	{
		if ( (i < rcWidth) & (i > 0) ) // a small security check
		{
			possiblelist.unset(i);
			brd.setPossibilityListFlag(true);
		}
	}
	
	public void   updatePossibilityList(smallMap s) throws generalException
	{
		if (this.isCellPossibilityListEmpty())
				this.brd.setWrongBoardDetected(true);	
		for (int i =1; i <= rcWidth; i++)
		{
			if (s.get(i) == true)
			{
				possiblelist.unset(i);
				brd.setPossibilityListFlag(true);
			}
		}
	}
	
	public boolean isSetPossibilityList(int indx)
	{
		if ( (indx > 0) && (indx <= rcWidth) )
		{
			return possiblelist.get(indx);
		}
		return false;
	}
		
	public void DUMP_POSSIBILITY_LIST()
	{
		possiblelist.DUMP_MAP();
	}
};