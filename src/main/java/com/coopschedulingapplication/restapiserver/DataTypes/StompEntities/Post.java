package com.coopschedulingapplication.restapiserver.DataTypes.StompEntities;

import java.io.Serializable;
import java.util.List;

public class Post <T> implements Serializable {

    private PostCommand command;
    private List<T> resultList;

    public Post(PostCommand command, List<T> resultList) {
        this.command = command;
        this.resultList = resultList;
    }

    public PostCommand getCommand() {
        return command;
    }

    public List<T> getResultList() {
        return resultList;
    }

    @Override
    public String toString() {
        return "Post{" +
                "command=" + command +
                "listlength=" + resultList.size() +
                '}';
    }
}
