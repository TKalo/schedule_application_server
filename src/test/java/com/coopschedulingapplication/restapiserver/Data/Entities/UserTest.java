package com.coopschedulingapplication.restapiserver.Data.Entities;

import com.coopschedulingapplication.restapiserver.Data.Enums.UserType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    Integer id = 1414;
    Integer storeId = 12414;
    UserType type = UserType.worker;
    String typeString = UserType.worker.toString();
    String name = "523 1445df";
    String email = "afasf gf3";

    @Test
    @DisplayName("Sunshine scenario")
    void Sunshine(){
        Map<String, Object> json = Map.of(
                "id", id,
                "storeId", storeId,
                "type", typeString,
                "name", name,
                "email", email
        );
        User object = new User(json);
        assertEquals(object.getId(),id);
        assertEquals(object.getStoreId(),storeId);
        assertEquals(object.getType(),type);
        assertEquals(object.getName(),name);
        assertEquals(object.getEmail(),email);
    }

    @Test
    @DisplayName("negative values scenario")
    void NegativeValues(){
        id = -1414;
        storeId = -12414;

        Map<String, Object> json = Map.of(
                "id", id,
                "storeId", storeId,
                "type", typeString,
                "name", name,
                "email", email
        );
        User object = new User(json);
        assertEquals(object.getId(),id);
        assertEquals(object.getStoreId(),storeId);
    }

    @Test
    @DisplayName("include additional fields scenario")
    void IncludeAdditionalFields(){
        Object nonExistingField = "some object";
        Map<String, Object> json = Map.of(
                "id", id,
                "storeId", storeId,
                "type", typeString,
                "name", name,
                "email", email,
                "nonExistingField",nonExistingField
        );
        User object = new User(json);
        assertEquals(object.getId(),id);
        assertEquals(object.getStoreId(),storeId);
        assertEquals(object.getType(),type);
        assertEquals(object.getName(),name);
        assertEquals(object.getEmail(),email);
    }

    @Test
    @DisplayName("missing fields scenario")
    void MissingFields(){

        Map<String, Object> json = Map.of();
        User object = new User(json);
        assertNull(object.getId());
        assertNull(object.getStoreId());
        assertNull(object.getType());
        assertNull(object.getName());
        assertNull(object.getEmail());
    }

    @Test()
    @DisplayName("wrong values scenario")
    void wrongValues(){
        Object string = "";
        Object integer = "";

        Map<String, Object> json = Map.of(
                "id", string,
                "name", integer
        );
        assertThrows(ClassCastException.class, () -> new ShiftTemplate(json));
    }
}