#include "stdio.h"

short findNumOnes(int n)
{
	short cntr = 0;
	short test = 0x1;
	short i;
	for (i = 0; i < 16; i++) /* there are 16 bits in a short */
	{
		int x = test<<i;
		if ((n & x) > 0) 
               	  cntr++;
	}
	return cntr;
}

int main ()
{
	int j = 0;
	int i;
	for (i = 0; i <=0xFFFF; i++)
	{
		j = findNumOnes(i);
                printf ("%d %d\n", i, j);
	}
}
