package lejos.ev3.tools;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.swing.SwingUtilities;

class ToolStarter
{
	public static void startSwingTool(final Class<?> c, final String[] args)
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ToolStarter.startTool(c, args);
			}
		});
	}
	
	public static void startTool(Class<?> c, String[] args)
	{
		int r;
		try
		{
			Method m = c.getDeclaredMethod("start", String[].class);
			if (m.getReturnType() != int.class)
				throw new NoSuchMethodException("start should return int");
			if (!Modifier.isStatic(m.getModifiers()))
				throw new NoSuchMethodException("start should be static");

			r = ((Integer)m.invoke(null, (Object)args)).intValue();
		}
		catch (Exception e)
		{
			Throwable e2 = e;
			if (e2 instanceof InvocationTargetException)
				e2 = ((InvocationTargetException)e).getTargetException();
			
			e2.printStackTrace(System.err);
			r = 1;
		}
		
		if (r != 0)
		{
			System.exit(r);
		}
	}
}