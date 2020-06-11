package com.weather.Capstone.controllers;

public class Temperature

{

    private double degreesKelvin;

    public Temperature()
    {
        degreesKelvin = 0;
    }

    public void setCelsius(double degreesCelsius)
    {
        degreesKelvin = degreesCelsius + 273.16;
    }

    public double getCelsius()
    {
        double c = degreesKelvin - 273.16;
        return c;
    }

    public void setFahrenheit(double degreesFahrenheit)
    {
        degreesKelvin = (5d/9 * (degreesFahrenheit - 32) + 273);
    }

    public double getFahrenheit()
    {
        double f = (((degreesKelvin - 273) * 9d/5) + 32);
        return f;
    }

}
