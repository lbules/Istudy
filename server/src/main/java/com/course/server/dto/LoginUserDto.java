package com.course.server.dto;


import java.util.HashSet;
import java.util.List;

public class LoginUserDto {

    /**
     * id
     */
    private String id;

    /**
     * 登陆名
     */
    private String loginName;

    /**
     * 昵称
     */
    private String name;

    /**
     * 登录凭证
     */
    private String token;

    /**
     * 所有资源中的请求
     */
    private HashSet<String> requests;


    /**
     * 所有的资源，用于前端页面控制
     */
    private List<ResourcesDto> resources;


    public HashSet<String> getRequests() {
        return requests;
    }

    public void setRequests(HashSet<String> requests) {
        this.requests = requests;
    }

    public List<ResourcesDto> getResources() {
        return resources;
    }

    public void setResources(List<ResourcesDto> resources) {
        this.resources = resources;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    @Override
    public String toString() {
        return "LoginUserDto{" +
                "id='" + id + '\'' +
                ", loginName='" + loginName + '\'' +
                ", name='" + name + '\'' +
                ", token='" + token + '\'' +
                ", requests=" + requests +
                ", resources=" + resources +
                '}';
    }
}