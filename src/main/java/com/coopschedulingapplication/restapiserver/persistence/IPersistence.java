package com.coopschedulingapplication.restapiserver.persistence;

import com.coopschedulingapplication.restapiserver.Data.ValueEntities.DepartmentCreationValues;
import com.coopschedulingapplication.restapiserver.Data.ValueEntities.WorkerCreationValues;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.List;

import com.coopschedulingapplication.restapiserver.Data.Entities.*;

@Component
@ComponentScan
public interface IPersistence {

    WorkerCreationRequest addWorkerCreationRequest(WorkerCreationRequest request, int userId);
    WorkerCreationRequest acceptWorkerCreationRequest(WorkerCreationRequest request);
    WorkerCreationRequest deleteWorkerCreationRequest(WorkerCreationRequest request);
    List<WorkerCreationRequest> getWorkerCreationRequestsStore(int storeId);

    ShiftTemplate addShiftTemplate(ShiftTemplate template, int userId);
    ShiftTemplate updateShiftTemplate(ShiftTemplate template);
    ShiftTemplate deleteShiftTemplate(ShiftTemplate template);
    List<ShiftTemplate> getShiftTemplatesByStore(int storeId);

    ScheduleTemplate setScheduleTemplate(ScheduleTemplate template);
    ScheduleTemplate getScheduleTemplateStore(int storeId);

    SchedulePreferences setSchedulePreferences(SchedulePreferences preferences);
    List<SchedulePreferences> getSchedulePreferencesByStore(int userId);
    SchedulePreferences getSchedulePreferencesByUser(int userId);

    User getUser(int userId);
    Store getUserStore(int userId);

    void addWorker(WorkerCreationValues values);
    void addDepartment(DepartmentCreationValues values);
}
