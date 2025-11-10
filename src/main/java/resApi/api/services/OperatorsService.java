package resApi.api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import resApi.api.entities.ApplicationRequest;
import resApi.api.entities.Operators;
import resApi.api.repositories.OperatorsRepository;
import resApi.api.repositories.RequestRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperatorsService {
    private final OperatorsRepository operatorsRepository;
    private final RequestRepository requestRepository;

    public List<Operators> getAll(){
        return operatorsRepository.findAll();
    }

    public List<Operators> getByRequest(Long requestId){
        ApplicationRequest request = requestRepository.findById(requestId).orElseThrow();
        return request.getOperators();
    }

    public boolean deleteOperator(Long id){
        try {
            operatorsRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Operators addOperator(Operators newOperator){
        return operatorsRepository.save(newOperator);
    }

    public Operators update(Operators operator, Long operatorId){
        Operators updateOperator = operatorsRepository.findById(operatorId).orElseThrow();

        updateOperator.setName(operator.getName());
        updateOperator.setSurname(operator.getSurname());
        updateOperator.setDepartment(operator.getDepartment());

        operatorsRepository.save(updateOperator);
        return updateOperator;
    }
}
