#!/usr/bin/python
print "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
print "<inputs>"

for i in range(100):
	print "<input id=\""+str(i)+"\"><element variableName=\"step\"><long>315</long></element>"
	print "<element variableName=\"initial-people\"><long>325</long></element>"
	print "<element variableName=\"average-coupling-tendency\"><double>5</double></element>"
	print "<element variableName=\"average-commitment\"><double>5</double></element>"
	print "<element variableName=\"average-condom-use\"><double>0</double></element>"
	print "<element variableName=\"average-test-frequency\"><double>0</double></element>"
	print "</input>"

print "</inputs>"

