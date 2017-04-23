package com.zhisheng.mapper;

import com.zhisheng.pojo.Book;

import java.util.List;
import java.util.Map;

/**
 * Created by 10412 on 2017/4/10.
 */
public interface BookMapper
{
    void addBook(Book book);

    void removeBook(Integer id);

    List<Book> findBookByName(String bookName);

    Book findBookById(Integer id);

    //查找所有书籍的名字
    List<String> findAllBooksName();

    int findCountOfBook();

    void updateBook(Book book);

   List<Map<String,Object>> findBookNameAndAuthor();


}
