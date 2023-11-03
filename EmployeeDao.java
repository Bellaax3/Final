package music.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import music.store.entity.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Long> {

}
