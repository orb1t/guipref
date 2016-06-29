Zenos Solutions GUI Preferences Library (ZSGPL)
===============================================

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
In your `pom.xml` file, add:

```xml
<dependency>
    <groupId>com.zensols.gui</groupId>
    <artifactId>pref</artifactId>
    <version>0.0.1</version>
</dependency>
```


Getting Started
---------------

```java
public class MyFrame extends com.zensols.gui.pref.ConfigPrefFrame {
...
}
```

License
-------
Lesser GPU
