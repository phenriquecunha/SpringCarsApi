package cunha.pedro.springApi.repositories;


import cunha.pedro.springApi.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
