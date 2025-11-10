package resApi.api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import resApi.api.entities.Courses;
import resApi.api.repositories.CoursesRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoursesService {
    private final CoursesRepository coursesRepository;

    public List<Courses> getAll(){
        return coursesRepository.findAll();
    }

    public Courses addCourse(Courses course){
        return coursesRepository.save(course);
    }

    public boolean remove(Long id){
        try {
            coursesRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public Courses update(Courses course, Long id){
        Courses updatedCourse = coursesRepository.findById(id).orElseThrow();

        updatedCourse.setName(course.getName());
        updatedCourse.setDescription(course.getDescription());
        updatedCourse.setPrice(course.getPrice());

        coursesRepository.save(updatedCourse);
        return updatedCourse;
    }

}

