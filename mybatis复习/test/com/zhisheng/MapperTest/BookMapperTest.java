package com.zhisheng.MapperTest;

import com.zhisheng.mapper.BookMapper;
import com.zhisheng.pojo.Book;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by 10412 on 2017/4/10.
 */
public class BookMapperTest
{
    private SqlSessionFactory sqlSessionFactory;


    @Before
    public void setup() throws Exception
    {
        //创建sqlSessionFactory

        //Mybatis 配置文件
        String resource = "SqlMapConfig.xml";

        //得到配置文件流
        InputStream inputStream = Resources.getResourceAsStream(resource);

        //创建会话工厂,传入Mybatis的配置文件信息
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void addBook() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);

        Book book = new Book();
        /*book.setId(6);
        book.setAuthor("田志声");
        book.setBookName("《Mybatis从入门到精通》");
        book.setPublishDate(new Date());
        book.setPrice(45.0);*/
        book.setId(7);
        book.setAuthor("田志声");
        book.setBookName("Mybatis");
        book.setPublishDate(new Date());
        book.setPrice(56.0);

        bookMapper.addBook(book);
        System.out.println(book);
        sqlSession.commit();
        sqlSession.close();
    }


    @Test
    public void removeBook() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);

        bookMapper.removeBook(1);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void findBookByName() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);

        List<Book> list = bookMapper.findBookByName("Mybatis");
        for (Book book : list ) {
            System.out.println(book);
        }
        sqlSession.close();
    }

    @Test
    public void findBookById() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);

        Book book = bookMapper.findBookById(1);
        System.out.println(book);
        sqlSession.close();
    }

    @Test
    public void findAllBooksName() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);

        List<String> bookNames = bookMapper.findAllBooksName();
        for (String  bookName : bookNames) {
            System.out.println(bookName);
        }
        sqlSession.close();
    }


    @Test
    public void findCountOfBook() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);
        int count = bookMapper.findCountOfBook();
        System.out.println(count);
        sqlSession.close();
    }


    @Test
    public void updateBook() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);

        Book book = new Book();
        book.setId(6);
        book.setBookName("《Mybatis实战》");
        book.setPrice(38.0);
        book.setAuthor("谢照东");
        bookMapper.updateBook(book);
        System.out.println(book);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void findBookNameAndAuthor() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);
        List<Map<String, Object>> list = bookMapper.findBookNameAndAuthor();
        for (Object o : list) {
            System.out.println(o);
        }
        sqlSession.close();
    }

}