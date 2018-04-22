package data;

import model.Person;

import java.util.List;

public interface DataAccessor {
    public List<Person> getAllPersonsDepthOne();
    public List<Person> getAllPersonsDepthTwo();
    public List<Person> getAllPersonsDepthThree();
    public List<Person> getAllPersonsDepthFour();
    public List<Person> getAllPersonsDepthFive();
    public String getName();
}
