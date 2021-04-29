package com.coopschedulingapplication.restapiserver.persistence;

import com.coopschedulingapplication.restapiserver.ConnectionObjects.DepartmentCreationValues;
import com.coopschedulingapplication.restapiserver.ConnectionObjects.WorkerCreationValues;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.List;

import com.coopschedulingapplication.restapiserver.Data.Objects.*;

@Component
@ComponentScan
public interface IPersistence {

    WorkerCreationRequest addWorkerCreationRequest(WorkerCreationRequest request, Principal principal);
    WorkerCreationRequest acceptWorkerCreationRequest(WorkerCreationRequest request);
    WorkerCreationRequest deleteWorkerCreationRequest(WorkerCreationRequest request);
    List<WorkerCreationRequest> getWorkerCreationRequestsStore(int storeId);

    ShiftTemplate addShiftTemplate(ShiftTemplate template, Principal principal);
    ShiftTemplate updateShiftTemplate(ShiftTemplate template);
    ShiftTemplate deleteShiftTemplate(ShiftTemplate template);
    List<ShiftTemplate> getShiftTemplatesStore(int storeId);

    ScheduleTemplate setScheduleTemplate(ScheduleTemplate template);
    ScheduleTemplate getScheduleTemplateStore(int storeId);

    SchedulePreferences setSchedulePreferences(SchedulePreferences preferences);
    List<SchedulePreferences> getSchedulePreferencesStore(int userId);
    SchedulePreferences getSchedulePreferencesUser(int userId);

    User getUser(int userId);
    Store getUserStore(int userId);

    void addWorker(WorkerCreationValues values);
    void addDepartment(DepartmentCreationValues values);
}
