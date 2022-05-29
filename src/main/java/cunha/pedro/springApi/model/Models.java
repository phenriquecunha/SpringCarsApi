package cunha.pedro.springApi.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Models {
    List<ReturnObj> modelos = new ArrayList<>();
    List<ReturnObj> anos = new ArrayList<>();
}
