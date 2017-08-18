var numbers = java.util.Arrays.asList(12,4,5,67,34,567,32);

var max = java.util.Collections.max(numbers);

print(`Max of ${numbers} is ${max}`);

var javaUtils = new JavaImporter(java.util);
with(javaUtils){
    var date = new Date();
    print(`Todays date is ${date}`);
}

var jSet = Java.type('java.util.HashSet');

var mySet = new jSet();
mySet.add(1);
mySet.add(4);

print(`My set is ${mySet}`);