package resApi.api.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import resApi.api.entities.Courses;
import resApi.api.entities.Operators;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class RequestDto {
    private Long id;
    private String userName;
    private String commentary;
    private String phone;
    private boolean handled = false;
    private Long courseId;
    private List<Long> operatorIds;
}
