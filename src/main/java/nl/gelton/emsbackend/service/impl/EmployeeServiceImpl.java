package nl.gelton.emsbackend.service.impl;

import lombok.AllArgsConstructor;
import nl.gelton.emsbackend.dto.EmployeeDto;
import nl.gelton.emsbackend.entity.Employee;
import nl.gelton.emsbackend.mapper.EmployeeMapper;
import nl.gelton.emsbackend.repository.EmployeeRepository;
import nl.gelton.emsbackend.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {

        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResolutionException("Employee does not exist with id: " + employeeId));
        return EmployeeMapper.mapToEmployeeDto(employee);
    }
}
