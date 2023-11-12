package service;

import model.EBook;
import repository.book.EBookRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class EBookServiceImpl implements EBookService{
    private final EBookRepository bookRepository;

    public EBookServiceImpl(EBookRepository bookRepository){
        this.bookRepository = bookRepository;
    }
    @Override
    public List<EBook> findAllEBooks() {
        return bookRepository.findAllEBooks();
    }

    @Override
    public EBook findEBookById(Long id) {
        return bookRepository.findEBookById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book with id: %d not found".formatted(id)));
    }


    @Override
    public boolean saveEBook(EBook book) {
        return bookRepository.saveEBook(book);
    }

    @Override
    public int getAgeOfEBook(Long id) {
        EBook book = this.findEBookById(id);
        LocalDate now = LocalDate.now();
        return (int) ChronoUnit.YEARS.between(book.getPublishedDate(), now);
    }
}
