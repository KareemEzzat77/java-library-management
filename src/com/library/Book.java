package com.library;

public class Book {
    private final int id;
    private String author;
    private String title;


    public Book(int id, String title, String author){
        this.id = id;
        this.title = title;
        this.author = author;
    }
    public int getId(){
        return this.id;
    }
    public String getAuthor(){
        return this.author;
    }
    public String getTitle()
    {
        return this.title;
    }

    public void setTitle(String title){
        if(!title.isEmpty()){
            this.title = title;
        }
    }

    public void setAuthor(String author){
        if(!author.isEmpty()){
            this.author = author;
        }
    }

    @Override
    public String toString(){
        return id + "- " + title + " by " + author + ".";
    }
}
