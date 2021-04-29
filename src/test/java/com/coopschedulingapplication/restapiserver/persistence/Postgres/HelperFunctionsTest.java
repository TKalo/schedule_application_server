package com.coopschedulingapplication.restapiserver.persistence.Postgres;

import com.coopschedulingapplication.restapiserver.Data.Enums.WeekDay;
import com.coopschedulingapplication.restapiserver.Data.Enums.WorkerType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class HelperFunctionsTest {


    @Autowired
    private PostgresHandler postgresHandler;

    @Test
    void snake2Camel(){
        Map<String,Object> camelMap = HelperFunctions.snake2Camel(Map.of("snake_to_camel", 0));
        assertEquals("snakeToCamel", camelMap.keySet().stream().findFirst().orElse(null));
        assertEquals(1, camelMap.size());
    }

    @Test
    void map2SqlMapTest(){

        Integer id = 1414;
        Integer storeId = 12414;
        Long startTime = 6525L;
        Long endTime = 15315135L;
        WeekDay weekDay = WeekDay.friday;
        WorkerType workerType = WorkerType.eighteen_plus;

        MapSqlParameterSource newMap = HelperFunctions.map2SqlMap(Map.of(
                "id", id,
                "storeId", storeId,
                "startTime", startTime,
                "endTime", endTime,
                "weekDay", weekDay,
                "workerType", workerType
        ));

        assertTrue(newMap.hasValue("id"));
        assertTrue(newMap.hasValue("storeId"));
        assertTrue(newMap.hasValue("startTime"));
        assertTrue(newMap.hasValue("endTime"));
        assertTrue(newMap.hasValue("weekDay"));
        assertTrue(newMap.hasValue("workerType"));


        assertEquals(weekDay,newMap.getValue("weekDay"));
        assertEquals(workerType,newMap.getValue("workerType"));
    }

    @Test
    void successOrNullTest(){
        assertEquals(1, HelperFunctions.successOrNull(()-> 1));
        assertNull(HelperFunctions.successOrNull(() -> Integer.parseInt("adasdd")));
    }
}
