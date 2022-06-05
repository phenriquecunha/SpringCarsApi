package cunha.pedro.springApi.controller;

import cunha.pedro.springApi.model.Car;
import cunha.pedro.springApi.model.ReturnObj;
import cunha.pedro.springApi.services.PriceService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Data
@Controller
public class PriceController {

    @Autowired
    PriceService prices;

    private String getBrandCode(String brandName){
        List<ReturnObj> brands = this.prices.getBrands();
        String brandCode = null;

        for(ReturnObj brand : brands){
            if(brand.getNome().toUpperCase().contains(brandName.toUpperCase())){
                brandCode = brand.getCodigo();
                break;
            }
        }
        return brandCode;
    }

    private String getModelCode(String brandCode, String modelName){
        List<ReturnObj> models = this.prices.getModels(brandCode).getModelos();
        String modelCode = null;

        for(ReturnObj model: models){
            if(model.getNome().toUpperCase().contains(modelName.toUpperCase())){
                modelCode = model.getCodigo();
                break;
            }
        }
        return modelCode;
    }

    private String getYearCode(String brandCode, String modelCode, String year){
        List<ReturnObj> years = this.prices.getYears(brandCode, modelCode);
        String yearCode = null;

        for(ReturnObj returnObj: years){
            if(returnObj.getNome().toUpperCase().contains(year)){
                yearCode = returnObj.getCodigo();
            }
        }

        return yearCode;
    }

    public String getFipePrice(Car car){
        String brandCode = getBrandCode(car.getBrand());
        String modelCode = getModelCode(brandCode, car.getModel());
        String yearCode = getYearCode(brandCode, modelCode, car.getModelYear());
        String priceStr = this.prices.getPrice(brandCode, modelCode, yearCode).toString();
        return priceStr.substring(7, priceStr.indexOf(',')) + ",00";
    }
}
