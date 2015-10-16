//package ;


//Utility class smallMap that will be used to keep track of 
//possibilities of a cell

public class smallMap 
{
// instance members
  private int map;
  private int  mapSize;                //futuristic number that keeps track of the number of cells per row/col/region [9 for standard sudoku]
  // instance methods
  private static int [] numOf1sLookUpTable;
  private static boolean loadNumOf1sLookUpTable = false;
  private static int PowerOf2 = 1;
  // private static int [] whereIs_1_SetLookUpTable;
  
  

  smallMap(int numCells, boolean init)              // constructor
  {  
  
	//if running the first time
	if (!smallMap.loadNumOf1sLookUpTable)
	{
		loadNumOf1sLkUptable(numCells);
		smallMap.loadNumOf1sLookUpTable = true;
	}  
  
    mapSize = numCells;
	if (init)
		this.allSet();
	else
		this.allunSet();
  }
  
  public static int lookUpNumberOf1s(int n)
  {
	if ( (n >= 0) && (n < smallMap.PowerOf2))
		return smallMap.numOf1sLookUpTable[n];
	else
		return 0;
  }
  
  public static void copySmallMap(smallMap src, smallMap dest)
  {
	dest.map = src.map;
	dest.mapSize = src.mapSize;
  }
  
  public boolean isEmpty()
  {
	if (map == 0) return true;
	else return false;
  }

  public void  allSet()               // set all maplets to true
  {
	map = PowerOf2-1;
  }
  
  public void  allunSet()               // set all maplets to true
  {
	map = 0;
  }
  
  public void  set(int index)
  {
	int i = 1;
	if (index <= mapSize)
		i = i<<(index-1);
	else
		i = 0;
	map = map | i;
  }

  public void  unset(int index)
  {
	int i = 1;
	if (index <= mapSize)
	{
		i = i<<(index-1); //left shift
		i=~i;  //one's cmplement
	}
	else
		i = 0xFFFF;
	map = map & i;
  }
  
  public boolean   get(int index)
  {
	int i = 1;
	if (index <= mapSize)
		i = i<<(index-1);
	else
		i = 0;
	if( (map & i ) == 0)
		return false;
	else
		return true;
  }

  public boolean isOnlyOneSet()
  {
	if (lookUpNumberOf1s(map) == 1) return true;
	else return false;
  }
  
  
  // for this cell, this method will return the only number set if any 
  // if more than one number is set, this method will return -1
  // if no numbers are set, this method will retrn 0
  public int getOnlyOneSet()
  {
  	boolean b = false;
	int num = 0;
	int test = 0x1;	
	for (int i=0; i < mapSize; i++)
	{
		int x = test<<i;
		//System.out.println ("x = " + x);
		//DUMP_MAP();
		if (((map & x) > 0) && (b==false))
		{
			num = i+1;
			b = true;
			continue;
		}
		if (((map & x) > 0) && (b==true))
			return -1;
	}
	return num;
  }
  
  private static void loadNumOf1sLkUptable(int numCells)
  {
	//System.out.println("Test once.");
	int n = 0;
	int i;
	//compute power of 2 of numCells
	for (i = 0; i <numCells; i++)
		PowerOf2 *=2; 
	
	smallMap.numOf1sLookUpTable = new int[PowerOf2];
	smallMap.numOf1sLookUpTable[0] = 0;
	for (n = 1; n < PowerOf2; n++)
	{
		int cntr = 0;    //temp var to hold number of 1s in a number
		int test = 0x1;  //tmp var to rotate 1
		for (i = 0; i < numCells; i++) /* there are 16 bits in a int */
		{
			int x = test<<i;
			if ((n & x) > 0)
				cntr++;
		}
		smallMap.numOf1sLookUpTable[n] = cntr;
		//System.out.println(n + "  " + cntr);
	}
  }
  
   // public static void loadWhereIsOneSetLkUpTable(int numCells)
   // {
		// smallMap.whereIs_1_SetLookUpTable = new int[numCells];
		
   // }
  
  
  
  public void DUMP_MAP()
  {
	int i;
	for (i = 0; i < mapSize; i++)
	{
		if ( (0x1<<i & map) > 0)
			System.out.print(i+1 + " ");
	}
	System.out.println(" ");
  }
};
