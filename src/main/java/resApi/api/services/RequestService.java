package resApi.api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import resApi.api.dto.RequestDto;
import resApi.api.entities.ApplicationRequest;
import resApi.api.entities.Courses;
import resApi.api.entities.Operators;
import resApi.api.repositories.CoursesRepository;
import resApi.api.repositories.OperatorsRepository;
import resApi.api.repositories.RequestRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;
    private final CoursesRepository coursesRepository;
    private final OperatorsRepository operatorsRepository;


    public List<RequestDto> getAll(){
        List<ApplicationRequest> requests = requestRepository.findAll();
        List<RequestDto> requestDtos = new ArrayList<>();

        requests.forEach(request -> {
            RequestDto requestDto = toDto(request);
            requestDtos.add(requestDto);
        });

        return requestDtos;
    }


    public RequestDto addNewReq(RequestDto newRequest, Long courseId){
        Courses course = coursesRepository.findById(courseId).orElseThrow();
        newRequest.setCourseId(course.getId());

        ApplicationRequest createdReq = toEntity(newRequest);
        requestRepository.save(createdReq);

        return toDto(createdReq);
    }


    public RequestDto getRequestById(Long id){
        ApplicationRequest request = requestRepository.findById(id).orElse(null);

        if(Objects.isNull(request)){
            return null;
        }

        if(request.getOperators().isEmpty()){
            request.setHandled(false);
        }

        return toDto(request);
    }


    public RequestDto update(Long id, ArrayList<Long> operatorIds){
        ApplicationRequest request = requestRepository.findById(id).orElse(null);

        if(Objects.isNull(request)){
            return null;
        }

        List<Operators> operators = new ArrayList<>();

        operatorIds.forEach(operatorId -> operators.add(operatorsRepository.findById(operatorId).orElseThrow()));
        request.setOperators(operators);
        request.setHandled(true);

        operatorIds.forEach(operatorId -> (operatorsRepository.findById(operatorId).orElseThrow()).getRequests().add(request));
        operatorsRepository.saveAll(operators);

        return toDto(request);
    }


    public boolean deleteOperatorId(Long operatorId, Long requestId){
        ApplicationRequest request = requestRepository.findById(requestId).orElseThrow();
        Operators operator = operatorsRepository.findById(operatorId).orElseThrow();

        boolean removed = request.getOperators().remove(operator);
        requestRepository.save(request);

        operator.getRequests().remove(request);
        operatorsRepository.save(operator);

        if(removed){
            return true;
        }else{
            return false;
        }
    }


    public RequestDto toDto(ApplicationRequest request){
        List<Long> operatorIds = new ArrayList<>();
        request.getOperators().forEach(operator -> operatorIds.add(operator.getId()));

        RequestDto requestDto = RequestDto
                .builder()
                .id(request.getId())
                .userName(request.getUserName())
                .commentary(request.getCommentary())
                .phone(request.getPhone())
                .handled(request.isHandled())
                .courseId(request.getCourse().getId())
                .operatorIds(operatorIds)
                .build();

        return requestDto;
    }

    public ApplicationRequest toEntity(RequestDto dto){
        Courses course = coursesRepository.findById(dto.getCourseId()).orElseThrow();

        List<Operators> operators = new ArrayList<>();

        dto.getOperatorIds().forEach(operatorId -> operators.add(operatorsRepository.findById(operatorId).orElseThrow()));

        ApplicationRequest request = new ApplicationRequest();
        request.setId(dto.getId());
        request.setUserName(dto.getUserName());
        request.setCommentary(dto.getCommentary());
        request.setPhone(dto.getPhone());
        request.setCourse(course);
        request.setHandled(dto.isHandled());
        request.setOperators(operators);

        return request;
    }
}
