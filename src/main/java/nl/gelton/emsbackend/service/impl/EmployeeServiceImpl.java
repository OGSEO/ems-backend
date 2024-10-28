package nl.gelton.emsbackend.service.impl;

import lombok.AllArgsConstructor;
import nl.gelton.emsbackend.dto.EmployeeDto;
import nl.gelton.emsbackend.entity.Employee;
import nl.gelton.emsbackend.exception.ResourceNotFoundException;
import nl.gelton.emsbackend.mapper.EmployeeMapper;
import nl.gelton.emsbackend.repository.EmployeeRepository;
import nl.gelton.emsbackend.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<EmployeeDto> getAllEmployees() {

        List<Employee> employees = employeeRepository.findAll();
//        return employees.stream().map((employee) -> EmployeeMapper.mapToEmployeeDto(employee))
        return employees.stream().map(EmployeeMapper::mapToEmployeeDto)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {

        Employee employeeToUpdate = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee does not exist with id: " + employeeId));
        employeeToUpdate.setFirstName(updatedEmployee.getFirstName());
        employeeToUpdate.setLastName(updatedEmployee.getLastName());
        employeeToUpdate.setEmail(updatedEmployee.getEmail());
        Employee savedEmployee = employeeRepository.save(employeeToUpdate);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public void deleteEmployee(Long employeeId) {

        Employee employeeToDelete = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee does not exist with id: " + employeeId));

        employeeRepository.deleteById(employeeId);
    }
}
