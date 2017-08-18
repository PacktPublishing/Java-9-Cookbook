package com.packt.cookbook.ch02_oop.a_classes;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class VehicleMockTest {

    @Test
    public void demoMock1(){
        double timeSec = 10.0;
        int engineHorsePower = 246;
        int vehicleWeightPounds = 4000;

        Engine engine = Mockito.mock(Engine.class);
        Mockito.when(engine.getHorsePower()).thenReturn(engineHorsePower);

        Vehicle vehicle = new Vehicle(vehicleWeightPounds, engine);
        double speed = vehicle.getSpeedMph(timeSec);
        assertEquals("Assert vehicle (" + engineHorsePower + " hp, " + vehicleWeightPounds
                + " lb) speed in " + timeSec + " sec: ", 117, speed, 0.001 * speed);
    }

    @Test
    public void demoMock2(){
        Vehicle vehicleMock = Mockito.mock(Vehicle.class);
        Mockito.when(vehicleMock.getSpeedMph(10)).thenReturn(30d);

        double speed = vehicleMock.getSpeedMph(10);
        System.out.println();
        System.out.println(speed);
    }
}
