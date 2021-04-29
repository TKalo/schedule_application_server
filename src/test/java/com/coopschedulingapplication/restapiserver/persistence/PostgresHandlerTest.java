package com.coopschedulingapplication.restapiserver.persistence;

import com.coopschedulingapplication.restapiserver.Data.Entities.ShiftTemplate;
import com.coopschedulingapplication.restapiserver.Data.Enums.WeekDay;
import com.coopschedulingapplication.restapiserver.Data.Enums.WorkerType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

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

        Principal principal = () -> "0";
        ShiftTemplate shiftTemplate = new ShiftTemplate(null,0,3L,4L, WeekDay.friday, WorkerType.below_eighteen);

        @Test
        void addShiftTemplate() {
            postgresHandler.addShiftTemplate(shiftTemplate, principal);
            List<ShiftTemplate> shiftTemplates = postgresHandler.getShiftTemplatesStore(0);
            assertTrue(shiftTemplates.size() > 0);
            assertEquals(shiftTemplate.getStartTime(),shiftTemplates.get(0).getStartTime());
            assertEquals(shiftTemplate.getEndTime(),shiftTemplates.get(0).getEndTime());
            assertEquals(shiftTemplate.getWeekDay(),shiftTemplates.get(0).getWeekDay());
            assertEquals(shiftTemplate.getWorkerType(),shiftTemplates.get(0).getWorkerType());
        }

        @Test
        void updateShiftTemplate() {
            ShiftTemplate shiftTemplateTemp = postgresHandler.getShiftTemplatesStore(0).get(0);
            shiftTemplateTemp.setWeekDay(WeekDay.monday);
            postgresHandler.updateShiftTemplate(shiftTemplate);
            List<ShiftTemplate> shiftTemplates = postgresHandler.getShiftTemplatesStore(0);
            assertTrue(shiftTemplates.size() > 0);
            assertEquals(shiftTemplate.getStartTime(),shiftTemplates.get(0).getStartTime());
            assertEquals(shiftTemplate.getEndTime(),shiftTemplates.get(0).getEndTime());
            assertEquals(shiftTemplate.getWeekDay(),shiftTemplates.get(0).getWeekDay());
            assertEquals(shiftTemplate.getWorkerType(),shiftTemplates.get(0).getWorkerType());
        }

        @Test
        void deleteShiftTemplate() {
            postgresHandler.deleteShiftTemplate(postgresHandler.getShiftTemplatesStore(0).get(0));
            List<ShiftTemplate> shiftTemplates = postgresHandler.getShiftTemplatesStore(0);
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



    @Test
    void snake2Camel(){
        Map<String,Object> camelMap = postgresHandler.snake2Camel(Map.of("snake_to_camel", 0));
        assertEquals("snakeToCamel", camelMap.keySet().stream().findFirst().orElse(null));
        assertEquals(1, camelMap.size());
    }

    @Test
    void successOrNullTest(){
        assertEquals(1, new SuccessOrNull<Integer>().successOrNull(()-> 1));
        assertNull(new SuccessOrNull<Integer>().successOrNull(() -> Integer.parseInt("adasdd")));
    }
}