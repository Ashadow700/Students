package springboot.repository;

import org.springframework.data.repository.CrudRepository;
import springboot.dataEntities.Student;

public interface StudentRepository extends CrudRepository<Student, Integer> {
}
