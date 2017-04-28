import random
print "<inputs>"
print "<simulation>"
print "<name>fire</name>"
print "<loop>true</loop>"
print "<description>nota</description>"
print "<toolkit>netlogo</toolkit>"
print "</simulation>"
for i in range(3):
    print "<input id=\""+str(i)+"\""+ " rounds=\"10\"> <element variableName=\"step\"><long>1000</long></element>"
    value=random.uniform(-2147483648,2147483647);
    valueint=int(value)
    print "<element variableName=\"random-seed\"><long>"+str(valueint)+"</long></element>"
    print "<element variableName=\"density\"><double>59</double></element>"
    print "</input>"  
print "</inputs>"
