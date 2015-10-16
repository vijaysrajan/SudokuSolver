public class colBand extends band
{
	private col [] colsInBand;
	protected int col_index;
	protected int rowcolWidth;
	colBand(int r) throws generalException
	{
		super(r);
		rowcolWidth = r;
		colsInBand = new col[rowcolWidth_root];	
		col_index = 0;
	}
	public void addCol(col r)
	{
		if (col_index < rowcolWidth_root)
		{
			colsInBand[col_index] = r;
			col_index++;
		}
	}

	void applyColBandColsReduction ()
	{
		int i;
		//for each col in band, apply constraint
		for (i =0; i < rowcolWidth_root; i++)
		{
			colsInBand[i].applyColReduction();
		}
	}	
}
