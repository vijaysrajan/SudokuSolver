public class rowBand extends band
{
	private row [] rowsInBand;
	protected int row_index;
	protected int rowcolWidth;
	rowBand(int r) throws generalException
	{
		super(r);
		rowcolWidth = r;
		rowsInBand = new row[rowcolWidth_root];	
		row_index = 0;
	}
	public void addRow(row r)
	{
		if (row_index < rowcolWidth_root)
		{
			rowsInBand[row_index] = r;
			row_index++;
		}
	}

	void applyRowBandRowsReduction ()
	{
		int i;
		//for each row in band, apply constraint
		for (i =0; i < rowcolWidth_root; i++)
		{
			rowsInBand[i].applyRowReduction();
		}
	}
}