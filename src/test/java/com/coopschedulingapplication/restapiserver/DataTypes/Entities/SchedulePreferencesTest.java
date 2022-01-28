package com.coopschedulingapplication.restapiserver.DataTypes.Entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.Map;


import static org.junit.jupiter.api.Assertions.*;

public class SchedulePreferencesTest {

    int userId = 1023;
    int prefWeekDays = 2044;
    int maxWeekDays = 50506;

    @Test
    @DisplayName("SchedulePreferencesTest - Sunshine scenario")
     void Sunshine(){
        Map<String, Object> json = Map.of(
                "userId", userId,
                "prefWeekDays", prefWeekDays,
                "maxWeekDays", maxWeekDays
        );
        SchedulePreferences object = new SchedulePreferences(json);
        assertEquals(object.getUserId(),userId);
        assertEquals(object.getPrefWeekDays(),prefWeekDays);
        assertEquals(object.getMaxWeekDays(),maxWeekDays);
    }

    @Test
    @DisplayName("negative values scenario")
    void NegativeValues(){
        userId = -1;
        prefWeekDays = -1;
        maxWeekDays = -1;
        Map<String, Object> json = Map.of(
                "userId", userId,
                "prefWeekDays", prefWeekDays,
                "maxWeekDays", maxWeekDays
        );
        SchedulePreferences object = new SchedulePreferences(json);
        assertEquals(object.getUserId(),userId);
        assertEquals(object.getPrefWeekDays(),prefWeekDays);
        assertEquals(object.getMaxWeekDays(),maxWeekDays);
    }

    @Test
    @DisplayName("include additional fields scenario")
    void IncludeAdditionalFields(){
        Object nonExistingField = "some object";

        Map<String, Object> json = Map.of(
                "userId", userId,
                "prefWeekDays", prefWeekDays,
                "maxWeekDays", maxWeekDays,
                "nonExistingField", nonExistingField
        );
        SchedulePreferences object = new SchedulePreferences(json);
        assertEquals(object.getUserId(),userId);
        assertEquals(object.getPrefWeekDays(),prefWeekDays);
        assertEquals(object.getMaxWeekDays(),maxWeekDays);
    }

    @Test
    @DisplayName("missing fields scenario")
    void MissingFields(){
        Map<String, Object> json = Map.of();
        SchedulePreferences object = new SchedulePreferences(json);
        assertNull(object.getUserId());
        assertNull(object.getMaxWeekDays());
        assertNull(object.getMaxWeekDays());
    }

    @Test()
    @DisplayName("wrong values scenario")
    void wrongValues(){
        String weeks = "not integer";
        Object preferenceDeadline = "not integer";

        Map<String, Object> json = Map.of(
                "userId", weeks,
                "maxWeekDays", preferenceDeadline
        );
        assertThrows(ClassCastException.class, () -> new SchedulePreferences(json));
    }
}
