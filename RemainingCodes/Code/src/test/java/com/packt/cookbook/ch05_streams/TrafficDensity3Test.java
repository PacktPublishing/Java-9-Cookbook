package com.packt.cookbook.ch05_streams;

import com.packt.cookbook.ch05_streams.api.TrafficUnit;
import com.packt.cookbook.ch05_streams.api.Vehicle;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class TrafficDensity3Test {

    @Test
    public void testSpeedModel(){
        double timeSec = 10.0;
        int engineHorsePower = 246;
        int vehicleWeightPounds = 4000;
        double speed = FactorySpeedModel.getSpeedModel().getSpeedMph(timeSec, vehicleWeightPounds, engineHorsePower);
        assertEquals("Assert vehicle (" + engineHorsePower + " hp, " + vehicleWeightPounds
                + " lb) speed in " + timeSec + " sec: ", 117, speed, 0.001 * speed);
    }

    @Test
    public void testCalcSpeed(){
        double timeSec = 10.0;
        TrafficDensity3 trafficDensity = new TrafficDensity3();

        Vehicle vehicle = Mockito.mock(Vehicle.class);
        Mockito.when(vehicle.getSpeedMph(timeSec)).thenReturn(100d);

        double traction = 0.2;
        double speed = trafficDensity.calcSpeed(vehicle, traction, timeSec);
        assertEquals("Assert speed (traction=" + traction
                + ") in " + timeSec + " sec: ", 20, speed, 0.001 * speed);
    }

    @Test
    public void testCalcLaneNumber() {
        double[] speeds = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        double[] speedLimitByLane = {4.5, 8.5, 12.5};
        int[] expectedLaneNumber = {1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3};

        TrafficDensityTestCalcLaneNumber trafficDensity = new TrafficDensityTestCalcLaneNumber();
        for(int i = 0; i < speeds.length; i++){
            int ln = trafficDensity.calcLaneNumber(speedLimitByLane.length, speedLimitByLane, speeds[i]);
            assertEquals("Assert lane number of speed " + speeds + " with speed limit "
                            + Arrays.stream(speedLimitByLane).mapToObj(Double::toString).collect(Collectors.joining(", ")),
                    expectedLaneNumber[i], ln);
        }
    }

    @Test
    public void testCalcLaneNumber2() {
        int[] count ={0};
        double[] speeds = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        Integer[] expectedCountByLane = {4, 4, 4};

        TrafficDensity3 trafficDensity = new TrafficDensity3(){
            @Override
            protected double calcSpeed(Vehicle vehicle, double traction, double timeSec) {
                return speeds[count[0]++];
            }
        };
        double timeSec = 10.0;
        int trafficUnitsNumber = speeds.length;
        double[] speedLimitByLane = {4.5, 8.5, 12.5};
        Integer[] trafficByLane = trafficDensity.trafficByLane(getTrafficUnitStream(trafficUnitsNumber), trafficUnitsNumber, timeSec, FactorySpeedModel.getSpeedModel(), speedLimitByLane );
        assertArrayEquals("Assert count of " + speeds.length + " vehicles by " + speedLimitByLane.length + " lanes with speed limit "
                        + Arrays.stream(speedLimitByLane).mapToObj(Double::toString).collect(Collectors.joining(", ")),
                expectedCountByLane, trafficByLane);
    }

    private Stream<TrafficUnit> getTrafficUnitStream(int trafficUnitsNumber){
        return FactoryTraffic.getTrafficUnitStream(trafficUnitsNumber, Month.APRIL, DayOfWeek.FRIDAY, 17,
                "USA", "Denver", "Main103S");
    }

    private class TrafficDensityTestCalcLaneNumber extends TrafficDensity3{
        protected int calcLaneNumber(int lanesCount, double[] speedLimitByLane, double speed){
            return super.calcLaneNumber(lanesCount, speedLimitByLane, speed);
        }
    }
}
