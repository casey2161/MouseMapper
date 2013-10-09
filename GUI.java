import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;


public class GUI extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MouseGrapher m;
	private JButton start;
	private MapperThread thread;
	private JButton stop;
	private JFileChooser chooser;
	private boolean started;
	public GUI(MouseGrapher m)
	{
		super(new BorderLayout());
		this.m = m;
		thread = new MapperThread(m);
		
		JPanel buttonPanel = new JPanel();
		stop = new JButton("Stop");
		stop.addActionListener(this);
		start = new JButton("Start");
		start.addActionListener(this);
		buttonPanel.add(start);
		buttonPanel.add(stop);
		this.add(buttonPanel, BorderLayout.CENTER);
		chooser = new JFileChooser();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==start && !started)
		{
			started = true;
			System.out.println("I have started");
			thread.start();
		}
		else if(e.getSource() == stop && started)
		{
			started = false;
			thread.interrupt();
			m = thread.getM();
			if(chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
			{
				File file = chooser.getSelectedFile();
				BufferedImage image = m.getImage();
				
				try {
					if(file.getAbsolutePath().contains(".png"))
						ImageIO.write(image, "png", file);
					else
						ImageIO.write(image, "png", new File(file.getAbsolutePath() +".png"));
				} catch (IOException ie) {
					// TODO Auto-generated catch block
					ie.printStackTrace();
				}
			}
			
		}
	}
}
