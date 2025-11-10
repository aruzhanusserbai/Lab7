package resApi.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import resApi.api.entities.Courses;
import resApi.api.entities.Operators;
import resApi.api.services.CoursesService;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CoursesController {
    private final CoursesService coursesService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getCourses(){
        List<Courses> courses = coursesService.getAll();

        if(courses.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return ResponseEntity.ok(courses);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCourse(Courses course){
        Courses createdCourse = coursesService.addCourse(course);

        if(Objects.isNull(createdCourse)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return ResponseEntity.ok(createdCourse);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id){
        boolean removed = coursesService.remove(id);

        if(removed){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> updateCourse(@RequestBody Courses course, @PathVariable Long id){
        Courses updatedCourse = coursesService.update(course, id);

        if(Objects.isNull(updatedCourse)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

}
