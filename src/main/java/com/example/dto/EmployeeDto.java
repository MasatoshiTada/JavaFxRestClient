package com.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class EmployeeDto implements Serializable {
    @JsonProperty("emp_id")
    private Integer empId;
    
    private String name;
    
    @JsonProperty("joined_date")
    private Date joinedDate;
    
    @JsonProperty("department")
    private DepartmentDto departmentDto = new DepartmentDto();

    public String getDepartmentName() {
        return departmentDto.getName();
    }
    
    public String getJoinedDateString() {
        return new SimpleDateFormat("yyyy-MM-dd").format(joinedDate);
    }
    
    /**
     * @return the empId
     */
    public Integer getEmpId() {
        return empId;
    }

    /**
     * @param empId the empId to set
     */
    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the joinedDate
     */
    public Date getJoinedDate() {
        return joinedDate;
    }

    /**
     * @param joinedDate the joinedDate to set
     */
    public void setJoinedDate(Date joinedDate) {
        this.joinedDate = joinedDate;
    }

    /**
     * @return the department
     */
    public DepartmentDto getDepartmentDto() {
        return departmentDto;
    }

    /**
     * @param departmentDto the department to set
     */
    public void setDepartmentDto(DepartmentDto departmentDto) {
        this.departmentDto = departmentDto;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.empId);
        hash = 89 * hash + Objects.hashCode(this.name);
        hash = 89 * hash + Objects.hashCode(this.joinedDate);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EmployeeDto other = (EmployeeDto) obj;
        if (!Objects.equals(this.empId, other.empId)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.joinedDate, other.joinedDate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EmployeeDto{" + "empId=" + empId + ", name=" + name + ", joinedDate=" + joinedDate + ", departmentDto=" + departmentDto + '}';
    }
    
}
