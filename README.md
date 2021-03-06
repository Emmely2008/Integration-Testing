# Integration-Testing
*By Emmely Lundberg cph-el69*




### ABOUT THE PROJECT UNDER TEST
This is a Java-project integrating with a Neo4J Graph Database and a PostgresSQL database.
The program is written as part of the assignment from the Database Course [Exercise - Technical Comparison of an SQL and Graph Database](https://github.com/datsoftlyngby/soft2018spring-databases-teaching-material/blob/master/assignments/Neo4J%20Exercise.ipynb) and used in this Test Exercise.
The purpose of the program is to perform and display benchmark test to compare queries performance time between a Graph Database and a Relational database.

I have also chosen to do the integration tests on this project because it is a project that it's not 100% complete/developed 
yet and this is a common scenario we face in integration test where we can use stubs to replace parts of the program/system that hasn't been implemented yet.

The program was written in a Test Driven Development TDD approach.  
The code is made testable  making use of design patterns such as Dependencies Injections and Polymorphism and stubs. 

The program is still under development so this program should be seen as a snapshot of a program under development that uses DDT, unit tests and integrations tests.
[The final code will be pushed to this repository](https://github.com/Emmely2008/Exercise---Technical-Comparison-of-an-SQL-and-Graph-Database-.git)

___________________

### ABOUT THE THREE DIFFERENT STRATEGIES IN INTEGRATION TESTING

Integration Testing is the process of testing the interface between two software units or modules.
It can be done on three ways:
1) Big Bang Approach
2) Top Down Approach
3) Bottom Up Approach


#### Big Bang Approach
Combining all the modules once and verifying the functionality after completion of individual module testing.

Example testing two modules separately and then combining the two.


#### Top Down & Bottom Up
Top down and bottom up are carried out by using dummy modules known as Stubs and Drivers.
These Stubs and Drivers are used to stand-in for missing components to simulate data communication between modules.


#### Top Down Approach 
In Top Down approach, testing takes place from top to bottom.

High-level modules are tested first and then low-level modules and finally integrating the low-level modules to the high level to ensure the system is working as intended.

Stubs are used as a temporary module, if a module is not ready for integration testing.

#### Bottom Up Approach
In the Bottom Up approach, testing takes place from bottom to up.

Lowest level modules are tested first and then high-level modules and finally 
integrating the high-level modules to the low level to ensure the system is 
working as intended.

Stubs comes into the picture when doing integration testing.
While working on integration sometimes we face a situation where some of the functionality are still under the development. 


________________

### MY APPROACH TO INTEGRATION TESTING

#### What I Find Important Areas To Test In This Project

 - It's important to verify that the measured data is collected and presented correctly.
 - Verifying that the result set from the queries are the same for the respective database.
 
 

I have chosen the Bottom Up Approach where I have implemented and unit tested of
 all the components starting with the ones at the underlaying bottom. 
 I have used stubs to make isolated unit tests of components that has external dependencies.
 
*Lowest level modules are tested first and then high-level modules and finally 
integrating the high-level modules to the low level to ensure the system is 
working as intended.*

*Stubs comes into the picture when doing integration testing.
While working on integration sometimes we face a situation where some of the functionality are still under the development.*

#### Bottom Up Approach - Start With Unit Tests

My approach has been:
- Unit test all components at he bottom before integrating test


I have developed the Program in the following way:

##### 1) Testing Queries in the respective database systems:

This is a manual test. I run the query directly with SQL an Cypher to test the result I get. 

Example PostGresSQL:

[![https://gyazo.com/936d73fc12f58991aca745dbf6459ada](https://i.gyazo.com/936d73fc12f58991aca745dbf6459ada.png)](https://gyazo.com/936d73fc12f58991aca745dbf6459ada)

Exaple Neo4J:
[![https://gyazo.com/83d317c15044569c7d08cff963756751](https://i.gyazo.com/83d317c15044569c7d08cff963756751.png)](https://gyazo.com/83d317c15044569c7d08cff963756751)

##### 2) Development of and unit tested the data Layer

This layer consists of the following classes and interfaces:
- DataAccessor - *interface*
- DataAccessNeo4J that *implements DataAccessor*
- DBConnectorPostGres that *implements DataAccessor*
- DataAccessStub that *implements DataAccessor*

- DBConnectorNeo4J - *used by DataAccessNeo4J*
- DBConnectorPostGres - *used by DBConnectorPostGres*

Unit testing this:

```
class DataAccessNeo4JTest {


    @Test
    void getAllPersonsDepthOne() {
        DataAccessNeo4J da = new DataAccessNeo4J(new DBConnectorNeo4J());
        List<Person> list = da.getAllPersonsDepthOne("Sol Linkert");
        assertThat(list.size(), equalTo(8));
    }
	
```

 

```
class DataAccessPostGreSQLTest {

    @Test
    void getAllPersonsDepthOne() throws Exception{
        DataAccessPostGreSQL da = new DataAccessPostGreSQL(new DBConnectorPostGres());
        List<Person> list = da.getAllPersonsDepthOne("Sol Linkert");
        assertThat(list.size(), equalTo(8));

    }
	
```
*The test are scares but I test that the respective databases returns the same number of items from the queries*


##### 3) Developed and unit tested helper Classes

- Stopwatch - *Getting the time elapsed in seconds*
- MeasurementData - *Collects the benchmark data and calculated the median and average from the collected data*

Unit testing this:

```
class StopwatchTest {

    @Test
    void elapsedTime() throws InterruptedException {
        Stopwatch sw = new Stopwatch();
        Thread.sleep(6000);
        double time = sw.elapsedTime();
        assert(time > 5);
    }
    void elapsedTimeTwo() throws InterruptedException {
        Stopwatch sw = new Stopwatch();
        Thread.sleep(6000);
        double time = sw.elapsedTime();
        assert(time < 12);
    }
    void elapsedTimeReset() throws InterruptedException {
        Stopwatch sw = new Stopwatch();
        Thread.sleep(6000);
        sw.resetTime();
        double time = sw.elapsedTime();
        assert(time < 5);
    }
}
```


```
class MeasurementDataTest {

    @Test
    void addData() {
        MeasurementData msd = new MeasurementData();
        msd.addData(10.0);
        assertThat(msd.getAverage(), equalTo(10.0));
        msd.addData(20.0);
        assertThat(msd.getAverage(), equalTo(15.0));
        assertThat(msd.getData().size(), equalTo(2));



    }

    @Test
    void getAverage() {
        MeasurementData msd = new MeasurementData();
        msd.addData(10.0);
        assertThat(msd.getAverage(), equalTo(10.0));
        msd.addData(20.0);
        assertThat(msd.getAverage(), equalTo(15.0));
        msd.addData(30.0);
        assertThat(msd.getAverage(), equalTo(20.0));
        msd.addData(0);
        assertThat(msd.getAverage(), equalTo(15.0));
    }

    @Test
    void getMedian() {
        MeasurementData msd = new MeasurementData();
        msd.addData(10.0);
        assertThat(msd.getMedian(), equalTo(10.0));
        msd.addData(20.0);
        assertThat(msd.getMedian(), equalTo(15.0));
        msd.addData(20.0);
        assertThat(msd.getMedian(), equalTo(20.0));
    }
}
```

##### 4) Developed and unit tested class Benchmark

This is the class that I needed to use stubs to be able to test the logic in isolation. 
I found a logic mistake that was captured in the test.




```

class BenchmarkTest {

    @Test
    void getBenchmarkResultsLengthOfDataMeasurements() {
        Stopwatch timeMock = mock(Stopwatch.class);

        Benchmark bm = new Benchmark(new DataAccessStub(), timeMock);
        when(timeMock.elapsedTime()).thenReturn(2.0);
        String[] methodsToTest = {"getAllPersonsDepthOne","getAllPersonsDepthTwo","getAllPersonsDepthFour","getAllPersonsDepthFive"};
        String[] twentyRandomNodes = {"Dino Kalt","Shirl Wilcock","Dulcie Miyares","Gianna Alan"};

        HashMap resultsStub = bm.getBenchmarkResults(twentyRandomNodes,methodsToTest);

        for (int i = 0; i < methodsToTest.length; i++) {
            MeasurementData msd = (MeasurementData) resultsStub.get(methodsToTest[i]);
            msd.getData();
            assertThat(msd.getData().size(), equalTo(twentyRandomNodes.length));

        }
        HashMap[] hm = {resultsStub};
        bm.printHashMapsData(hm, methodsToTest);

    }
    @Test
    void getBenchmarkResultsMedian() {
        Stopwatch timeMock = mock(Stopwatch.class);

        Benchmark bm = new Benchmark(new DataAccessStub(), timeMock);
        when(timeMock.elapsedTime()).thenReturn(2.0);
        String[] methodsToTest = {"getAllPersonsDepthOne","getAllPersonsDepthTwo","getAllPersonsDepthFour","getAllPersonsDepthFive"};
        String[] twentyRandomNodes = {"Dino Kalt","Shirl Wilcock","Dulcie Miyares","Gianna Alan"};

        HashMap resultsStub = bm.getBenchmarkResults(twentyRandomNodes,methodsToTest);

        for (int i = 0; i < methodsToTest.length; i++) {
            MeasurementData msd = (MeasurementData) resultsStub.get(methodsToTest[i]);
            msd.getData();
            assertThat(msd.getAverage(), equalTo(2.0));

        }
        HashMap[] hm = {resultsStub};
        bm.printHashMapsData(hm, methodsToTest);

    }
    @Test
    void getBenchmarkResultsAverage() {
        Stopwatch timeMock = mock(Stopwatch.class);

        Benchmark bm = new Benchmark(new DataAccessStub(), timeMock);
        when(timeMock.elapsedTime()).thenReturn(2.0);
        String[] methodsToTest = {"getAllPersonsDepthOne","getAllPersonsDepthTwo","getAllPersonsDepthFour","getAllPersonsDepthFive"};
        String[] twentyRandomNodes = {"Dino Kalt","Shirl Wilcock","Dulcie Miyares","Gianna Alan"};

        HashMap resultsStub = bm.getBenchmarkResults(twentyRandomNodes,methodsToTest);

        for (int i = 0; i < methodsToTest.length; i++) {
            MeasurementData msd = (MeasurementData) resultsStub.get(methodsToTest[i]);
            msd.getData();
            assertThat(msd.getMedian(), equalTo(2.0));

        }
        HashMap[] hm = {resultsStub};
        bm.printHashMapsData(hm, methodsToTest);

    }
}

```

##### 5) Integration test with the Benchmark class

In this test no stubs are used so we test the Benchmark class by injecting the real StopWatch and 
the DataAccess that makes queries to the database. 

The test integrates the following classes:


- Stopwatch
- MeasurementData
- DataAccessor: DataAccessNeo4J, DBConnectorPostGres.

We know that each of the classes works because we have unit tested them in isolation.

Even if Integration testing is about testing only two components at the time. I was little pragmatic and 
by over looking that four components are used. I see this integration test mostly between the Benchmark class and the DataAccess.
The other classes are more of helpers and because of the unit test I can be confident that they work as expected. 

```
public class BenchmarkIntegrationTest {


    @Test
    void getBenchmarkResultsNeo4JDataMeasurementsRealTime() {
        Stopwatch time = new Stopwatch();

        Benchmark bm = new Benchmark(new DataAccessNeo4J(new DBConnectorNeo4J()), time);
        String[] methodsToTest = {"getAllPersonsDepthOne"};
        String[] twentyRandomNodes = {"Dino Kalt","Shirl Wilcock","Dulcie Miyares","Gianna Alan"};

        HashMap resultsNeo4J = bm.getBenchmarkResults(twentyRandomNodes,methodsToTest);

        for (int i = 0; i < methodsToTest.length; i++) {
            MeasurementData msd = (MeasurementData) resultsNeo4J.get(methodsToTest[i]);
            msd.getData();
            assertTrue(msd.getAverage() > 0);
            assertTrue(msd.getMedian() > 0);

        }
        HashMap[] hm = {resultsNeo4J};
        bm.printHashMapsData(hm, methodsToTest);

    }
    @Test
    void getBenchmarkResultsPostGresDataMeasurementsRealTime() throws Exception {
        Stopwatch time = new Stopwatch();

        Benchmark bm = new Benchmark(new DataAccessPostGreSQL(new DBConnectorPostGres()), time);
        String[] methodsToTest = {"getAllPersonsDepthOne"};
        String[] twentyRandomNodes = {"Dino Kalt","Shirl Wilcock","Dulcie Miyares","Gianna Alan"};

        HashMap resultsPotGres = bm.getBenchmarkResults(twentyRandomNodes,methodsToTest);

        for (int i = 0; i < methodsToTest.length; i++) {
            MeasurementData msd = (MeasurementData) resultsPotGres.get(methodsToTest[i]);
            msd.getData();
            assertTrue(msd.getAverage() > 0);
            assertTrue(msd.getMedian() > 0);

        }
        HashMap[] hm = {resultsPotGres};
        bm.printHashMapsData(hm, methodsToTest);

    }


}
```
________________


### TEST RESULTS

[![https://gyazo.com/a6cde0100795c6cfd4dca415eb957922](https://i.gyazo.com/a6cde0100795c6cfd4dca415eb957922.png)](https://gyazo.com/a6cde0100795c6cfd4dca415eb957922)