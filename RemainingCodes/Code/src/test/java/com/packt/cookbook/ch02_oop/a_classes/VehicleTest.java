package com.packt.cookbook.ch02_oop.a_classes;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class VehicleTest {

    @Test
    public void testGetSpeedMph(){
        //System.out.println("Hello! I am your first test method!");
        //assertEquals("Assert Hello length:", 5, "Hello".length());
        double timeSec = 10.0;
        int engineHorsePower = 246;
        int vehicleWeightPounds = 4000;

        Engine engine = new Engine();
        engine.setHorsePower(engineHorsePower);

        Vehicle vehicle = new Vehicle(vehicleWeightPounds, engine);
        double speed = vehicle.getSpeedMph(timeSec);
        assertEquals("Assert vehicle (" + engineHorsePower + " hp, " + vehicleWeightPounds
                + " lb) speed in " + timeSec + " sec: ", 117, speed, 0.001 * speed);
    }

    @Test
    public void testGetSpeedMphException(){
        int vehicleWeightPounds = 4000;
        Engine engine = null;
        try {
            Vehicle vehicle = new Vehicle(vehicleWeightPounds, engine);
            fail("Exception was not thrown");
        } catch (RuntimeException ex) {}
    }

}
