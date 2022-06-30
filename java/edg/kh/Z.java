import javax.swing.*;
import java.awt.*;

public class Z
{
  public static void main(String[] args)
  {
    EventQueue.invokeLater(new Runnable()
      {
        public void run()
        {
          JFrame f=new ZF();
          f.setTitle("shapetest");
          f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          f.setVisible(true);
        }
      }
    );
  }
}

