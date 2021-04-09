package com.course.server.domain;

public class CourseContentFile {
    private String id;

    private String couseId;

    private String url;

    private String name;

    private Integer size;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCouseId() {
        return couseId;
    }

    public void setCouseId(String couseId) {
        this.couseId = couseId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", couseId=").append(couseId);
        sb.append(", url=").append(url);
        sb.append(", name=").append(name);
        sb.append(", size=").append(size);
        sb.append("]");
        return sb.toString();
    }
}