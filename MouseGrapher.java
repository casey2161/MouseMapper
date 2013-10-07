import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;


public class MouseGrapher {
	private Point2D lastPoint;
	private BufferedImage canvas;
	private long time;
	private long programStart;
	public MouseGrapher(long startTime)
	{
		programStart = startTime;
		canvas = new BufferedImage((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(),(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight(),BufferedImage.TYPE_INT_RGB);
		init();
		time =0;
	}
	
	public void run()
	{
		Point2D p1 = (Point2D) MouseInfo.getPointerInfo().getLocation();
		if(lastPoint!=null)
		{
			updateCanvas(p1,lastPoint);			
		}
		lastPoint = p1;
	}

	private void updateCanvas(Point2D p1, Point2D p2) 
	{
		if(p1.getX()!=p2.getX() && p1.getY()!=p2.getY())
		{
			time -=System.currentTimeMillis();
			time*=-1;
			Graphics2D g2 = canvas.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setStroke(new BasicStroke(.5f));
			Color c = getColor();
			g2.setColor(c);
			g2.draw(new Line2D.Double(p1,p2));
			if(time>1000)
			{
				System.out.println(time);
				Ellipse2D.Double temp = new Ellipse2D.Double(p2.getX()+(time/1000)*10,p2.getY()-(time/1000)*10,(time/1000)*10 + 1,(time/1000)*10 + 1);
				g2.draw(temp);
				g2.fill(temp);
				time=0;
			}
			else
			{
				time =0;
			}
		}
		else if(time==0 && p1.getX()==p2.getX() && p1.getY()==p2.getY())
		{
			time = System.currentTimeMillis();
		}
	}
	
	public void purge()
	{
		if(time>10000)
		{
			time-=System.currentTimeMillis();
			time*=-1;
			Graphics2D g2 = canvas.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			Color c = getColor();
			g2.setColor(c);
			Ellipse2D.Double temp = new Ellipse2D.Double(lastPoint.getX(),lastPoint.getY(),(time/1000)*10 + 1,(time/1000)*10 + 1);
			System.out.println(temp.getX()+ " " + temp.getY() + " " + temp.getHeight());
			g2.draw(temp);
			g2.fill(temp);
			time=0;
		}
	}
	
	private Color getColor() {
		int minutesElapsed = (int) (System.currentTimeMillis() - programStart)/1000;
		switch(minutesElapsed%7)
		{
		case 0: return Color.pink;
		case 1: return Color.blue;
		case 2: return Color.cyan;
		case 3: return Color.orange;
		case 4: return Color.green;
		case 5: return Color.magenta;
		case 6: return Color.red;
		default: return Color.white;
		}
	}

	private void init()
	{
		Graphics2D g2 = canvas.createGraphics();
		g2.setBackground(Color.white);
		g2.clearRect(0, 0, (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(),(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight());
	}
	public BufferedImage getImage()
	{
		return canvas;
	}
}
