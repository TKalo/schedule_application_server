package com.coopschedulingapplication.restapiserver.Data.Entities;

import com.coopschedulingapplication.restapiserver.Data.Enums.WeekDay;
import com.coopschedulingapplication.restapiserver.Data.Enums.WorkerType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ShiftTemplateTest {

    Integer id = 1414;
    Integer storeId = 12414;
    Long startTime = 6525L;
    Long endTime = 15315135L;
    WeekDay weekDay = WeekDay.friday;
    WorkerType workerType = WorkerType.eighteen_plus;
    String weekDayString = WeekDay.friday.toString();
    String workerTypeString = WorkerType.eighteen_plus.toString();

    @Test
    @DisplayName("SchedulePreferencesTest - Sunshine scenario")
    void Sunshine(){
        Map<String, Object> json = Map.of(
                "id", id,
                "storeId", storeId,
                "startTime", startTime,
                "endTime", endTime,
                "weekDay", weekDayString,
                "workerType", workerTypeString

        );
        ShiftTemplate object = ShiftTemplate.fromJson(json);
        assertEquals(object.getId(),id);
        assertEquals(object.getStoreId(),storeId);
        assertEquals(object.getStartTime(),startTime);
        assertEquals(object.getEndTime(),endTime);
        assertEquals(object.getWeekDay(),weekDay);
        assertEquals(object.getWorkerType(),workerType);
    }

    @Test
    @DisplayName("negative values scenario")
    void NegativeValues(){
        id = -1414;
        storeId = -12414;
        startTime = -6525L;
        endTime = -15315135L;

        Map<String, Object> json = Map.of(
                "id", id,
                "storeId", storeId,
                "startTime", startTime,
                "endTime", endTime,
                "weekDay", weekDayString,
                "workerType", workerTypeString

        );
        ShiftTemplate object = ShiftTemplate.fromJson(json);
        assertEquals(object.getId(),id);
        assertEquals(object.getStoreId(),storeId);
        assertEquals(object.getStartTime(),startTime);
        assertEquals(object.getEndTime(),endTime);
    }

    @Test
    @DisplayName("include additional fields scenario")
    void IncludeAdditionalFields(){
        Object nonExistingField = "some object";
        Map<String, Object> json = Map.of(
                "id", id,
                "storeId", storeId,
                "startTime", startTime,
                "endTime", endTime,
                "weekDay", weekDayString,
                "workerType", workerTypeString,
                "nonExistingField",nonExistingField
        );
        ShiftTemplate object = ShiftTemplate.fromJson(json);
        assertEquals(object.getId(),id);
        assertEquals(object.getStoreId(),storeId);
        assertEquals(object.getStartTime(),startTime);
        assertEquals(object.getEndTime(),endTime);
        assertEquals(object.getWeekDay(),weekDay);
        assertEquals(object.getWorkerType(),workerType);
    }

    @Test
    @DisplayName("missing fields scenario")
    void MissingFields(){

        Map<String, Object> json = Map.of();
        ShiftTemplate object = ShiftTemplate.fromJson(json);
        assertNull(object.getId());
        assertNull(object.getStoreId());
        assertNull(object.getStartTime());
        assertNull(object.getEndTime());
        assertNull(object.getWeekDay());
        assertNull(object.getWorkerType());
    }

    @Test()
    @DisplayName("wrong values scenario")
    void wrongValues(){
        Object string = "";
        Object integer = 1;

        Map<String, Object> json = Map.of(
                "id", string,
                "workerType", integer
        );
        assertThrows(ClassCastException.class, () -> ShiftTemplate.fromJson(json));
    }
}