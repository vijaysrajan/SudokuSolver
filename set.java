public abstract class set
{
	protected cell [] c;
	protected board brd;
	protected int index;
	protected int rowcolWidth;
	protected smallMap map;
	set(int r, board b)
	{
		rowcolWidth = r;
		c = new cell[rowcolWidth];
		brd = b;
		index = 0;
		map = new smallMap(rowcolWidth, false);
	}
	
	public int getId()
	{
		return 0;
	}
	
	//find doubles and triples in matching cells and update the possibility list of all those cells
	
	static void DUMP_SET(set s)
	{
		for (int i = 0; i < board.rowcolWidth; i++)
		{
			if (s.c[i].getValue() > 0)
				System.out.print(s.c[i].getValue());
			else
				System.out.print(".");
		}
	}
	//if a single number is a possibility of exactly one cell, then That cell takes the value of that possibility
	void doSingleReduction() throws generalException
	{
		int i = 0;
		int j = 0;
		intCellStruct [] cntrTable = new intCellStruct[rowcolWidth];
		
		//initialize counter table
		for (i = 0; i < rowcolWidth; i++)
			cntrTable[i] = new intCellStruct();
		
		//set cntr table
		for (j = 0; j < rowcolWidth; j++)
		{
			if (c[j].state.getState() == cellState.EMPTY) 
			{
				for (i = 1; i <= rowcolWidth; i++)
				{
					if (c[j].isSetPossibilityList(i))
					{
						cntrTable[i-1].num++;
						if (cntrTable[i-1].num == 1)
							cntrTable[i-1].c = c[j];
						else
							cntrTable[i-1].c = null;
					}
				}
			}
		}
		
		for (j = 0; j < rowcolWidth; j++)
		{
			if (1 == cntrTable[j].num)
			{
				board b = cntrTable[j].c.getCellBoard();
				int u = j + 1;
				// System.out.println ("(" + cntrTable[j].c.getCellRow().rowID +","+ cntrTable[j].c.getCellCol().colID +")" + "=" + u + " . ");
				//board.TESTCELLS(b);
				cntrTable[j].c.setValue(j+1,cellState.RESOLVED);
				//board.TESTCELLS(b);
				b.doBoardReduction();
			}
		}
	}
	
	void updatePossibilityList (cell cl, smallMap s)
	{
		try
		{
			cl.updatePossibilityList(s);
		}
		catch (generalException g)
		{
		}
	}
	
	public void updateCellsWhereOnlyOnePossibility()
	{
		int tmp;
		try 
		{
			//check cells if only one is set
			for (int i = 0; i < rowcolWidth; i++)
			{
				if (c[i].state.getState() == cellState.EMPTY)
				{
					tmp = c[i].setValueIfOnlyOneSet();
					if (tmp > 0)
					{
						c[i].setValue(tmp, cellState.RESOLVED);
						c[i].getCellBoard().doBoardReduction();
					}
				}
			}
		}
		catch (generalException g)
		{
		}
	}
}