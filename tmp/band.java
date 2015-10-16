public class band 
{
	protected region [] regionInBand;
	protected int rowcolWidth_root;
	protected int reg_index;
	band (int rowcolWidth) throws generalException 
	{
		if (board.isNumPerfectSquare(rowcolWidth))
		{
			rowcolWidth_root = board.getSquareRoot(rowcolWidth);
			regionInBand = new region[rowcolWidth_root];
			reg_index = 0;
		}
		else 
			throw new generalException ("band::band rowcolWidth not perfect square.");
	}
	public void addRegion(region r)
	{
		if (reg_index < rowcolWidth_root)
		{
			regionInBand[reg_index] = r;
			reg_index++;
		}
	}
	
	void applyBandRegionReduction ()
	{
		int i;
		//for each row in band, apply constraint
		for (i =0; i < rowcolWidth_root; i++)
		{
			regionInBand[i].applyRegionReduction();
		}
	}
};