package resApi.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import resApi.api.entities.Operators;
import resApi.api.repositories.OperatorsRepository;
import resApi.api.services.OperatorsService;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/operators")
public class OperatorsController {
    private final OperatorsService operatorsService;

    @GetMapping("/request/{id}")
    public ResponseEntity<?> getOperators(@PathVariable Long id){
        List<Operators> operators = operatorsService.getByRequest(id);

        if(operators.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(operators, HttpStatus.OK);
        }
    }

    @GetMapping("/getOperators")
    public ResponseEntity<?> getAll(){
        List<Operators> operators = operatorsService.getAll();

        if(operators.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(operators, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteOperator(@PathVariable Long id){
        boolean removed = operatorsService.deleteOperator(id);

        if(removed){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add/operator")
    public ResponseEntity<?> addOperator(@RequestBody Operators operator){
        Operators createdOperator = operatorsService.addOperator(operator);

        if(Objects.isNull(createdOperator)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> updateOperator(@RequestBody Operators operator, @PathVariable Long id){
        Operators updatedOperator = operatorsService.update(operator, id);

        if(Objects.isNull(updatedOperator)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

}
