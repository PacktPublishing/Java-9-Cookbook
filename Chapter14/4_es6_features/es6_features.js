//Template Strings
print("******* Template strings *******");
var name = "Sanaulla";

print(`My name is ${name}`);

//let, const and block scope
print("******* let, const and block scope *******");
const pi = 3.14;
var language = "Java";
function hello(){
    let name = "Mohamed";
    language = "Javascript";
    print(`From hello(). Hello ${name}`);
    print(`From hello(). Language is ${language}`);
}
print(`Before hello(). Language is ${language}`);
hello();
print(`After hello(). Language is ${language}`);
print(`After hello(). Hello ${name}`);
//pi = 4.5; //this will be error because pi is defined as a constant

//Iterators and for..of loops
print("******* Using for ... of loops *******");
let numbers = [2,4,6,8,10,12];
for ( const number of numbers ){
    print(number);
}
//Map, Set
print("******* Map, Set *******");
var set = new Set();
set.add("elem 1").add("elem 2").add("elem 3").add("elem 1");
print(`Set ${set} has ${set.size} elements`);

var map = new Map();
map.set(1, "elem 1");
map.set(2, "elem 2");
map.set(3, "elem 3");
print(`Map has 1? ${map.has(1)}`);

//Arrow functions
print("******* Arrow functions *******");
numbers = [1,2,3,4,5,6,7,8,9,10];
var evenNumbers = numbers.filter(n => n % 2 == 0);
print(`Even numbers: ${evenNumbers}`);