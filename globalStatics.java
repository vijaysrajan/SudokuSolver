import java.io.FileReader;
import java.io.*;
import java.io.BufferedReader;
import java.lang.String;
import java.io.IOException;

class globalStatics
{
	public static void main (String args[])
	{
		try 
		{
			board b = new board(9);
			try
			{
				populateFromFileRowColumnMethod(args[0], b);
			}
			catch (Exception e)
			{
			}			
			TESTROWS(b);
			System.out.println ("");
			if (true == 	board.solveBoard(b))
			{
				TESTROWS(b);
				System.out.println("");	
			}
			else
			{
				TESTCELLS(b);
				System.out.println ("Could not Solve.");
			}
		}
		catch ( generalException E)
		{
		}
	}
	
	public static void PauseRunningProgram()
	{
		try {
			    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			    String s;
			    in.readLine();
			}
		catch (IOException e)
		{
		}
	}
	
	
	// //	UNIT TESTING CODE
	// //	All the code below is used only for UNIT Testing.
	// //      This code is useful if there are any crashes.
	// //	This code DUMPS/Prints out the rows, columns and regions.
	
	public static void TESTCELLS(board b)
	{
		for (int r = 0; r < board.rowcolWidth; r++)
		{
			for (int c = 0; c < board.rowcolWidth; c++)
			{
				int row = r+1;
				int col = c+1;
				System.out.print ("(" + row + "," + col + "):  " );
				if (b.cellStateGet(r,c) == cellState.RESOLVED) 
				{
					System.out.println("Resolved Value is " + b.cellValueGet(r,c));
				}
				else if  (b.cellStateGet(r,c) == cellState.FIXED) 
				{
					System.out.println("Fixed Value is " + b.cellValueGet(r,c));
				}				
				else if  (b.cellStateGet(r,c) != cellState.FIXED) 
				{
					System.out.print("Possibility List is ");
					b.cellGet(r,c).DUMP_POSSIBILITY_LIST();
				}
			}
		}
	}
	static int cnt = 0;
	public static void TESTROWS(board b)
	{
		for (int k = 0; k < board.rowcolWidth ; k++)
		{
			set.DUMP_SET(b.getRow(k));
		}
		cnt++;
	}
	
	private static void TESTCOLS(board b)
	{
		System.out.println("------ BEGIN COLS ------");
		for (int k = 0; k < board.rowcolWidth ; k++)
		{
			set.DUMP_SET(b.getCol(k));
			System.out.println(" ");
		}
		System.out.println("------ END COLS ------");	
	}
	
	private static void TESTREGIONS(board b)
	{
		System.out.println("------ BEGIN REGIONS ------");
		for (int k = 0; k < board.rowcolWidth ; k++)
		{
			set.DUMP_SET(b.getRegion(k));
			System.out.println(" ");
		}
		System.out.println("------ END REGIONS ------");
	}
	
	// private static void TESTBANDS (board b)
	// {
	// }
	
	// private void populateBoard() throws generalException
	// {
		// int x = 0;
		// int root = board.getSquareRoot(board.rowcolWidth);
		// for (int i = 0; i < root; i++)
		// {
			// for(int j = 0; j < root; j++)
			// {
				// for (int k = 0; k < board.rowcolWidth ; k++)
				// {
					// grid[root*(i)+j][k].setValue(x%board.rowcolWidth + 1, cellState.FIXED);
					// x = x + 1;
				// }
				// x = x + root;
			// }
			// x = x + 1;
		// }
	// }
	// //	END UNIT TEST CODE
		//Static Method populateFromFileRowColumnMethod
	//param: fullfilename & board
	//This function reads the param file assuming that
	//the format of data is 
	//<row> <col> <value>
	//<row> and <col> are values from 1 .. rowcolWidth  example 1..9 in a typical sudoku program BEWARE to adjust row -1 and col -1 for the sake of Java Array grid[][]
	//<value> takes value 1 ... rowcolWidth
	public static void populateFromFileRowColumnMethod(String fullfilename, board b) throws Exception
	{
		String line;    // String that holds current file line
		int count = 0;  // Line number of count 
	
		try 
		{
			//  Sets up a file reader to read the filename passed line one character at a time 
			FileReader input = new FileReader(fullfilename);
			// Filter FileReader through a Buffered read to read a line at a time 
			BufferedReader bufRead = new BufferedReader(input);
			// Read through file one line at time and populate sudoku board
			do 
			{
				int row = -1;
				int col = -1;
				int val = -1;
				int cnt;
				line = bufRead.readLine();
				if (line == null)
					break;
				count++;
				int lineLen = line.length();

				//begin get row from line
				String temp = new String("");
				for ( cnt = 0; cnt < lineLen; cnt++)
				{
					char [] c = new char[1];
					c[0] = line.charAt(cnt);
					if  ( ( c[0] == ' ') && (row == -1) )   // ignore leading spaces in a line
					{
						continue;
					}
					else if ( (c[0] == ' ') && (row != -1) ) // we have reached the end of the numeric string
					{
						break;
					}
					else if (Character.isDigit(c[0])) // if numeric string
					{
						temp = temp.concat(new String(c));
						row = 0;
					}
					else
					{
						System.out.println ("Exception");
						throw new generalException ("Exception Reading from file. Line ::" + line + ":: format incorrect");
					}
				}
				row = Integer.parseInt(temp);
				//end row from line
				//begin get col from line
				temp = new String("");
				for (; cnt < lineLen; cnt++)
				{
					char [] c = new char[1];
					c[0] = line.charAt(cnt);
					if  ( ( c[0] == ' ') && (col == -1) )   // ignore leading spaces in a line
					{
						continue;
					}
					else if ( (c[0] == ' ') && (col != -1) ) // we have reached the end of the numeric string
					{
						break;
					}
					else if (Character.isDigit(c[0])) // if numeric string
					{
						temp = temp.concat(new String (c));
						col = 0;
					}
					else
					{
						throw new generalException ("Exception Reading from file. Line ::" + line + ":: format incorrect");
					}
				}
				col = Integer.parseInt(temp);
				//end get col from line
				//begin get value from line
				temp = new String("");
				for (; cnt < lineLen; cnt++)
				{
					char [] c = new char[1];
					c[0] = line.charAt(cnt);
					if  ( ( c[0] == ' ') && (val == -1) )   // ignore leading spaces in a line
						continue;
					else if ( (c[0] == ' ') && (val != -1) ) // we have reached the end of the numeric string
						break;
					else if (Character.isDigit(c[0])) // if numeric string
					{
						temp = temp.concat(new String (c));
						val = 0;
					}
					else
					{
						throw new generalException ("Exception Reading from file. Line ::" + line + ":: format incorrect");
					}
				}
				val = Integer.parseInt(temp);
				//end get value from line
				try
				{
					//System.out.println ("Row=" + row + "; Col=" + col + "; val=" + val);
					b.cellSet(row-1,col-1,val,cellState.FIXED);
				}
				catch (ArrayIndexOutOfBoundsException e)
				{
					throw new generalException("Array is out of bounds from input file ::" + fullfilename);
				}
			} while (true);// do loop to read input file line by line
			bufRead.close();
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			/* If no file was passed on the command line, this expception is
			generated. A message indicating how to the class should be
			called is displayed */
			System.out.println("Usage: java ReadFile filename\n");          
		}
		catch (IOException e)
		{
			// If another exception is generated, print a stack trace
			e.printStackTrace();
		}
	}
}

