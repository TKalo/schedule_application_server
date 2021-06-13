package com.coopschedulingapplication.restapiserver.Data.Entities;

import com.coopschedulingapplication.restapiserver.Data.Enums.WorkerCreationStatus;
import com.coopschedulingapplication.restapiserver.Data.Enums.WorkerType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WorkerCreationRequestTest {

    Integer id = 1414;
    Integer storeId = 235325;
    WorkerType type = WorkerType.below_eighteen;
    WorkerCreationStatus status = WorkerCreationStatus.pending;
    String typeString = WorkerType.below_eighteen.toString();
    String statusString = WorkerCreationStatus.pending.toString();
    String key = "asfdas2345 235";

    @Test
    @DisplayName("Sunshine scenario")
    void Sunshine(){
        Map<String, Object> json = Map.of(
                "id", id,
                "storeId", storeId,
                "type", typeString,
                "status", statusString,
                "key", key
        );
        WorkerCreationRequest object = new WorkerCreationRequest(json);
        assertEquals(id,object.getId());
        assertEquals(storeId,object.getStoreId());
        assertEquals(type,object.getType());
        assertEquals(status,object.getStatus());
        assertEquals(key,object.getKey());
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
                "status", statusString,
                "key", key
        );
        WorkerCreationRequest object = new WorkerCreationRequest(json);
        assertEquals(id,object.getId());
        assertEquals(storeId,object.getStoreId());
    }

    @Test
    @DisplayName("include additional fields scenario")
    void IncludeAdditionalFields(){
        Object nonExistingField = "some object";
        Map<String, Object> json = Map.of(
                "id", id,
                "storeId", storeId,
                "type", typeString,
                "status", statusString,
                "key", key,
                "nonExistingField",nonExistingField
        );
        WorkerCreationRequest object = new WorkerCreationRequest(json);
        assertEquals(id,object.getId());
        assertEquals(storeId,object.getStoreId());
        assertEquals(type,object.getType());
        assertEquals(status,object.getStatus());
        assertEquals(key,object.getKey());
    }

    @Test
    @DisplayName("missing fields scenario")
    void MissingFields(){

        Map<String, Object> json = Map.of();
        WorkerCreationRequest object = new WorkerCreationRequest(json);
        assertNull(object.getId());
        assertNull(object.getStoreId());
        assertNull(object.getType());
        assertNull(object.getStatus());
        assertNull(object.getKey());
    }

    @Test()
    @DisplayName("wrong values scenario")
    void wrongValues(){
        Object string = "";
        Object integer = "";

        Map<String, Object> json = Map.of(
                "id", string,
                "type", integer
        );
        assertThrows(ClassCastException.class, () ->new WorkerCreationRequest(json));
    }
}