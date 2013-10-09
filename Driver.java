import javax.swing.JFrame;
import javax.swing.UIManager;

public class Driver
{
	public static boolean done = false;
	public static void main(String[] args)
	{
		GUI gui = new GUI(new MouseGrapher(System.currentTimeMillis()));
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e)
		{
			//nothing
		}
		UIManager.put("swing.boldMetal",Boolean.FALSE);
		JFrame frame = new JFrame("Mouse Capture");
		frame.add(gui);
		frame.setSize(300,200);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
