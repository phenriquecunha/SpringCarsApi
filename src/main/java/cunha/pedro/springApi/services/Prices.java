package cunha.pedro.springApi.services;

import cunha.pedro.springApi.model.Models;
import cunha.pedro.springApi.model.ReturnObj;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "fipe", url = "https://parallelum.com.br/fipe/api/v1/carros/marcas")
public interface Prices {

    @GetMapping()
    List<ReturnObj> getBrands();

    @GetMapping("/{brandCode}/modelos")
    Models getModels(@PathVariable String brandCode);

    @GetMapping("/{brandCode}/modelos/{modelCode}/anos")
    List<ReturnObj> getYears(@PathVariable String brandCode, @PathVariable String modelCode);

    @GetMapping("/{brandCode}/modelos/{modelCode}/anos/{yearCode}")
    Object getPrice(@PathVariable String brandCode, @PathVariable String modelCode, @PathVariable String yearCode);

}
