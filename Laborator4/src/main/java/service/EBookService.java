package service;

import model.Book;
import model.EBook;

import java.util.List;

public interface EBookService {
    List<EBook> findAllEBooks();
    EBook findEBookById(Long id);
    boolean saveEBook(EBook book);
    int getAgeOfEBook(Long id);
}
