package ynn.mylogo.ui.swt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import ynn.mylogo.model.ActionsRegistry;
import ynn.mylogo.model.designtime.CompositeActionDefinition;
import ynn.mylogo.model.designtime.impl.ArgumentDefinitionImpl;
import ynn.mylogo.model.designtime.impl.CompositeActionDefinitionImpl;
import ynn.mylogo.model.designtime.predefined.impl.GoForwardActionDefinitionImpl;
import ynn.mylogo.model.designtime.predefined.impl.RepeatActionDefinitionImpl;
import ynn.mylogo.model.designtime.predefined.impl.TurnRightActionDefinitionImpl;
import ynn.mylogo.model.runtime.Action;
import ynn.mylogo.model.runtime.NumericValue;
import ynn.mylogo.model.runtime.impl.ActionListImpl;
import ynn.mylogo.model.runtime.impl.NumericValueImpl;
import ynn.mylogo.model.runtime.impl.VariableArgumentValueImpl;
import ynn.mylogo.parser.LogoParser;
import ynn.mylogo.parser.LogoParser.ErrorEntry;
import ynn.mylogo.parser.LogoParser.ParserResult;
import ynn.mylogo.ui.images.Images;

public class MainWindow
{
	
	private final Shell _shell;
	private TurtleCanvas _turtleCanvas;
	private Text _fldCommand;
	private Button _btnGo;
	private Label _lblStatusBarMessage;
	private ActionsRegistry _actionsRegistry;
	
	public MainWindow(Display display)
	{
		_shell = new Shell(display);
		_actionsRegistry = new ActionsRegistry();
		Image img = Images.getMyLogoIcon(true);
		_shell.setImage(img);
		_shell.setText("myLOGO");
		initializeControls();
		layoutControls();
		
		CompositeActionDefinition action = new CompositeActionDefinitionImpl("CUBE");
		action.getArguments().add(new ArgumentDefinitionImpl("S", NumericValue.class));
		Action repeat = new RepeatActionDefinitionImpl().createRuntimeAction();
		repeat.getValues().add(new NumericValueImpl(4));
		List<Action> actions = new ArrayList<Action>();
		Action fd = new GoForwardActionDefinitionImpl().createRuntimeAction();
		fd.getValues().add(new VariableArgumentValueImpl(":S"));
		actions.add(fd);
		Action rt = new TurnRightActionDefinitionImpl().createRuntimeAction();
		rt.getValues().add(new NumericValueImpl(90));
		actions.add(rt);
		repeat.getValues().add(new ActionListImpl(actions));
		action.getActions().add(repeat);
		_actionsRegistry.registerAction(action);
		
		CompositeActionDefinition circle = new CompositeActionDefinitionImpl("CIRCLE");
		circle.getArguments().add(new ArgumentDefinitionImpl("S", NumericValue.class));
		repeat = new RepeatActionDefinitionImpl().createRuntimeAction();
		repeat.getValues().add(new NumericValueImpl(360));
		actions = new ArrayList<Action>();
		fd = new GoForwardActionDefinitionImpl().createRuntimeAction();
		fd.getValues().add(new VariableArgumentValueImpl(":S"));
		actions.add(fd);
		rt = new TurnRightActionDefinitionImpl().createRuntimeAction();
		rt.getValues().add(new NumericValueImpl(1));
		actions.add(rt);
		repeat.getValues().add(new ActionListImpl(actions));
		circle.getActions().add(repeat);
		_actionsRegistry.registerAction(circle);
	}

	private void initializeControls()
	{
		_turtleCanvas = new TurtleCanvas(_shell, SWT.None);
		_turtleCanvas.setBackground(new Color(_shell.getDisplay(), 255, 255, 255));
		_fldCommand = new Text(_shell, SWT.BORDER);
		_fldCommand.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyReleased(KeyEvent e)
			{
				clearStatusMessage();
				if (e.keyCode == SWT.CR)
				{
					doCommand();
					_fldCommand.setText("");
				}
			}
		});
		_btnGo = new Button(_shell, SWT.PUSH);
		_btnGo.setText("Go");
		_btnGo.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				doCommand();
				_fldCommand.setText("");
			}
		});
		_lblStatusBarMessage = new Label(_shell, SWT.BORDER);
	}

	protected void clearStatusMessage()
	{
		setStatusMessage("");
	}
	
	protected void setStatusMessage(String text)
	{
		_lblStatusBarMessage.setText(text);
		_lblStatusBarMessage.setToolTipText(text);
	}

//	protected void doCommand()
//	{
//		Parser parser = new Parser(_actionsRegistry);
//		try
//		{
//			List<Action> actions = parser.parse(_fldCommand.getText());
//			RuntimeActionsExecutor executor = new RuntimeActionsExecutor(_turtleCanvas);
//			for (Action action : actions)
//			{
//				action.accept(executor);
//			}
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//			setStatusMessage(e.getMessage());
//		}
//	}

	protected void doCommand()
	{
		LogoParser parser = new LogoParser(_actionsRegistry);
		try
		{
			String text = _fldCommand.getText();
			ParserResult parserResult = parser.parse(text);
			List<Action> actions = parserResult.getActions();
			RuntimeActionsExecutor executor = new RuntimeActionsExecutor(_turtleCanvas);
			for (Action action : actions)
			{
				action.accept(executor);
			}
			if (parserResult.getErrors().isEmpty()) 
			{
				setStatusMessage("Executed successfully: " + text);	
			}
			else 
			{
				StringBuilder sb = new StringBuilder();
				sb.append("Command has errors: ");
				for (ErrorEntry error : parserResult.getErrors())
				{
					sb.append(error.getMessage()).append(" (").append(error.getToken()).append("); ");
				}
				setStatusMessage(sb.toString());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			setStatusMessage(e.getMessage());
		}
	}

	private void layoutControls()
	{
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		_shell.setLayout(gridLayout);
		GridData data;
		data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = 2;
		_turtleCanvas.setLayoutData(data);
		data = new GridData(GridData.FILL_HORIZONTAL);
		_fldCommand.setLayoutData(data);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		_lblStatusBarMessage.setLayoutData(data);
//		data = new FormData();
//		data.top = new FormAttachment(_turtleCanvas, 0);
//		data.left = new FormAttachment(_fldCommand, 0);
//		data.right = new FormAttachment(100, 0);
//		data.bottom = new FormAttachment(_shell);
//		_btnGo.setLayoutData(data);
	}
	
	public void open()
	{
		_shell.open();
		while (!_shell.isDisposed()) 
		{
			if (!_shell.getDisplay().readAndDispatch())
			{
				_shell.getDisplay().sleep();
			}
		}
	}

}
