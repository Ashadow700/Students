package springboot.api;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springboot.dataEntities.Student;
import springboot.repository.StudentRepository;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping(path="/student")
public class Api {

    private final static Logger LOG = LoggerFactory.getLogger(Api.class);

    @Autowired
    private StudentRepository repository;

    @PostMapping(path="/post")
    public ResponseEntity<String> postStudent(@RequestBody @Valid Student student){

        LOG.debug("Received POST student request with for student object with ID " + student.getId());

        if(repository.existsById(student.getId())) {
            String message = "Student with id " + student.getId() + " already exists in database. Student was not posted";
            LOG.debug(message);
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        repository.save(student);

        LOG.debug("Successfully posted student with ID " + student.getId() + " to database");
        return new ResponseEntity<>("Successfully posted student to database", HttpStatus.CREATED);
    }

    @GetMapping(path="/get/{id}")
    public ResponseEntity<Object> getStudent(@PathVariable("id") Integer id) {
        LOG.debug("Received GET request for student object with ID " + id);

        Optional<Student> student = repository.findById(id);
        if(student.isPresent()){
            LOG.debug("Found student with ID" + id);
            return new ResponseEntity<>(student.get(), HttpStatus.OK);

        } else {
            String message = "Student with id " + id + " was not found in the database";
            LOG.debug(message);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path="/get/all")
    public ResponseEntity<Object> getAllStudents() {
        LOG.debug("Received GET all students request");

        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @PutMapping(path="/update")
    public ResponseEntity<String> updateStudent(@RequestBody @Valid Student student){

        LOG.debug("Received Put student request with for student object with ID " + student.getId());

        if(!repository.existsById(student.getId())) {
            String message = "Student with id " + student.getId() + " does not exists in database. Student was not updated";
            LOG.debug(message);
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        repository.save(student);

        String message = "Successfully updated student with ID " + student.getId();
        LOG.debug(message);
        return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(path="/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable("id") Integer id) {
        LOG.debug("Received DELETE request for student with id " + id);

        if(!repository.existsById(id)) {
            String message = "Student with id " + id+ " does not exist in database. Student was not deleted";
            LOG.debug(message);
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        repository.deleteById(id);
        String message = "Student with id " + id + " was successfully deleted from database";
        LOG.debug(message);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping(path="/tea")
    public ResponseEntity<String> tea(){
        return new ResponseEntity<>("I agree. It is time for a tea and fika break", HttpStatus.I_AM_A_TEAPOT);
    }
}