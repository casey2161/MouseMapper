
public class MapperThread extends Thread
{
	private MouseGrapher m;
	private boolean done;
	public MapperThread(MouseGrapher m)
	{
		this.m = m;
		done = false;
	}
	
	public void run()
	{
		while(!done)
		{
			m.run();
			
			try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				done = true;
			}
		}
	}
	
	public MouseGrapher getM()
	{
		m.purge();
		System.out.println("recording done");
		return m;		
	}
}
