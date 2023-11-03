package music.store.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import music.store.entity.Employee;

@Data
@NoArgsConstructor
public class MusicStoreEmployee {
	
	private Long employeeId;
	
	private String employeeFirstName;
	private String employeeLastName;
	private String employeeEmail;
	private Long employeePhoneNumber;
	
	public MusicStoreEmployee(Employee employee) {
		employeeId = employee.getEmployeeId();
		employeeFirstName = employee.getEmployeeFirstName();
		employeeLastName = employee.getEmployeeLastName();
		employeeEmail = employee.getEmployeeEmail();
		employeePhoneNumber = employee.getEmployeePhoneNumber();
	}
	
}
