package ynn.mylogo.ui.images;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class Images
{
	
	private static final Map<String,Image> _registry = new HashMap<String, Image>();
	
	private static Image getPooledImage(String path, boolean isPooled)
	{
		Image img;
		if (_registry.containsKey(path))
		{
			img = _registry.get(path);
		}
		else
		{
			InputStream inputStream = Images.class.getResourceAsStream(path);
			img = new Image(Display.getDefault(), inputStream);
		}
		return img;
	}
	
	public static Image getMyLogoIcon(boolean isPooled)
	{
		return getPooledImage("myLOGO.ico", isPooled);
	}

}
