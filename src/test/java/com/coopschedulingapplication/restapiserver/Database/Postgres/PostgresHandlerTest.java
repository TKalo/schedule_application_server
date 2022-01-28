package com.coopschedulingapplication.restapiserver.Database.Postgres;

import com.coopschedulingapplication.restapiserver.DataTypes.Entities.ShiftTemplate;
import com.coopschedulingapplication.restapiserver.DataTypes.Enums.WeekDay;
import com.coopschedulingapplication.restapiserver.DataTypes.Enums.WorkerType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class PostgresHandlerTest {

    @Autowired
    private PostgresHandler postgresHandler;


    @Nested
    class WorkerCreationRequestTest{

        @Test
        void addWorkerCreationRequest() {

        }

        @Test
        void acceptWorkerCreationRequest() {

        }

        @Test
        void deleteWorkerCreationRequest() {

        }

        @Test
        void getWorkerCreationRequestsStore() {

        }
    }

    @Nested
    class ShiftTemplateTest{

        ShiftTemplate shiftTemplate = new ShiftTemplate(0,0,3L,4L, WeekDay.friday, WorkerType.below_eighteen);

        @Test
        void addShiftTemplate() {
            postgresHandler.addShiftTemplate(shiftTemplate, 0);
            List<ShiftTemplate> shiftTemplates = postgresHandler.getShiftTemplatesByStore(0);
            assertTrue(shiftTemplates.size() > 0);
            assertEquals(shiftTemplate.getStartTime(),shiftTemplates.get(shiftTemplates.size()-1).getStartTime());
            assertEquals(shiftTemplate.getEndTime(),shiftTemplates.get(shiftTemplates.size()-1).getEndTime());
            assertEquals(shiftTemplate.getWeekDay(),shiftTemplates.get(shiftTemplates.size()-1).getWeekDay());
            assertEquals(shiftTemplate.getWorkerType(),shiftTemplates.get(shiftTemplates.size()-1).getWorkerType());
        }

        @Test
        void updateShiftTemplate() {
            ShiftTemplate shiftTemplateTemp = postgresHandler.getShiftTemplatesByStore(0).get(0);
            shiftTemplateTemp.setWeekDay(WeekDay.monday);
            postgresHandler.updateShiftTemplate(shiftTemplate);
            List<ShiftTemplate> shiftTemplates = postgresHandler.getShiftTemplatesByStore(0);
            assertTrue(shiftTemplates.size() > 0);
            assertEquals(shiftTemplate.getStartTime(),shiftTemplates.get(0).getStartTime());
            assertEquals(shiftTemplate.getEndTime(),shiftTemplates.get(0).getEndTime());
            assertEquals(shiftTemplate.getWeekDay(),shiftTemplates.get(0).getWeekDay());
            assertEquals(shiftTemplate.getWorkerType(),shiftTemplates.get(0).getWorkerType());
        }

        @Test
        void deleteShiftTemplate() {
            postgresHandler.deleteShiftTemplate(postgresHandler.getShiftTemplatesByStore(0).get(0));
            List<ShiftTemplate> shiftTemplates = postgresHandler.getShiftTemplatesByStore(0);
            assertEquals(0, shiftTemplates.size());
        }
    }

    @Nested
    class ScheduleTemplateTest{

        @Test
        void setScheduleTemplate() {

        }

        @Test
        void getScheduleTemplateStore() {

        }

    }

    @Nested
    class SchedulePreferencesTest{
        @Test
        void setSchedulePreferences() {
        }

        @Test
        void getSchedulePreferencesStore() {
        }

        @Test
        void getSchedulePreferencesUser() {
        }
    }
}