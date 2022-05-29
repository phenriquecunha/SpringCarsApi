package cunha.pedro.springApi.controller;

import java.util.List;
import java.util.Optional;

import cunha.pedro.springApi.model.ReturnObj;
import cunha.pedro.springApi.model.Car;
import cunha.pedro.springApi.repositories.CarRepository;
import cunha.pedro.springApi.services.Prices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class CarController {

    @Autowired
    Prices prices;

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

        car.setPrice(getFipePrice(car, prices));

        return carRepository.save(car);
    }

    @DeleteMapping("/car/{id}")
    public String delCar(@RequestParam Long id){

        carRepository.deleteById(id);
        return "Deleted success!";
    }

    private static String getBrandCode(String brandName, Prices prices){

        List<ReturnObj> brands = prices.getBrands();
        String brandCode = null;

        for(ReturnObj brand : brands){
            if(brand.getNome().toUpperCase().contains(brandName.toUpperCase())){
                brandCode = brand.getCodigo();
                break;
            }
        }
        return brandCode;
    }

    private static String getModelCode(String brandCode, String modelName, Prices prices){

        List<ReturnObj> models = prices.getModels(brandCode).getModelos();
        String modelCode = null;

        for(ReturnObj model: models){
            if(model.getNome().toUpperCase().contains(modelName.toUpperCase())){
                modelCode = model.getCodigo();
                break;
            }
        }
        return modelCode;
    }

    private static String getYearCode(String brandCode, String modelCode, String year, Prices prices){

        List<ReturnObj> years = prices.getYears(brandCode, modelCode);
        String yearCode = null;

        for(ReturnObj returnObj: years){
            if(returnObj.getNome().toUpperCase().contains(year)){
                yearCode = returnObj.getCodigo();
            }
        }

        return yearCode;
    }

    private static String getFipePrice(Car car, Prices prices){

        String brandCode = getBrandCode(car.getBrand(), prices);
        String modelCode = getModelCode(brandCode, car.getModel(), prices);
        String yearCode = getYearCode(brandCode, modelCode, car.getModelYear(), prices);
        String priceStr = prices.getPrice(brandCode, modelCode, yearCode).toString();
        return priceStr.substring(7, priceStr.indexOf(',')) + ",00";
    }

}
