Java/Swing GUI Preferences Library
==================================

This is a small and compact library preferences utility library to (mostly)
keep preferences for Java/Swing components.  The idea is to provide, as
*easily* as possible, a way to "remember" where windows,
frames and other GUI components (i.e. dividers) between runs of an application.

Features
--------

* Preference support for size and locations of `JFrames`.
* Preference support for dividers in `JSplitPane`.
* Registers preferences in your own name space and app to avoid name
  collisions.

Obtaining
---------
In your `pom.xml` file add the below or [look here](https://plandes.github.io/guipref/dependency-info.html):
```xml
<dependency>
    <groupId>com.zensols.gui</groupId>
    <artifactId>pref</artifactId>
    <version>0.0.2</version>
</dependency>
```

Getting Started
---------------
```java
public class MyFrame extends com.zensols.gui.pref.PrefFrame {
    public class MyFrame() {
	    super("optionConfigFrame");
	}
...
}
```

Documentation
-------------
More [documentation](https://plandes.github.io/guipref/):
* [Javadoc](https://plandes.github.io/guipref/apidocs/index.html)
* [Dependencies](https://plandes.github.io/guipref/dependencies.html)

License
-------
Copyright © 2016 Paul Landes

GNU Lesser General Public License, Version 3.0
