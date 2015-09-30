package ua.shevchenko;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

public class GamePanel extends JPanel
{
    private int cellSize = 10;
    private Set<Cell> aliveCells = new HashSet<Cell>();
    private boolean showGrid = false;
    private static final long serialVersionUID = 6669085374807339147L;
    private int x;
    private int y;
    public GamePanel()
    {
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent event)
            {
                int row = event.getX() / cellSize;
                int col = event.getY() / cellSize;
                Cell cell = new Cell(row, col);
                if (aliveCells.contains(cell))
                {
                    aliveCells.remove(cell);
                }
                else
                {
                    aliveCells.add(cell);
                }
                repaint(); //принудительное перерисовывание апплета
            }
        });
        addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseDragged(MouseEvent event)  //перетаскивание мышью
            {
                int row = event.getX() / cellSize;
                int col = event.getY() / cellSize;
                if (row == x && col == y)
                {
                    return;
                }
                Cell cell = new Cell(row, col);
                aliveCells.add(cell);
                repaint();
            }
        });
    }

    @Override
    public void paint(Graphics g)    //прорисовка клеточки
    {
        Graphics2D g2 = (Graphics2D) g;
        Rectangle bounds = getBounds();
        g2.setColor(g2.getBackground());
        g2.fillRect((int) bounds.getMinX(), (int) bounds.getMinY(),
                (int) bounds.getMaxX(), (int) bounds.getMaxY());
        g2.setColor(Color.GRAY);
        if (showGrid)
        {
            for (int x = 0; x < bounds.getMaxX(); x += cellSize)
            {
                g2.drawLine(x, (int) bounds.getMinY(), x,
                        (int) bounds.getMaxY());
            }
            for (int y = 0; y < bounds.getMaxY(); y += cellSize)
            {
                g2.drawLine((int) bounds.getMinX(), y, (int) bounds.getMaxX(),
                        y);
            }
        }

        g2.setColor(Color.GREEN);
        for (Cell cell : aliveCells)
        {
            g2.fillOval(cell.getRow() * cellSize + 1, cell.getCol() * cellSize
                    + 1, cellSize - 2, cellSize - 2);
        }
    }

    public int run()
    {
        Set<Cell> bufferAliveCells = new HashSet<Cell>();
        for (Cell cell : aliveCells)
        {
            bufferAliveCells.addAll(getBuildAliveCells(cell));
        }
        aliveCells = bufferAliveCells;
        repaint();
        validate();
        return aliveCells.size();
    }

    public Set<Cell> getBuildAliveCells(Cell aCell)     //добавление новой клетки
    {
        int x = aCell.getRow();
        int y = aCell.getCol();
        Set<Cell> bufferAliveCells = new HashSet<Cell>();
        for (int i = x - 1; i < x + 2 * 1; i += 1)
        {
            for (int j = y - 1; j < y + 2; j += 1)
            {
                Cell blankCell = new Cell(i, j); //пустая клетка 
                int numberNeighbors = numberOfNeighbors(blankCell);
                if (!(aliveCells.contains(blankCell)) && numberNeighbors == 3)
                {
                    bufferAliveCells.add(blankCell);
                }
                else if ((i == x && j == y) && numberNeighbors == 2
                        || numberNeighbors == 3)
                {
                    bufferAliveCells.add(blankCell);
                }
            }
        }
        return bufferAliveCells;
    }

    public int numberOfNeighbors(Cell aCell)
    {
        int x = aCell.getRow();
        int y = aCell.getCol();
        int counter = 0;
        for (int i = x - 1; i < x + 2; i++)
        {
            for (int j = y - 1; j < y + 2; j++)
            {
                Cell tempCell = new Cell(i, j);
                if (aliveCells.contains(tempCell))
                {
                    if (!(i == x && j == y))
                    {
                        counter++;
                    }
                }
            }
        }
        return counter;
    }

    public void setCellSize(int cellSize)
    {
        this.cellSize = cellSize;
    }

    public void clear()//метод удаления всех элементов коллекции
    
    {
        aliveCells.clear();   
        repaint();
    }

    public void showGrid(boolean selected)
    {
        this.showGrid = selected;
        repaint();
    }

    public void setGridSize(int value)
    {
        cellSize = value;
        repaint();
    }
}