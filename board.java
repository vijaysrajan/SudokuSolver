public class board 
{
	private 	cell    [][] grid;
	private 	row     [] rows;
	private 	col     [] cols;
	private 	region  [] regions;
	static int 	rowcolWidth;
	
	//the following few variables are used for book keeping 
	private int totalNumOfCellsCreated; // This keeps track of the totalnumber of cells
	private int numOfFixedCells;        //This keeps track of the number of number of fixed cells
	private int numOfEmptyCells;        //This keeps track of the number of number of empty cells
	private int numOfResolvedCells;     //This keeps track of the number of number of resolved cells
	private boolean  somethingResolved;
	private boolean  gameOver;
	private boolean  possibilityListFlag;
	private boolean	 wrongBoardDetected;
	
	public int 		getTotalNumOfCellsCreated() {return totalNumOfCellsCreated;}
	public int 		getNumOfFixedCells ()       {return numOfFixedCells;}
	public int 		getNumOfEmptyCells()        {return numOfEmptyCells;}
	public int 		getNumOfResolvedCells()     {return numOfResolvedCells;}
	public boolean 	getSomethingResolved()  	{return somethingResolved;}
	public void 	resetSomethingResolved()	{somethingResolved = false;}
	public void 	resetPossibilityListFlag()	{possibilityListFlag = false;}
	public boolean 	getPossibilityListFlag()	{return possibilityListFlag;}
	public void 	setPossibilityListFlag(boolean f){possibilityListFlag = f;}
	public void		setWrongBoardDetected(boolean f) {wrongBoardDetected = f;}
	public boolean  getWrongBoardDetected()			 {return wrongBoardDetected;}
	public int		cellStateGet(int row, int col) {return this.grid[row][col].state.getState();}
	public int		cellValueGet(int row, int col) {return this.grid[row][col].getValue();}
	public cell		cellGet(int r,int c){return 	this.grid[r][c];}
	public row		getRow(int r)
	{
		if ( (r >= 0 ) && (r <= board.rowcolWidth))
		{
			return this.rows[r];
		}
		return null;
	}
	public col		getCol(int c)
	{
		if ( (c >= 0 ) && (c <= board.rowcolWidth))
		{
			return this.cols[c];
		}
		return null;
	}
	public region  getRegion(int rg)
	{
		if ( (rg >= 0 ) && (rg <= board.rowcolWidth))
		{
			return this.regions[rg];
		}
		return null;
	}
	public void		cellSet(int r,int c, int value, int state)
	{
		if ( ( (value > 0) && (value <= board.rowcolWidth) ) &&
		     ( (state >= cellState.EMPTY) && (state <= cellState.TEMP) ) &&
			 ( (r >= 0 ) && (r <= board.rowcolWidth)) &&  
			 ( (c >= 0 ) && (c <= board.rowcolWidth)) 
			)
		this.grid[r][c].setValue (value, state);
	}
	
	
	public void 	incNumOfFixedCells ()       {numOfFixedCells++; numOfEmptyCells--;}
	public void 	incNumOfResolvedCells()     
	{
		numOfResolvedCells++; 
		numOfEmptyCells--;
		if (numOfEmptyCells == 0)
			gameOver = true;
	}
	public void 	setSomethingResolved(boolean b)	{somethingResolved = b;}
	public boolean	isGameOver()					{return gameOver;}
	
	//constructor that takes row & col length
	public board (int r) throws generalException
	{
		board.rowcolWidth = r;
		//bookkeeping
		gameOver 				= false;
		somethingResolved 		= false;
		possibilityListFlag 	= false;
		totalNumOfCellsCreated	= r * r;
		numOfFixedCells 		= 0;
		numOfEmptyCells 		= r * r;
		numOfResolvedCells 		= 0;
		wrongBoardDetected		= false; //assume that board is right
		
		// check that this length must be a perfect square	
		if (!isNumPerfectSquare(board.rowcolWidth))
			throw new generalException("board::board ... grid size specified is not perfect square");
		
		// create the array place holder for cells in a 2 dimensional array
		//create empty cells
		createGridOfcells();
		//assign cells to containers row, column & region
		createAndAssignRows();
		createAndAssignCols();
		createAndAssignRegions();			
	}
	
	private static board copyBoard(board b)
	{
		try 
		{
			board copy_b = new board(board.rowcolWidth);
	//		copy_b.totalNumOfCellsCreated = b.totalNumOfCellsCreated;
		//	copy_b.numOfFixedCells = b.numOfFixedCells; //remember to add the resolved cells in b as fixed cells to copy_b
		//	System.out.println("*********numOfFixedCells = " + b.numOfFixedCells);
		//	copy_b.numOfEmptyCells = b.numOfEmptyCells;
			int i,j;
			for (i = 0; i < board.rowcolWidth; i++)  // for each row
			{
				for (j = 0; j < board.rowcolWidth; j++)  // for each column
				{
					int state = b.cellStateGet(i,j);
					if ((state == cellState.FIXED) || 
						(state == cellState.RESOLVED) )
					{
						copy_b.grid[i][j].setValue(b.cellValueGet(i,j),cellState.FIXED);
						//copy_b.numOfFixedCells += b.numOfResolvedCells;
					}
					// else if (state == cellState.EMPTY)
						// cell.copyPossibilityList(b.grid[i][j],copy_b.grid[i][j]);
				}
			}
			return copy_b;
		}
		catch (generalException e)
		{
		}
		return null;
	}
	
	private void createGridOfcells()
	{
		grid = new cell[board.rowcolWidth][board.rowcolWidth];
		int i, j;
		for (i = 0; i < board.rowcolWidth; i++)  // for each row
		{
			for (j = 0; j < board.rowcolWidth; j++)  // for each column
				grid[i][j] = new cell(board.rowcolWidth, this);
		}
		
	}
	
	private void createAndAssignRows() throws generalException
	{
		int r, c;
		rows = new row[board.rowcolWidth];
		for (r = 0; r < board.rowcolWidth; r++)
		{
			rows[r] = new row(board.rowcolWidth, r, this);
			for (c = 0; c < board.rowcolWidth; c++)
			{
				rows[r].addCellRefToRow(grid[r][c]);
				grid[r][c].setCellRow(rows[r]);
			}
		}
	}
	private void createAndAssignCols() throws generalException
	{
		int r, c;
		cols = new col[board.rowcolWidth];
		for (c = 0; c < board.rowcolWidth; c++)
		{
			cols[c] = new col(board.rowcolWidth, c, this);
			for (r = 0; r < board.rowcolWidth; r++)
			{
				cols[c].addCellRefToCol(grid[r][c]);
				grid[r][c].setCellCol(cols[c]);
			}
		}	
	}
	private void createAndAssignRegions() throws generalException
	{
		int root = board.getSquareRoot(board.rowcolWidth);
		int r,c;
		int index = 0;
		regions = new region[board.rowcolWidth];
		for (r = 0; r < board.rowcolWidth; r++)
		{
			for (c = 0; c < board.rowcolWidth; c++)
			{
				if ( (r%root == 0) & (c%root == 0) )
				{
					regions[index] = new region(board.rowcolWidth, index, this);
					index++;
				}
				regions[((int)(r/root)) * root + (int)(c/root)].addCellRefToRegion(grid[r][c]);
				grid[r][c].setCellRegion(regions[((int)(r/root)) * root + (int)(c/root)]);
			}
		}
	}
	
	static boolean isNumPerfectSquare(int num)
	{
		//lets return true for now
		if ((num == 1) || (num == 4) || (num == 9) || (num == 16) )
			return true;
		//else
		return false;
	}

	static int getSquareRoot(int num)  throws generalException 
	{
		if (num == 1)
			return 1;
		else if (num == 4)
			return 2;
		else if (num == 9)
			return 3;
		else if (num == 16)
			return 4;
		else 
			throw new generalException ("static board::getSquareRoot unacceptable board.rowcolWidth");
	}
	
	public void doBoardReduction()
	{
		for (int setCount = 0; setCount < rowcolWidth; setCount++)
		{
			this.rows[setCount].applyRowReduction();
			this.cols[setCount].applyColReduction();
			this.regions[setCount].applyRegionReduction();
		}
	}

	public void doBoardSingleParseResolve() throws generalException
	{
		int setCount = 0;
		do
		{
			resetSomethingResolved();
			doBoardReduction();
			for (setCount = 0; setCount < rowcolWidth; setCount++)
			{
				rows[setCount].doSingleReduction();
				rows[setCount].updateCellsWhereOnlyOnePossibility();
				cols[setCount].doSingleReduction();
				cols[setCount].updateCellsWhereOnlyOnePossibility();
				regions[setCount].doSingleReduction();
				regions[setCount].updateCellsWhereOnlyOnePossibility();					
			}
		} while ((getSomethingResolved()) );
	}
	
	public static boolean solveBoard(board b) throws generalException
	{
		if (b == null) 
			return false;
		b.doBoardReduction();
		b.doBoardSingleParseResolve();
		// System.out.print("totalNumOfCellsCreated = " + b.totalNumOfCellsCreated);
		// System.out.print("numOfEmptyCells = " + b.numOfEmptyCells);
		// System.out.print("; numOfResolvedCells = " + b.numOfResolvedCells);
		// System.out.println("");
		// globalStatics.TESTROWS(b);
		// System.out.print(" <---- numOfFixedCells = " + b.numOfFixedCells);
		// System.out.println("");
		// globalStatics.PauseRunningProgram();

		if (b.getWrongBoardDetected())
			return false;

		if (b.gameOver) 
		{
			System.out.println("Game Over!!!");
			globalStatics.TESTROWS(b);
			System.out.println ("");
			return true;
		}
		// To Do
		// First try only those cells with 2 options
		// If the fist option fails, then the second option can be used
		int r, c, i;
		for (r = 0; r < board.rowcolWidth; r++)
		{
			for (c = 0; c < board.rowcolWidth; c++)
			{
				if (b.cellStateGet(r,c) == cellState.EMPTY)
				{
					cell tmpCell = b.cellGet(r,c);
					if (tmpCell.isCellPossibilityListEmpty())
					{
						return false;				
					}
					for (i = 1; i <= board.rowcolWidth; i++)
					{
						if (tmpCell.isSetPossibilityList(i))
						{
							board bb = board.copyBoard(b);
							bb.cellSet(r,c,i,cellState.RESOLVED);
// globalStatics.TESTROWS(bb);
// //System.out.println("");	
// int row, col;
// row = r+1;
// col = c+1;
// System.out.print("  (" + row + "," + col + ") ="  );	
// tmpCell.DUMP_POSSIBILITY_LIST();
// for (int kk = 0; kk < r*board.rowcolWidth + c; kk++)
// System.out.print(" ");
// System.out.println("|");
							if (bb.gameOver)
							{
								System.out.println("Game Over!!!");
								globalStatics.TESTROWS(bb);
								System.out.println ("");
								return true;
							}
							if (!solveBoard(bb))
							{
								continue;
							}
							else
								return true;
						}
					}
					return false;
				}
			}
		}

		return false;
	}
}