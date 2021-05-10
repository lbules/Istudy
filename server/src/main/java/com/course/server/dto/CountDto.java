package com.course.server.dto;

/**
 * @Auther: luoss
 * @Date: 2021/5/1 15:27
 * @Description:
 */
public class CountDto {
    private int allCourse;

    private int allChapter;

    private int allSection;

    private int allEnroll;

    private int allComment;

    private int allMember;

    private int newComment;

    private int newMember;

    private int newEnroll;

    public int getAllCourse() {
        return allCourse;
    }

    public void setAllCourse(int allCourse) {
        this.allCourse = allCourse;
    }

    public int getAllChapter() {
        return allChapter;
    }

    public void setAllChapter(int allChapter) {
        this.allChapter = allChapter;
    }

    public int getAllSection() {
        return allSection;
    }

    public void setAllSection(int allSection) {
        this.allSection = allSection;
    }

    public int getAllEnroll() {
        return allEnroll;
    }

    public void setAllEnroll(int allEnroll) {
        this.allEnroll = allEnroll;
    }

    public int getAllComment() {
        return allComment;
    }

    public void setAllComment(int allComment) {
        this.allComment = allComment;
    }

    public int getNewComment() {
        return newComment;
    }

    public void setNewComment(int newComment) {
        this.newComment = newComment;
    }

    public int getNewMember() {
        return newMember;
    }

    public void setNewMember(int newMember) {
        this.newMember = newMember;
    }

    public int getNewEnroll() {
        return newEnroll;
    }

    public void setNewEnroll(int newEnroll) {
        this.newEnroll = newEnroll;
    }

    public int getAllMember() {
        return allMember;
    }

    public void setAllMember(int allMember) {
        this.allMember = allMember;
    }

    @Override
    public String toString() {
        return "CountDto{" +
                "allCourse=" + allCourse +
                ", allChapter=" + allChapter +
                ", allSection=" + allSection +
                ", allEnroll=" + allEnroll +
                ", allComment=" + allComment +
                ", allMember=" + allMember +
                ", newComment=" + newComment +
                ", newMember=" + newMember +
                ", newEnroll=" + newEnroll +
                '}';
    }
}
