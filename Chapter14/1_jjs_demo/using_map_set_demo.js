var s = new Set();
s.add("item1").add("item2").add("item1").add("item3");

print (`Set size is ${s.size}`);
print("Set Elements: ")
for ( let e of s){
    print(e)
}
print(`Set has item1? ${s.has("item1")}`);
print(`Set has item4? ${s.has("item4")}`);


var m = new Map();
m.set("key1", "value1");
m.set("key2", "value2");
m.set("key3", "value3");
print(`Map has ${m.size} entries`);
