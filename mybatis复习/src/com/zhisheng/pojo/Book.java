package com.zhisheng.pojo;

import java.util.Date;

/**
 * Created by 10412 on 207/4/10.
 */
public class Book
{
    private Integer id;//编号
    private String bookName;//书名
    private String author; //作者
    private Date publishDate;//发布日期
    private Double price;//价格

    public Book() {
    }

    public Book(Integer id, String bookName, String author, Date publishDate, Double price) {
        this.id = id;
        this.bookName = bookName;
        this.author = author;
        this.publishDate = publishDate;
        this.price = price;
    }

    public Book(String bookName, String author, Date publishDate, Double price) {
        this.bookName = bookName;
        this.author = author;
        this.publishDate = publishDate;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", publishDate=" + publishDate +
                ", price=" + price +
                '}';
    }
}
