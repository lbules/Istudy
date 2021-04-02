package com.course.server.dto;

/**
 * @Auther: luoss
 * @Date: 2021/4/2 16:20
 * @Description:
 */
public class SortDto {
    /**
     * 用于排序
     */

    private String id;

    private int oldSort;

    private int newSort;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOldSort() {
        return oldSort;
    }

    public void setOldSort(int oldSort) {
        this.oldSort = oldSort;
    }

    public int getNewSort() {
        return newSort;
    }

    public void setNewSort(int newSort) {
        this.newSort = newSort;
    }

    @Override
    public String toString() {
        return "SortDto{" +
                "id='" + id + '\'' +
                ", oldSort=" + oldSort +
                ", newSort=" + newSort +
                '}';
    }
}
