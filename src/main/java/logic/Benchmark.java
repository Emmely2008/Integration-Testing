package logic;

import data.DataAccessor;
import junit.helper.Stopwatch;

import java.util.HashMap;


public class Benchmark {
    DataAccessor dt;
    Stopwatch timer;


    public Benchmark(DataAccessor dt, Stopwatch timer){
        this.dt = dt;
        this.timer = timer;
    }

    private double doBenchmark(String function){

        this.timer.resetTime();
        if( function.equals("getAllPersonsDepthOne")){
            dt.getAllPersonsDepthOne();
        }
        if( function.equals("getAllPersonsDepthTwo")){
            dt.getAllPersonsDepthTwo();
        }
        if( function.equals("getAllPersonsDepthThree")){
            dt.getAllPersonsDepthThree();
        }
        if( function.equals("getAllPersonsDepthFour")){
            dt.getAllPersonsDepthFour();
        }
        if( function.equals("getAllPersonsDepthFive")){
            dt.getAllPersonsDepthFive();
        }

        double time = timer.elapsedTime();

        return time;

    }

    public HashMap getBenchmarkResults(int runs, String[] methods){

        HashMap measurement = new HashMap();
        for (int i = 0; i < methods.length; i++) {
            MeasurementData measurementData = new MeasurementData();
            measurementData.setType(this.dt.getName());
            for (int j = 0; j < runs; j++) {
                measurementData.addData(this.doBenchmark(methods[i]));
            }
            measurement.put(methods[i], measurementData);
        }
        return measurement;
    }

    public void printHashMapsData(HashMap[] hm, String[] keys){

        for (int j = 0; j < keys.length ; j++) {
            System.out.print(keys[j]+":\t");
                for (int i = 0; i < hm.length ; i++) {

                    MeasurementData ms = (MeasurementData)hm[i].get(keys[j]);
                    System.out.print(ms.getAverage() + "("+ms.getType()+")\t");
                    System.out.print(ms.getMedian() + "("+ms.getType()+")\t");
                }
            System.out.print("\n");
        }
    }
}
