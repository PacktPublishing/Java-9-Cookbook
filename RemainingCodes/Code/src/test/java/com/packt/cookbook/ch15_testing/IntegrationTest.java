package com.packt.cookbook.ch15_testing;

import com.packt.cookbook.ch15_testing.api.Vehicle;
import com.packt.cookbook.ch15_testing.factories.DateLocation;
import com.packt.cookbook.ch15_testing.factories.FactorySpeedModel;
import com.packt.cookbook.ch15_testing.factories.FactoryTraffic;
import com.packt.cookbook.ch15_testing.factories.FactoryVehicle;
import com.packt.cookbook.ch15_testing.process.Dispatcher;
import com.packt.cookbook.ch15_testing.process.Process;
import com.packt.cookbook.ch15_testing.utils.DbUtil;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {
    private static DateLocation dateLocation = new DateLocation(Month.APRIL, DayOfWeek.FRIDAY, 17,
            "USA", "Denver", "Main103S");
    private static double timeSec = 10.0;
    private static int trafficUnitsNumber = 1000;
    private static double[] speedLimitByLane = {15, 35, 55};

    public static void main(String... args) {
        //demo1_test_speed_model_with_real_data();
        //demo2_class_level_integration_test();
        demo3_subsystem_level_integration_test();
    }

    private static void demo1_test_speed_model_with_real_data(){
        double timeSec = DbUtil.getTimeSecFromDataCommon();
        FactoryTraffic.readDataFromDb = true;
        TrafficDensity trafficDensity = new TrafficDensity();
        FactoryTraffic.getTrafficUnitStream(dateLocation, 1000).forEach(tu -> {
            Vehicle vehicle = FactoryVehicle.build(tu);
            vehicle.setSpeedModel(FactorySpeedModel.getSpeedModel());
            double speed = trafficDensity.calcSpeed(vehicle, tu.getTraction(), timeSec);
            assertEquals("Assert vehicle (" + tu.getHorsePower() + " hp, " + tu.getWeightPounds()
                    + " lb) speed in " + timeSec + " sec: ", tu.getSpeed(), speed, speed * 0.001);
        });
    }

    private static void demo2_class_level_integration_test() {
        FactoryTraffic.readDataFromDb = true;

        String result = IntStream.rangeClosed(1, speedLimitByLane.length).mapToDouble(i -> {
            AverageSpeed averageSpeed = new AverageSpeed(trafficUnitsNumber, timeSec, dateLocation, speedLimitByLane, i,100);
            ForkJoinPool commonPool = ForkJoinPool.commonPool();
            return commonPool.invoke(averageSpeed);
        }).mapToObj(Double::toString).collect(Collectors.joining(", "));

        String expectedResult = "7.0, 23.0, 41.0";
        String limits = Arrays.stream(speedLimitByLane).mapToObj(Double::toString).collect(Collectors.joining(", "));
        assertEquals("Assert average speeds by " + speedLimitByLane.length + " lanes with speed limit "
                        + limits, expectedResult, result);

        TrafficDensity trafficDensity = new TrafficDensity();
        result = Arrays.stream(trafficDensity.trafficByLane(trafficUnitsNumber, timeSec, dateLocation, speedLimitByLane))
                        .map(Object::toString).collect(Collectors.joining(", "));
        expectedResult = "354, 335, 311";
        assertEquals("Assert vehicle count by " + speedLimitByLane.length + " lanes with speed limit "
                + limits, expectedResult, result);
    }

    private static void demo3_subsystem_level_integration_test() {
        FactoryTraffic.readDataFromDb = true;
        DbUtil.createResultTable();

        Dispatcher.dispatch(trafficUnitsNumber, 10, dateLocation, speedLimitByLane);

        try {
            Thread.sleep(3000l);
        } catch (InterruptedException ex) {}

        String result = DbUtil.selectResult(Process.AVERAGE_SPEED.name());
        String expectedResult = "7.0, 23.0, 41.0";
        String limits = Arrays.stream(speedLimitByLane).mapToObj(Double::toString).collect(Collectors.joining(", "));
        assertEquals("Assert average speeds by " + speedLimitByLane.length + " lanes with speed limit "
                + limits, expectedResult, result);

        result = DbUtil.selectResult(Process.TRAFFIC_DENSITY.name());
        expectedResult = "354, 335, 311";
        assertEquals("Assert vehicle count by " + speedLimitByLane.length + " lanes with speed limit "
                + limits, expectedResult, result);
    }

}
