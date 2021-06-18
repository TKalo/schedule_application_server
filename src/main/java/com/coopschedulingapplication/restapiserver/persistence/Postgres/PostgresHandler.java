package com.coopschedulingapplication.restapiserver.persistence.Postgres;

import com.coopschedulingapplication.restapiserver.Data.Entities.*;
import com.coopschedulingapplication.restapiserver.Data.ValueEntities.ChainCreationValues;
import com.coopschedulingapplication.restapiserver.Data.ValueEntities.DepartmentCreationValues;
import com.coopschedulingapplication.restapiserver.Data.ValueEntities.PersistenceResult;
import com.coopschedulingapplication.restapiserver.Data.ValueEntities.WorkerCreationValues;
import com.coopschedulingapplication.restapiserver.persistence.IPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Component
public class PostgresHandler implements IPersistence {

    PostgresGenericFunctions PGF;
    @Autowired
    public void setJdbcTemplate(PostgresGenericFunctions PGF) {
        this.PGF = PGF;
    }

    WorkerCreationFunctions workerCreation;
    @Autowired
    public void setWorkerCreation(WorkerCreationFunctions workerCreation) {
        this.workerCreation = workerCreation;
    }

    ShiftTemplateFunctions shiftTemplate;
    @Autowired
    public void setShiftTemplate(ShiftTemplateFunctions shiftTemplate) {
        this.shiftTemplate = shiftTemplate;
    }

    ScheduleTemplateFunctions scheduleTemplate;
    @Autowired
    public void setScheduleTemplate(ScheduleTemplateFunctions scheduleTemplate) {
        this.scheduleTemplate = scheduleTemplate;
    }

    AddUserFunctions addUserFunctions;
    @Autowired
    public void setAddUserFunctions(AddUserFunctions addUserFunctions) {
        this.addUserFunctions = addUserFunctions;
    }


    SchedulePreferencesFunctions schedulePreferences;
    @Autowired
    public void setSchedulePreferences(SchedulePreferencesFunctions schedulePreferences) {
        this.schedulePreferences = schedulePreferences;
    }

    UserAuthentication userAuthentication;
    @Autowired
    public void setUserAuthentication(UserAuthentication userAuthentication) {
        this.userAuthentication = userAuthentication;
    }


    @Override
    public WorkerCreationRequest addWorkerCreationRequest(WorkerCreationRequest request, int userId) {
        return workerCreation.add(request, userId);
    }

    @Override
    public WorkerCreationRequest acceptWorkerCreationRequest(WorkerCreationRequest request) {
        return workerCreation.accept(request);
    }

    @Override
    public WorkerCreationRequest deleteWorkerCreationRequest(WorkerCreationRequest request) {
        return workerCreation.delete(request);
    }

    @Override
    public List<WorkerCreationRequest> getWorkerCreationRequestsStore(int storeId) {
        return workerCreation.getByStore(storeId);
    }

    @Override
    public ShiftTemplate addShiftTemplate(ShiftTemplate template, int userId) {
        return shiftTemplate.add(template, userId);
    }

    @Override
    public ShiftTemplate updateShiftTemplate(ShiftTemplate template) {
        return shiftTemplate.update(template);
    }

    @Override
    public ShiftTemplate deleteShiftTemplate(ShiftTemplate template) {
        return shiftTemplate.delete(template);
    }

    @Override
    public List<ShiftTemplate> getShiftTemplatesByStore(int storeId) {
        return shiftTemplate.getByStore(storeId);
    }

    @Override
    public ScheduleTemplate setScheduleTemplate(ScheduleTemplate template) {
        return scheduleTemplate.set(template);
    }

    @Override
    public ScheduleTemplate getScheduleTemplateStore(int storeId) {
        return scheduleTemplate.getByStore(storeId);
    }

    @Override
    public SchedulePreferences setSchedulePreferences(SchedulePreferences preferences) {
        return schedulePreferences.set(preferences);
    }

    @Override
    public List<SchedulePreferences> getSchedulePreferencesByStore(int storeId) {
        return schedulePreferences.getByStore(storeId);
    }

    @Override
    public SchedulePreferences getSchedulePreferencesByUser(int userId) {
        return schedulePreferences.getByUser(userId);
    }

    @Override
    public User getUser(int userId) {
        String sql = "SELECT * FROM user_table WHERE id=:id";
        Map<String, Object> params = Map.of("id",userId);
        return HelperFunctions.successOrNull(()-> new User(PGF.queryMap(sql,params)));
    }

    @Override
    public Store getUserStore(int userId) {
        String sql = "SELECT * FROM store WHERE id=(SELECT store_id FROM user_table WHERE id=:userId)";
        Map<String, Object> params = Map.of("user_id", userId);
        return HelperFunctions.successOrNull(()-> new Store(PGF.queryMap(sql,params)));
    }

    @Override
    public void addWorker(WorkerCreationValues values) {
        addUserFunctions.addWorker(values);
    }

    @Override
    public void addDepartment(DepartmentCreationValues values) {
        addUserFunctions.addDepartment(values);
    }

    @Override
    public void addChain(ChainCreationValues values) {
        addUserFunctions.addChain(values);
    }

    @Override
    public PersistenceResult<Integer> authenticateUser(String email, String password, String userType) {
        return userAuthentication.authenticate(email, password, userType);
    }
}


