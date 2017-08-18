package com.packt.cookbook.ch05_streams;

import com.packt.cookbook.ch05_streams.api.SpeedModel;
import com.packt.cookbook.ch05_streams.api.TrafficUnit;
import com.packt.cookbook.ch05_streams.api.Vehicle;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class TrafficDensity1Test {

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
    public void testTrafficByLane() {
        TrafficDensity1 trafficDensity = new TrafficDensity1();
        double timeSec = 10.0;
        int trafficUnitsNumber = 120;
        double[] speedLimitByLane = {30, 50, 65};
        Integer[] expectedCountByLane = {30, 30, 60};
        Integer[] trafficByLane = trafficDensity.trafficByLane(getTrafficUnitStream2(trafficUnitsNumber), trafficUnitsNumber, timeSec, FactorySpeedModel.getSpeedModel(), speedLimitByLane );
        assertArrayEquals("Assert count of " + trafficUnitsNumber + " vehicles by " + speedLimitByLane.length + " lanes with speed limit "
                        + Arrays.stream(speedLimitByLane).mapToObj(Double::toString).collect(Collectors.joining(", ")),
                expectedCountByLane, trafficByLane);
    }

    private Stream<TrafficUnit> getTrafficUnitStream2(int trafficUnitsNumber){
        return IntStream.range(0, trafficUnitsNumber).mapToObj(i -> getTrafficUnit(i));
    }

    private TrafficUnit getTrafficUnit(int i){
        return new TrafficUnit() {
            @Override
            public Vehicle.VehicleType getVehicleType() {
                return (i & 3) == 1 ? Vehicle.VehicleType.CAR
                        : (i & 3) == 2 ? Vehicle.VehicleType.TRUCK : Vehicle.VehicleType.CAB_CREW;
            }
            @Override
            public int getHorsePower() {
                return (i & 3) == 1 ? 246 : (i & 3) == 2 ? 350 : 550;
            }
            @Override
            public int getWeightPounds() {
                return (i & 3) == 1 ? 2000 : (i & 3) == 2 ? 5000 : 3500;
            }
            @Override
            public int getPayloadPounds() {
                return (i & 3) == 1 ? 300 : (i & 3) == 2 ? 3000 : 2500;
            }
            @Override
            public int getPassengersCount() {
                return (i & 3) == 1 ? 4 : (i & 3) == 2 ? 2 : 4;
            }
            @Override
            public double getSpeedLimitMph() { return 55; }
            @Override
            public double getTraction() { return (i & 3) == 1 ? 0.2 : (i & 3) == 2 ? 0.1 : 0.4; }
            @Override
            public SpeedModel.RoadCondition getRoadCondition() { return null; }
            @Override
            public SpeedModel.TireCondition getTireCondition() { return null; }
            @Override
            public int getTemperature() { return 0; }
        };
    }



}
