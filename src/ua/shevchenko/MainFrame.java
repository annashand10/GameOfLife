package ua.shevchenko;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainFrame extends JFrame
{
    private static final long serialVersionUID = -2777662879440115915L;
    private Timer timer;
    private GamePanel gamePanel;      
    private int speed = 250;
    private int iteration =1;
    private int population = 1; //количество клеток
    private JButton startButton;
    public MainFrame()
    {
        setTitle("Game of life");
        setMinimumSize(new Dimension(1000, 600));
        gamePanel = new GamePanel();
        setLocationRelativeTo(null);             //устанавливаем окно в центр
        add(gamePanel, BorderLayout.CENTER);         //расположение компонента по центру
        JPanel buttonPanel = new JPanel(new FlowLayout());
        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener()    //интерфейс класса, который будет обрабатывать события от кнопки  
        {
            @Override 
            public void actionPerformed(ActionEvent paramActionEvent)
            {
                if (timer == null)
                {
                    createTimer();
                    startButton.setText("Stop");
                }
                else
                {
                    timer.stop();
                    timer = null;
                    startButton.setText("Start");
                }
            }
        });
        JButton  stepButton = new JButton("Step");
        stepButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                int population =  gamePanel.run();
                setTitle("Population: " + population + " Iteration: " + iteration++);
                MainFrame.this.population = population; 
            }
        });
        final JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                gamePanel.clear();
                iteration = 1;
                setTitle("Life - start new game");
            }
        });
        buttonPanel.add(startButton);
        buttonPanel.add(stepButton);
        buttonPanel.add(clearButton);
        JCheckBox grid = new JCheckBox("grid");
        grid.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                JCheckBox check = (JCheckBox) arg0.getSource();
                gamePanel.showGrid(check.isSelected());
            }
        });
        buttonPanel.add(grid);
        buttonPanel.add(new JLabel("Speed:"));
        JSpinner spiner = new JSpinner();
        spiner.setValue(500);
        spiner.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent arg0)
            {
                JSpinner spiner = (JSpinner) arg0.getSource();
                speed = (Integer) spiner.getValue();
                createTimer();
            }
        });
        buttonPanel.add(spiner);
        buttonPanel.add(new JLabel("Size:"));
        JSpinner spinerSize = new JSpinner();
        spinerSize.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent arg0)
            {
                JSpinner spinerSize = (JSpinner) arg0.getSource();
                gamePanel.setGridSize((Integer)spinerSize.getValue());
            }
        });
        spinerSize.setValue(20);
        buttonPanel.add(spinerSize);
        add(buttonPanel, BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private void createTimer()
    {
        if (timer != null)
        {
            timer.stop();
        }
        timer = new Timer(speed, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent paramActionEvent)
            {
                int population = gamePanel.run();
                if (population==0 )
                {
                    timer.stop();
                    timer = null;
                    startButton.setText("Start");
                }
                setTitle("Life - Population: " + population + " Iteration: " + iteration++);
                if (MainFrame.this.population == population)
                {
                    setTitle("Life - Population: " + population + " Iteration: " + iteration++ + " Stably");
                }
                MainFrame.this.population = population; 
            }
        });
        timer.start();
    }
}