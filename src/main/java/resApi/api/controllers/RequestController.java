package resApi.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import resApi.api.dto.RequestDto;
import resApi.api.services.RequestService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/request")
public class RequestController {
    private final RequestService requestService;

    @GetMapping()
    public ResponseEntity<?> home(){
        List<RequestDto> requests = requestService.getAll();

        if(requests.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(requests, HttpStatus.OK);
        }
    }


    @PostMapping("/add")
    public ResponseEntity<?> addRequest(@RequestBody RequestDto request){
        RequestDto createdRequest = requestService.addNewReq(request, request.getCourseId());

        return new ResponseEntity<>(createdRequest, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detailsPage(@PathVariable Long id){
        RequestDto request =  requestService.getRequestById(id);

        if(Objects.isNull(request)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return ResponseEntity.ok(request);
        }
    }

    @PutMapping("/{id}/operators/add")
    public ResponseEntity<?> addOperators(@PathVariable Long id, @RequestBody ArrayList<Long> operatorIds ){
        RequestDto updateRequest = requestService.update(id, operatorIds);

        if(Objects.isNull(updateRequest)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return ResponseEntity.ok(updateRequest);
        }
    }

    @DeleteMapping("/{requestId}/delete/operator/{operatorId}")
    public ResponseEntity<?> deleteOperator(@PathVariable Long operatorId, @PathVariable Long requestId){
        boolean removed = requestService.deleteOperatorId(operatorId, requestId);

        if(removed){
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
