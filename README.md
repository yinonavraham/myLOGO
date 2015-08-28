[ ![Download](https://api.bintray.com/packages/yinonavraham/maven/myLOGO-Core/images/download.svg) ](https://bintray.com/yinonavraham/maven/myLOGO-Core/_latestVersion)
[![Build Status](https://travis-ci.org/yinonavraham/myLOGO.svg)](https://travis-ci.org/yinonavraham/myLOGO)

myLOGO
======

myLOGO is a LOGO language tool in Java. It contains the core utilities for working with the LOGO language. This includes:
* LOGO language parser
* Design-time and runtime models
* Basic interfaces for implementing a UI for displaying the result drawing

Also included, a sample UI implementation, based on SWT.

##Supported Actions:
* `FD :D` - Go forward `:D` steps
* `BK :D` - Go bakward `:D` steps
* `RT :A` - Turn right `:A` degrees
* `LT :A` - Turn left `:A` degrees
* `CLEAR` - Clear the canvas and take the turtle home (reset)
* `HOME` - Take the turtle home
* `SET :C` - Set the pen color according to a given index
* `PU` - Pen up
* `PD` - Pen down
* `ST` - Show the turtle
* `HT` - Hide the turtle
* `REPEAT :T :AL` - Repeat `:T` times the actions in the `:AL` action list
* `ED :NAME :ARGS :AL END` - Define a new action named `:NAME` which gets the arguments defined by `:ARGS` and executes the actions listed in `:AL`

###Examples
* Draw a square with size 50

 `REPEAT 4 [FD 50 RT 90]`
* Define a new action to draw a square

 `ED SQUARE :S REPEAT 4 [FD :S RT 90]`
 
##API Usage

```java
ActionsRegistry actionsRegistry = new ActionsRegistry();
MyCustomTurtleCanvas turtleCanvas = new MyCustomTurtleCanvas();
LogoParser parser = new LogoParser(actionsRegistry);
ParserResult parserResult = parser.parse(text);
List<Action> actions = parserResult.getActions();
RuntimeActionsExecutor executor = new RuntimeActionsExecutor(turtleCanvas);
for (Action action : actions)
{
   action.accept(executor);
}
```

Above is a simplified example for using the API. In the example, the only custom class that was implemented for the example is `MyCustomTurtleCanvas` which implements the `TurtleActions` interface. This custom implementation is the one that is responsible for drawing according to given actions defined by the interface (the basic actions of the language).

The `ActionsRegistry` is responsible for storing the supported actions. The registry is initialized with the basic LOGO actions and can be enhanced by registering new actions, either by parsing text (as in the example) or programmatically.

The `RuntimeActionsExecutor` is responsible for executing the runtime actions on the given canvas. The executor and the actions are designed based on the Visitor design pattern.

##Build Dependency
This library is available in [Bintray.com](https://bintray.com/yinonavraham/maven/myLOGO-Core). 

In order to have it in your dependencies, use the following maven repository url (jcenter) and artifact coordinates:
```
url:     http://jcenter.bintray.com/
group:   ynn.mylogo
name:    mylogo-core
version: 1.0.1
```
(Note to set the version to the version you desire, see latest above)
