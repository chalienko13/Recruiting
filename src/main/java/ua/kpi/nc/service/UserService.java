package ua.kpi.nc.service;

import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.ScheduleTimePoint;
import ua.kpi.nc.persistence.model.User;

import java.util.List;
import java.util.Set;

/**
 * Created by Chalienko on 13.04.2016.
 */

public interface UserService {

    User getUserByUsername(String username);

    User getUserByID(Long id);

    boolean isExist(String username);

    boolean insertUser(User user, List<Role> roles);

    int updateUser(User user);

    boolean updateUserWithRole(User user);

    boolean addRole(User user, Role role);

    int deleteRole(User user, Role role);

    List<User> getAllNotScheduleStudents();

    List<User> getActiveStaffByRole(Role role);

    int deleteUser(User user);

    Long insertFinalTimePoint(User user, ScheduleTimePoint scheduleTimePoint);

    int deleteFinalTimePoint(User user, ScheduleTimePoint scheduleTimePoint);

    User getUserByToken(String token);

    Set<User> getAssignedStudents(Long id);

    Set<User> getAllStudents();

    List<User> getStudentsFromToRows(Long fromRows, Long rowsNum, Long sortingCol, boolean increase);

    List<User> getEmployeesFromToRows(Long fromRows, Long rowsNum, Long sortingCol, boolean increase);

    Set<User> getAllEmploees();

    Set<User> getAll();

    List<Integer> getCountUsersOnInterviewDaysForRole(Role role);

    User getAuthorizedUser();

    Long getAllStudentCount();

    Long getAllEmployeeCount();

    Long getAllEmployeeCountFiltered(Long fromRows, Long rowsNum, Long sortingCol, boolean increase, Long idStart, Long idFinish, List<Role> roles, boolean interviewer, boolean notIntrviewer, boolean notEvaluated);

    int deleteToken(Long id);

    List<User> getEmployeesByNameFromToRows(String name);

    List<User> getStudentsByNameFromToRows(String lastName, Long fromRows, Long rowsNum);

    int[] batchUpdate(List<User> users);

    List<User> getFilteredEmployees(Long fromRows, Long rowsNum, Long sortingCol, boolean increase, Long idStart, Long idFinish, List<Role> roles, boolean interviewer, boolean notIntrviewer, boolean notEvaluated);

    Long getUserCount();

    Long getCountActiveEmployees(Long idRole0, Long idRole1);

    Long getCountActiveDoubleRoleEmployee ();

    int disableAllStaff();
    
    List<User> getStudentsWithNotconnectedForms();

    List<User> getUserByTimeAndRole(Long scheduleTimePointId, Long roleId);

    List<String> getNotMarkedInterviwers();

    List<User> getUsersWithoutInterview(Long roleId);

    List<User> getUserWithFinalTimePoint();
}