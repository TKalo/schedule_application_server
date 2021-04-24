package com.coopschedulingapplication.restapiserver;

public class SpringDests {

    //prefixes
    public static final String endPoint = "/app";
    public static final String app = "/app";
    public static final String user = "/user";

    //message types
    public static final String request = "/request";
    public static final String subscribe = "/subscribe";

    //request types
    public static final String add = "/add";
    public static final String update = "/add";
    public static final String delete = "/add";
    public static final String accept = "/add";
    public static final String decline = "/add";

    //data objects
    public static final String workerCreationRequest = "/workerCreationRequest";
    public static final String scheduleTemplate = "/scheduleTemplate";
    public static final String shiftTemplate = "/shiftTemplate";
    public static final String chain ="/department";
    public static final String department ="/department";
    public static final String worker ="/worker";
    public static final String currentUser = "/currentUser";
    public static final String currentStore = "/currentStore";
    public static final String schedulePreferences = "/schedulePreferences";

    //subscriptions
    public static final String userCreationRequestSub = SpringDests.app + SpringDests.subscribe + SpringDests.workerCreationRequest;
    public static final String scheduleTemplateSub = SpringDests.app + SpringDests.subscribe + SpringDests.scheduleTemplate;
    public static final String shiftTemplateSub = SpringDests.app + SpringDests.subscribe + SpringDests.shiftTemplate;
    public static final String schedulePreferencesSub = SpringDests.app + SpringDests.subscribe + SpringDests.schedulePreferences;


    //destination values
    public static final String storeIdValue = "/{storeId}";
    public static final String userIdValue = "/{userId}";
}
