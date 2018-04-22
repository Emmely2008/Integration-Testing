import data.*;
import junit.helper.Stopwatch;
import logic.Benchmark;

import java.util.HashMap;

public class Main {


    public static void main(String[] args){
        int neo = new DataAccessNeo4J(new DBConnectorNeo4J()).getAllPersonsDepthOne("Sol Linkert").size();
        int pos = 0;
        try {
            pos = new DataAccessPostGreSQL(new DBConnectorPostGres()).getAllPersonsDepthOne("Sol Linkert").size();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(neo);
        System.out.println(pos);


        //Stubbing
        Benchmark bm = new Benchmark(new DataAccessStub(), new Stopwatch());
        String[] methodsToTest = {"getAllPersonsDepthOne","getAllPersonsDepthTwo","getAllPersonsDepthFour","getAllPersonsDepthFive"};
        String[] twentyRandomNodes = {"Dino Kalt","Shirl Wilcock","Dulcie Miyares","Gianna Alan"};
        HashMap resultsStub = bm.getBenchmarkResults(twentyRandomNodes,methodsToTest);
        HashMap[] hm = {resultsStub};
        bm.printHashMapsData(hm, methodsToTest);
    }



}
