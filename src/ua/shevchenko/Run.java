package ua.shevchenko;

import javax.swing.SwingUtilities;

public class Run
{
    public static void main(String[] args)
    {//���������� ��������� ����� 
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()             //������ ������
            {
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
            }
        });
    }
}
