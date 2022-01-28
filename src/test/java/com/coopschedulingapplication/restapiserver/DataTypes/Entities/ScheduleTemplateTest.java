package com.coopschedulingapplication.restapiserver.DataTypes.Entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleTemplateTest {

    Integer storeId = 1023;
    Integer weeks = 2044;
    Long preferenceDeadline = 50506L;
    Long creationDeadline = 23555L;
    Long initiationDeadline = 23525235L;

    @Test
    @DisplayName("SchedulePreferencesTest - Sunshine scenario")
    void Sunshine(){
        Map<String, Object> json = Map.of(
                "storeId", storeId,
                "weeks", weeks,
                "preferenceDeadline", preferenceDeadline,
                "creationDeadline", creationDeadline,
                "initiationDeadline", initiationDeadline
        );
        ScheduleTemplate object = new ScheduleTemplate(json);
        assertEquals(object.getStoreId(),storeId);
        assertEquals(object.getWeeks(),weeks);
        assertEquals(object.getPreferenceDeadline(),preferenceDeadline);
        assertEquals(object.getCreationDeadline(),creationDeadline);
        assertEquals(object.getInitiationDeadline(),initiationDeadline);
    }

    @Test
    @DisplayName("negative values scenario")
    void NegativeValues(){
        storeId = -1023;
        weeks = -2044;
        preferenceDeadline = -50506L;
        creationDeadline = -23555L;
        initiationDeadline = -23525235L;

        Map<String, Object> json = Map.of(
                "storeId", storeId,
                "weeks", weeks,
                "preferenceDeadline", preferenceDeadline,
                "creationDeadline", creationDeadline,
                "initiationDeadline", initiationDeadline
        );
        ScheduleTemplate object = new ScheduleTemplate(json);
        assertEquals(object.getStoreId(),storeId);
        assertEquals(object.getWeeks(),weeks);
        assertEquals(object.getPreferenceDeadline(),preferenceDeadline);
        assertEquals(object.getCreationDeadline(),creationDeadline);
        assertEquals(object.getInitiationDeadline(),initiationDeadline);
    }

    @Test
    @DisplayName("include additional fields scenario")
    void IncludeAdditionalFields(){
        Object nonExistingField = "some object";

        Map<String, Object> json = Map.of(
                "storeId", storeId,
                "weeks", weeks,
                "preferenceDeadline", preferenceDeadline,
                "creationDeadline", creationDeadline,
                "initiationDeadline", initiationDeadline,
                "nonExistingField", nonExistingField
        );
        ScheduleTemplate object = new ScheduleTemplate(json);
        assertEquals(object.getStoreId(),storeId);
        assertEquals(object.getWeeks(),weeks);
        assertEquals(object.getPreferenceDeadline(),preferenceDeadline);
        assertEquals(object.getCreationDeadline(),creationDeadline);
        assertEquals(object.getInitiationDeadline(),initiationDeadline);
    }

    @Test
    @DisplayName("missing fields scenario")
    void MissingFields(){
        Map<String, Object> json = Map.of();
        ScheduleTemplate object = new ScheduleTemplate(json);
        assertNull(object.getWeeks());
        assertNull(object.getPreferenceDeadline());
        assertNull(object.getStoreId());
        assertNull(object.getCreationDeadline());
        assertNull(object.getInitiationDeadline());
    }

    @Test()
    @DisplayName("wrong values scenario")
    void wrongValues(){
        String weeks = "";
        Object preferenceDeadline = "";

        Map<String, Object> json = Map.of(
                "weeks", weeks,
                "preferenceDeadline", preferenceDeadline
        );
        assertThrows(ClassCastException.class, () -> new ScheduleTemplate(json));
    }
}