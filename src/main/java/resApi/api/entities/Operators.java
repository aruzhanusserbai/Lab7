package resApi.api.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "operators")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Operators {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String surname;

    private String department;

    @ManyToMany
    @JoinTable(
            name = "operators_requests",
            joinColumns = @JoinColumn(name = "operators_id"),
            inverseJoinColumns = @JoinColumn(name = "applicationRequest_id")
    )
    @JsonBackReference
    private List<ApplicationRequest> requests = new ArrayList<>();
}
