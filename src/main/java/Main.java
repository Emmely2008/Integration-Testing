import data.*;
import junit.helper.Stopwatch;
import logic.Benchmark;

import java.util.HashMap;

public class Main {


    public static void main(String[] args){
        int neo = new DataAccessNeo4J(new DBConnectorNeo4J()).getAllPersonsDepthOne().size();
        int pos = 0;
        try {
            pos = new DataAccessPostGreSQL(new DBConnectorPostGres()).getAllPersonsDepthOne().size();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(neo);
        System.out.println(pos);


        //Stubbing
        Benchmark bm = new Benchmark(new DataAccessStub(), new Stopwatch());
        String[] methodsToTest = {"getAllPersonsDepthOne","getAllPersonsDepthTwo","getAllPersonsDepthFour","getAllPersonsDepthFive"};
        HashMap resultsStub = bm.getBenchmarkResults(10,methodsToTest);
        HashMap[] hm = {resultsStub};
        bm.printHashMapsData(hm, methodsToTest);
    }



}
