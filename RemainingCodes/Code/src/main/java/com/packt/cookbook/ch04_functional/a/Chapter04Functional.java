package com.packt.cookbook.ch04_functional.a;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Chapter04Functional {

    public static void main(String... args) {
        demo1();
        demo2();
        demo3();
        demo4();
    }

    public static void demo1() {

        Function<Integer, Double> ourFunc = new Function<Integer, Double>(){
            public Double apply(Integer i){
                return i * 10.0;
            }
        };
        System.out.println(ourFunc.apply(1));

        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                System.out.println("The " + s + " is consumed.");
            }
        };
        consumer.accept("Hello!");

        Supplier<String> supplier = new Supplier<String>() {
            public String get() {
                String res = "Success";
                //Do something and return result – Success or Error.
                return res;
            }
        };
        System.out.println(supplier.get());

        Predicate<Double> pred = new Predicate<Double>() {
            public boolean test(Double num) {
                System.out.println("Test if " + num + " is smaller than 20");
                return num < 20;
            }
        };
        System.out.println(pred.test(10.0) ? "10 is smaller": "10 is bigger");

        IntFunction<String> ifunc = new IntFunction<String>() {
            public String apply(int i) {
                return String.valueOf(i * 10);
            }
        };
        System.out.println(ifunc.apply(1));

        BiFunction<String, Integer, Double> bifunc =
                new BiFunction<String, Integer, Double >() {
                    public Double apply(String s, Integer i) {
                        return (s.length() * 10d)/i;
                    }
                };
        System.out.println(bifunc.andThen(x->11d).apply("1",2));

        BinaryOperator<Integer> binfunc = new BinaryOperator<Integer>(){
            public Integer apply(Integer i, Integer j) {
                return i >= j ? i : j;
            }
        };
        System.out.println(binfunc.apply(1,2));

    }
    public static void demo2() {
        Function<Integer, Double> multiplyBy10 = createMultiplyBy(10d);
        System.out.println(multiplyBy10.apply(1));

        Function<Integer, Double> multiplyBy30 = createMultiplyBy(30d);
        System.out.println(multiplyBy30.apply(1));

        Function<Double,Double> subtract7 = createSubtract(7.0);
        System.out.println(subtract7.apply(10.0));

        Consumer<String> sayHappyToSee = createTalker("Happy to see you again!");
        sayHappyToSee.accept("Hello!");

        Supplier<String> successOrFailure = createResultSupplier();
        System.out.println(successOrFailure.get());

        Predicate<Double> isSmallerThan20 = createIsSmallerThan(20d);
        System.out.println(isSmallerThan20.test(10d));

        Predicate<Double> isBiggerThan18 = createIsBiggerThan(18d);
        System.out.println(isBiggerThan18.test(10d));

        //We can pass the created functions as parameters:
        Supplier<String> compare1By10And20 = applyCompareAndSay(1, multiplyBy10, isSmallerThan20);
        System.out.println(compare1By10And20.get());

        Supplier<String> compare1By30And20 = applyCompareAndSay(1, multiplyBy30, isSmallerThan20);
        System.out.println(compare1By30And20.get());

        //And we can chain functions using the default methods of some Functional Interfaces:

        Supplier<String> compare1By30Less7To20 = applyCompareAndSay(1, multiplyBy30.andThen(subtract7), isSmallerThan20);
        System.out.println(compare1By30Less7To20.get());

        Supplier<String> compare1By30Less7TwiceTo20 = applyCompareAndSay(1, multiplyBy30.andThen(subtract7).andThen(subtract7), isSmallerThan20);
        System.out.println(compare1By30Less7TwiceTo20.get());

        Supplier<String> compare1By30Less7To20_ = applyCompareAndSay(1, subtract7.compose(multiplyBy30), isSmallerThan20);
        System.out.println(compare1By30Less7To20_.get());

        Supplier<String> compare1By30Less7TwiceTo20_ = applyCompareAndSay(1, subtract7.compose(multiplyBy30).andThen(subtract7), isSmallerThan20);
        System.out.println(compare1By30Less7TwiceTo20_.get());

        Consumer<String> askHowAreYou = createTalker("How are you?");
        sayHappyToSee.andThen(askHowAreYou).accept("Hello!");

        Supplier<String> compare1By30Less7TwiceTo18And20 = applyCompareAndSay(1, multiplyBy30.andThen(subtract7).andThen(subtract7),
                isSmallerThan20.and(isBiggerThan18), "between 18 and 20");
        System.out.println(compare1By30Less7TwiceTo18And20.get());

        //Other default methods of Functional Interfaces include:

        Function<Integer, Integer> id = Function.identity();
        System.out.println("Function.identity.apply(4) => " + id.apply(4));
    }

    private static Function<Integer, Double> createMultiplyBy(double num){
        Function<Integer, Double> ourFunc = new Function<Integer, Double>(){
            public Double apply(Integer i){
                return i * num;
            }
        };
        return ourFunc;
    }

    private static Function<Double, Double> createSubtract(double num){
        Function<Double, Double> ourFunc = new Function<Double, Double>(){
            public Double apply(Double dbl){
                return dbl - num;
            }
        };
        return ourFunc;
    }

    private static Consumer<String> createTalker(String value){
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                System.out.println(s + value);
            }
        };
        return consumer;
    }

    private static Supplier<String> createResultSupplier(){
        Supplier<String> supplier = new Supplier<String>() {
            public String get() {
                String res = "Success";
                //Do something and return result – Success or Error.
                return res;
            }
        };
        return supplier;
    }

    private static Predicate<Double> createIsSmallerThan(double limit){
        Predicate<Double> pred = new Predicate<Double>() {
            public boolean test(Double num) {
                System.out.println("Test if " + num + " is smaller than " + limit);
                return num < limit;
            }
        };
        return pred;
    }
    private static Predicate<Double> createIsBiggerThan(double limit){
        Predicate<Double> pred = new Predicate<Double>() {
            public boolean test(Double num) {
                System.out.println("Test if " + num + " is bigger than " + limit);
                return num > limit;
            }
        };
        return pred;
    }
    private static Supplier<String> applyCompareAndSay(int i, Function<Integer, Double> func, Predicate<Double> isSmaller){
        Supplier<String> supplier = new Supplier<String>() {
            public String get() {
                double v = func.apply(i);
                return isSmaller.test(v)? v + " is smaller" : v + " is bigger";
            }
        };
        return supplier;
    }

    private static Supplier<String> applyCompareAndSay(int i, Function<Integer, Double> func, Predicate<Double> compare, String message){
        Supplier<String> supplier = new Supplier<String>() {
            public String get() {
                double v = func.apply(i);
                return (compare.test(v)? v + " is " : v + " is not ") + message;
            }
        };
        return supplier;
    }

    private static Supplier<String> applyCompareAndSayLambda(int i, Function<Integer, Double> func, Predicate<Double> isSmaller){
        return () -> {
            double v = func.apply(i);
            return isSmaller.test(v)? v + " is smaller" : v + " is bigger";
        };
    }

    private static Supplier<String> applyCompareAndSayLambda(int i, Function<Integer, Double> func, Predicate<Double> compare, String message){
        return () -> {
            double v = func.apply(i);
            return (compare.test(v)? v + " is " : v + " is not ") + message;
        };
    }

    public static void demo3() {

        //Introducing Lambdas:
        Function<Integer, Double> ourFunc = i -> i * 10.0;
        System.out.println(ourFunc.apply(1));

        Consumer<String> consumer = s -> System.out.println("The " + s + " is consumed.");
        consumer.accept("Hello!");

        Supplier<String> supplier = () -> {
                String res = "Success";
                //Do something and return result – Success or Error.
                return res;
        };
        System.out.println(supplier.get());

        Predicate<Double> pred = num -> {
            System.out.println("Test if " + num + " is smaller than 20");
                return num < 20;
            };
        System.out.println(pred.test(10.0) ? "10 is smaller": "10 is bigger");

        Function<Integer, Double> multiplyBy10 = i -> i * 10.0;
        System.out.println("1 * 10.0 => " + multiplyBy10.apply(1));

        Function<Integer, Double> multiplyBy30 = i -> i * 30.0;
        System.out.println("1 * 30.0 => " + multiplyBy30.apply(1));

        Function<Double,Double> subtract7 = x -> x - 7.0;
        System.out.println("10.0 - 7.0 => " + subtract7.apply(10.0));

        Consumer<String> sayHappyToSee = s -> System.out.println(s + " Happy to see you again!");
        sayHappyToSee.accept("Hello!");

        Predicate<Double> isSmallerThan20 = x -> x < 20d;
        System.out.println("10.0 is smaller than 20.0 => " + isSmallerThan20.test(10d));

        Predicate<Double> isBiggerThan18 = x -> x > 18d;
        System.out.println("10.0 is smaller than 18.0 => " + isBiggerThan18.test(10d));

        BiFunction<Integer, String, Double> demo = (x,y) -> x * 10d + Double.parseDouble(y);
        System.out.println(demo.apply(1, "100"));

        demo = (x,y) -> {
            double v = x * 10d;
            return v + Double.parseDouble(y);
        };
        System.out.println(demo.apply(1, "100"));

        //We can pass the created functions as parameters:

        Supplier<String> compare1By10And20 = applyCompareAndSay(1, multiplyBy10, isSmallerThan20);
        System.out.println("Compare (1 * 10) and 20 => " + compare1By10And20.get());

        Supplier<String> compare1By30And20 = applyCompareAndSay(1, multiplyBy30, isSmallerThan20);
        System.out.println("Compare (1 * 30) and 20 => " + compare1By30And20.get());

        Supplier<String> compare1By30Less7To20 = applyCompareAndSay(1, multiplyBy30.andThen(subtract7), isSmallerThan20);
        System.out.println("Compare (1 * 30 - 7) and 20 => " + compare1By30Less7To20.get());

        Supplier<String> compare1By30Less7TwiceTo20 = applyCompareAndSay(1, multiplyBy30.andThen(subtract7).andThen(subtract7), isSmallerThan20);
        System.out.println("Compare (1 * 30 - 7 - 7) and 20 => " + compare1By30Less7TwiceTo20.get());

        Supplier<String> compare1By30Less7TwiceTo18And20 = applyCompareAndSay(1, multiplyBy30.andThen(subtract7).andThen(subtract7),
                isSmallerThan20.and(isBiggerThan18), "between 18 and 20");
        System.out.println("Compare (1 * 30 - 7 - 7) and the range 18 to 20 => " + compare1By30Less7TwiceTo18And20.get());


        //Or we can create functions on the fly:


        Supplier<String> compare1By10And20Lambda = applyCompareAndSay(1, x -> x * 10d, x -> x < 20);
        System.out.println("Compare (1 * 10) and 20 => " + compare1By10And20Lambda.get());

        Supplier<String> compare1By30And20Lambda = applyCompareAndSay(1, x -> x * 30d, x -> x < 20);
        System.out.println("Compare (1 * 30) and 20 => " + compare1By30And20Lambda.get());

        //And we can re-write factory methods using Lambda:
        Supplier<String> compare1By30Less7TwiceTo20Lambda = applyCompareAndSayLambda(1, x -> x*30.0-7.0-7.0, x -> x < 20) ;
        System.out.println("Compare (1 * 30 - 7 - 7) and 20 => " + compare1By30Less7TwiceTo20Lambda.get());

        Supplier<String> compare1By30Less7TwiceTo18And20Lambda = applyCompareAndSayLambda(1, x -> x*30.0-7.0-7.0, x -> x < 20 && x > 18, "betwen 18 and 20") ;
        System.out.println("Compare (1 * 30 - 7 - 7) and the range 18 to 20 => " + compare1By30Less7TwiceTo18And20Lambda.get());

    }
    public static void demo4() {
        Demo d = new Demo();
        d.method();
    }

    public static class Demo{
        private String prop = "DemoProperty";
        public void method(){
            Consumer<String> consumer = s -> {
                System.out.println("Lambda accept(" + s + "): this.prop=" + this.prop);
            };
            consumer.accept(this.prop);

            consumer = new Consumer<String>() {
                private String prop = "ConsumerProperty";
                public void accept(String s) {
                    System.out.println("Anonymous accept(" + s + "): this.prop=" + this.prop);
                }
            };
            consumer.accept(this.prop);
        }
    }
}
