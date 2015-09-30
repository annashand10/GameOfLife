package ua.shevchenko;


public class Cell
{
    private int row;
    private int col;
    private int evalution;
    public Cell(int row, int col)
    {
        super();
        this.row = row;
        this.col = col;
    }
    public void increment()
    {
        evalution++;
    }
    public int getEvalution()
    {
        return evalution;
    }
    public int getRow()
    {
        return row;
    }
    public int getCol()
    {
        return col;
    }
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + col;
        result = prime * result + row;
        return result;
    }

	
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cell other = (Cell) obj;
        if (col != other.col)
            return false;
        if (row != other.row)
            return false;
        return true;
    }

}
