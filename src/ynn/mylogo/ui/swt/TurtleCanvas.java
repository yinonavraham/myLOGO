package ynn.mylogo.ui.swt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import ynn.mylogo.ui.TurtleActions;
import ynn.mylogo.ui.TurtleColors;
import ynn.mylogo.ui.TurtleState;

public class TurtleCanvas extends Canvas implements TurtleActions
{
	
	private TurtleState _turtle;
	private List<Line> _lines;
	private Color _turtleColor;
	private boolean _suspendRedraw = false;
	private Point _offset = null;

	public TurtleCanvas(Composite parent, int style)
	{
		super(parent, style);
		_turtle = new TurtleState();
		_lines = new ArrayList<Line>();
		_turtleColor = new Color(getDisplay(), 0, 0, 0);
		addPaintListener(new PaintListener() {	
			@Override
			public void paintControl(PaintEvent e)
			{
				paintTurtleCanvas(e.gc);
			}
		});
	}

	@Override
	public void turnRight(int angle)
	{
		_turtle.turnRight(angle);
		if (_turtle.isTurtleShown())
		{
			redraw();
		}
	}

	@Override
	public void turnLeft(int angle)
	{
		_turtle.turnLeft(angle);
		if (_turtle.isTurtleShown())
		{
			redraw();
		}
	}

	@Override
	public void goForward(int distance)
	{
		Point p1 = new Point((int)_turtle.getX(), (int)_turtle.getY());
		_turtle.goForward(distance);
		if (_turtle.isPenDown())
		{
			Line line = new Line();
			line.p1 = p1;
			line.p2 = new Point((int)_turtle.getX(), (int)_turtle.getY());
			line.color = _turtleColor;
			_lines.add(line);
		}
		if (_turtle.isTurtleShown() || _turtle.isPenDown())
		{
			redraw();
		}
	}

	@Override
	public void goBackward(int distance)
	{
		Point p1 = new Point((int)_turtle.getX(), (int)_turtle.getY());
		_turtle.goBackward(distance);
		if (_turtle.isPenDown())
		{
			Line line = new Line();
			line.p1 = p1;
			line.p2 = new Point((int)_turtle.getX(), (int)_turtle.getY());
			line.color = _turtleColor;
			_lines.add(line);
		}
		if (_turtle.isTurtleShown() || _turtle.isPenDown())
		{
			redraw();
		}
	}

	@Override
	public void penDown()
	{
		_turtle.penDown();
		if (_turtle.isTurtleShown())
		{
			redraw();
		}
	}

	@Override
	public void penUp()
	{
		_turtle.penUp();
		if (_turtle.isTurtleShown())
		{
			redraw();
		}
	}

	@Override
	public void setColor(int color)
	{
		TurtleColors tc = TurtleColors.valueOf(color);
		switch (tc)
		{
			case BLACK:
				_turtleColor = getDisplay().getSystemColor(SWT.COLOR_BLACK);
				break;
			case BLUE:
				_turtleColor = getDisplay().getSystemColor(SWT.COLOR_BLUE);
				break;
			case BROWN:
				_turtleColor = new Color(getDisplay(), 102, 51, 0);
				break;
			case CYAN:
				_turtleColor = getDisplay().getSystemColor(SWT.COLOR_CYAN);
				break;
			case GREEN:
				_turtleColor = getDisplay().getSystemColor(SWT.COLOR_GREEN);
				break;
			case MAGENTA:
				_turtleColor = getDisplay().getSystemColor(SWT.COLOR_MAGENTA);
				break;
			case ORANGE:
				_turtleColor = new Color(getDisplay(), 255, 102, 0);
				break;
			case RED:
				_turtleColor = getDisplay().getSystemColor(SWT.COLOR_RED);
				break;
			case WHITE:
				_turtleColor = getDisplay().getSystemColor(SWT.COLOR_WHITE);
				break;
			case YELLOW:
				_turtleColor = getDisplay().getSystemColor(SWT.COLOR_YELLOW);
				break;
			default:
				throw new IllegalArgumentException("Color value is not supported: " + color);
		}
		redraw();
	}

	@Override
	public void showTurtle()
	{
		_turtle.showTurtle();
		redraw();
	}

	@Override
	public void hideTurtle()
	{
		_turtle.hideTurtle();
		redraw();
	}
	
	@Override
	public void goHome()
	{
		_turtle.goHome();
		redraw();
	}

	@Override
	public void clear()
	{
		_turtle.clear();
		_lines.clear();
		redraw();
	}
	
	private void paintTurtle(GC gc, Point p)
	{
		Path path = new Path(getDisplay());
		int x;
		int y;
		x = p.x - (int)Math.round(Math.cos(Math.toRadians(_turtle.getAngle() + 90)) * 5);
		y = p.y - (int)Math.round(Math.sin(Math.toRadians(_turtle.getAngle() + 90)) * 5);
		Point p1 = new Point(x, y);
		x = p.x + (int)Math.round(Math.cos(Math.toRadians(_turtle.getAngle() + 90)) * 5);
		y = p.y + (int)Math.round(Math.sin(Math.toRadians(_turtle.getAngle() + 90)) * 5);
		Point p2 = new Point(x, y);
		x = p.x + (int)Math.round(Math.cos(Math.toRadians(_turtle.getAngle())) * 10);
		y = p.y + (int)Math.round(Math.sin(Math.toRadians(_turtle.getAngle())) * 10);
		Point p3 = new Point(x, y);
		path.moveTo(p1.x, p1.y);
		path.lineTo(p2.x, p2.y);
		path.lineTo(p3.x, p3.y);
		path.lineTo(p1.x, p1.y);
		if (_turtle.isPenDown())
		{
			gc.setBackground(_turtleColor);
		}
		else
		{
			gc.setBackground(getBackground());
		}
		gc.fillPath(path);
		gc.setForeground(new Color(getDisplay(), 0, 0, 0));
		gc.drawPath(path);
	}

	private void paintTurtleCanvas(GC gc)
	{
		Point offset = getOffset();
		for (Line line : _lines)
		{
			int x1 = line.p1.x + offset.x;
			int y1 = line.p1.y + offset.y;
			int x2 = line.p2.x + offset.x;
			int y2 = line.p2.y + offset.y;
			gc.setForeground(line.color);
			gc.drawLine(x1, y1, x2, y2);
		}
		Point currPosition = new Point((int)_turtle.getX()+offset.x, (int)_turtle.getY()+offset.y);
		if (_turtle.isTurtleShown())
		{
			paintTurtle(gc,currPosition);
		}
	}
	
	public void setOffset(Point offset)
	{
		_offset = offset;
		redraw();
	}
	
	private Point getOffset()
	{
		if (_offset == null)
		{
			Rectangle area = getClientArea();
			int offsetX = area.width / 2;
			int offsetY = area.height / 2;
			return new Point(offsetX, offsetY);
		}
		else
		{
			return _offset;
		}
	}
	
	public Rectangle calcBounds() 
	{
		Integer minX = null;
		Integer minY = null;
		Integer maxX = null;
		Integer maxY = null;
		Point offset = getOffset();
		for (Line line : _lines)
		{
			minX = calcMin(minX, line.p1.x+offset.x, line.p2.x+offset.x);
			minY = calcMin(minY, line.p1.y+offset.y, line.p2.y+offset.y);
			maxX = calcMax(maxX, line.p1.x+offset.x, line.p2.x+offset.x);
			maxY = calcMax(maxY, line.p1.y+offset.y, line.p2.y+offset.y); 
		}
		int x = minX == null ? 0 : minX;
		int y = minY == null ? 0 : minY;
		int width = minX == null ? 0 : Math.max(1, maxX-minX);
		int height = minY == null ? 0 : Math.max(1, maxY-minY);
		return new Rectangle(x, y, width, height);
	}
	
	private int calcMin(Integer min, int v1, int v2) 
	{
		// Get the minimum between v1 & v2
		int result = Math.min(v1, v2);
		// If the given minimum is null or greater then the result above - set the minimum to the result  
		result = (min == null || result < min) ? result : min;
		return result;
	}
	
	private int calcMax(Integer max, int v1, int v2) 
	{
		// Get the maximum between v1 & v2
		int result = Math.max(v1, v2);
		// If the given maximum is null or greater then the result above - set the maximum to the result  
		result = (max == null || result > max) ? result : max;
		return result;
	}

	@Override
	public void redraw()
	{
		if (!_suspendRedraw)
		{
			super.redraw();
		}
	}
	
	public void suspendRedraw()
	{
		_suspendRedraw = true;
	}
	
	public void resumeRedraw()
	{
		_suspendRedraw = false;
		redraw();
	}

}
