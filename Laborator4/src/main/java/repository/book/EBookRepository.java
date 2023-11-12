package repository.book;

import model.AudioBook;
import model.Book;
import model.EBook;

import java.util.List;
import java.util.Optional;

public interface EBookRepository {
    List<EBook> findAllEBooks();
    Optional <EBook> findEBookById(Long id);
    boolean saveEBook(EBook eBook);
    void removeAllEBooks();
}
