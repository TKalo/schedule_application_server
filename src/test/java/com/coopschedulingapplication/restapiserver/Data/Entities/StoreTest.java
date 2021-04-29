package com.coopschedulingapplication.restapiserver.Data.Entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StoreTest {

    Integer id = 1414;
    String address = "23525";
    String city = "2315235";
    String key = "1245124515";


    @Test
    @DisplayName("SchedulePreferencesTest - Sunshine scenario")
    void Sunshine(){
        Map<String, Object> json = Map.of(
                "id", id,
                "address", address,
                "city", city,
                "key", key
        );
        Store object = Store.fromJson(json);
        assertEquals(object.getId(),id);
        assertEquals(object.getAddress(),address);
        assertEquals(object.getCity(),city);
        assertEquals(object.getKey(),key);
    }

    @Test
    @DisplayName("negative values scenario")
    void NegativeValues(){
        id = -1414;

        Map<String, Object> json = Map.of(
                "id", id,
                "address", address,
                "city", city,
                "key", key
        );
        Store object = Store.fromJson(json);
        assertEquals(object.getId(),id);
    }

    @Test
    @DisplayName("include additional fields scenario")
    void IncludeAdditionalFields(){
        Object nonExistingField = "some object";
        Map<String, Object> json = Map.of(
                "id", id,
                "address", address,
                "city", city,
                "key", key,
                "nonExistingField",nonExistingField
        );
        Store object = Store.fromJson(json);
        assertEquals(object.getId(),id);
        assertEquals(object.getAddress(),address);
        assertEquals(object.getCity(),city);
        assertEquals(object.getKey(),key);
    }

    @Test
    @DisplayName("missing fields scenario")
    void MissingFields(){
        Map<String, Object> json = Map.of();
        Store object = Store.fromJson(json);
        assertNull(object.getId());
        assertNull(object.getAddress());
        assertNull(object.getCity());
        assertNull(object.getKey());
    }

    @Test()
    @DisplayName("wrong values scenario")
    void wrongValues(){
        Object string = "";
        Object integer = 1;

        Map<String, Object> json = Map.of(
                "id", string,
                "address", integer
        );
        assertThrows(ClassCastException.class, () -> Store.fromJson(json));
    }
}