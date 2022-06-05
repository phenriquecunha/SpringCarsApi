package cunha.pedro.springApi.controller;

import java.util.List;
import java.util.Optional;

import cunha.pedro.springApi.model.Car;
import cunha.pedro.springApi.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class CarController {

    @Autowired
    PriceController price;

    @Autowired
    private CarRepository carRepository;

    @GetMapping("/cars")
    public List<Car> getCars(){
        return carRepository.findAll();
    }

    @GetMapping("/car/{id}")
    public Optional<Car> getCarById(@PathVariable Long id){
        return carRepository.findById(id);
    }

    @PostMapping("/car")
    @ResponseStatus(HttpStatus.CREATED)
    public Car addCar(@RequestBody Car car){
        car.setPrice(price.getFipePrice(car));
        return carRepository.save(car);
    }

    @DeleteMapping("/car/{id}")
    public String delCar(@RequestParam Long id){
        carRepository.deleteById(id);
        return "Deleted success!";
    }
}
