/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.juergen.sqltool;

/**
 *
 * @author Juergen
 */
public class Books {
    private Integer id;
    private String title;
    private String author;
    private Integer year;
    private Integer pages;

    public Books() {}
    
    public Books(Integer id, String title, String author, Integer year, Integer pages) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.pages = pages;
    }

    @Override
    public String toString() {
        return "Books{" + "id=" + id + ", title=" + title + ", author=" + author + ", year=" + year + ", pages=" + pages + '}';
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getPages() {
        return pages;
    }

    
    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }
}
