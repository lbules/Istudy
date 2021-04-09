package com.course.server.dto;


public class CourseContentFileDto {

    /**
     * id
     */
    private String id;

    /**
     * 课程id
     */
    private String couseId;

    /**
     * 地址
     */
    private String url;

    /**
     * 文件名
     */
    private String name;

    /**
     * 大小|字节b
     */
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