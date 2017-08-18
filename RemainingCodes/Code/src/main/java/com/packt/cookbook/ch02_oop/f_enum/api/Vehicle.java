package com.packt.cookbook.ch02_oop.f_enum.api;

public interface Vehicle {
    void setSpeedModel(SpeedModel speedModel);
    double getSpeedMph(double timeSec);
    int getWeightPounds();
}
