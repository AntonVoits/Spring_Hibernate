package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.CarService;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);
        CarService carService = context.getBean(CarService.class);
        Car car1 = new Car("BMW", 3);
        Car car2 = new Car("Mercedes", 126);

        userService.add(new User("User1", "Lastname1", "user1@mail.ru"));
        userService.add(new User("User2", "Lastname2", "user2@mail.ru"));
        userService.add(new User("User3", "Lastname3", "user3@mail.ru"));
        userService.add(new User("User4", "Lastname4", "user4@mail.ru"));

        userService.add(new User("User5", "Lastname5", "user5@mail.ru", car1));
        userService.add(new User("User6", "Lastname6", "user6@mail.ru", car2));

        List<User> users = userService.listUsers();
        for (User user : users) {
            Optional<Car> optional = Optional.ofNullable(user.getCar());
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            if (optional.isPresent()) {
                System.out.println("Car Model = " + optional.get().getModel());
                System.out.println("Car Series = " + optional.get().getSeries());
            } else {
                System.out.println("Car Model = " + optional.orElse(null));
                System.out.println("Car Series = " + optional.orElse(null));
            }
            System.out.println();
        }

        System.out.println(carService.getUserByCarModelAndSeries(car1));
        System.out.println(carService.getUserByCarModelAndSeries(car2));

        context.close();
    }
}
