package ua.shevchenko;

import javax.swing.SwingUtilities;

public class Run
{
    public static void main(String[] args)
    {//создаеться отдельный поток 
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()             //запуск потока
            {
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
            }
        });
    }
}
