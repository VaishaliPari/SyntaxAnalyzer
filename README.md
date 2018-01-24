# SyntaxAnalyzer
 Syntax Analyzer in Java for a check.
## Syntax for the  check  is as follows,
<P|N> <property|@operation(@operation(property))> [context] comparator [<value|@operation(@operation(property))>]

### property:
Property can be a “.” (dot) separated alphanumeric string

### value:
Value can be any of the values we want to use for comparison. 
This can either be scalar value or an array. Array will be represented as “,” (comma separated) string.

### context:
Context can be all lowercase alphabets only string.

### operatrion:
Operations can be nested.
The Following will be supported,
```
● @count
● @lower 
● @upper
```

### comparator:
The following will be supported,
```
==
>
<
>=
<=
!=
has
not has
in
not in
contains
not contains
match
not match
added
removed
```
## Compile and Execute
Use any IDE to run the check package with the analyser.java file in the src folder.

Using commandline: 
```
javac check/analyser.java
java analyser
```
