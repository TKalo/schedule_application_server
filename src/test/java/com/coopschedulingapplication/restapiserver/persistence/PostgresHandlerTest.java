package com.coopschedulingapplication.restapiserver.persistence;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostgresHandlerTest {

    @Autowired
    private PostgresHandler postgresHandler;


    @Nested
    class WorkerCreationRequest{

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


    @Test
    void addShiftTemplate() {

    }

    @Test
    void updateShiftTemplate() {

    }

    @Test
    void deleteShiftTemplate() {

    }

    @Test
    void getShiftTemplatesStore() {

    }

    @Test
    void setScheduleTemplate() {

    }

    @Test
    void getScheduleTemplateStore() {

    }

    @Test
    void setSchedulePreferences() {
    }

    @Test
    void getSchedulePreferencesStore() {
    }

    @Test
    void getSchedulePreferencesUser() {
    }

    @Test
    void getUser() {
    }

    @Test
    void getUserStore() {
    }

    @Test
    void addWorker() {
    }

    @Test
    void addDepartment() {
    }
}