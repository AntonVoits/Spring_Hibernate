package hiber.dao;

import hiber.model.Car;
import hiber.model.User;

public interface CarDao {

    User getUserByCarModelAndSeries(Car car);
}
