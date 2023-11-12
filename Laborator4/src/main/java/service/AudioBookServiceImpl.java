package service;

import model.AudioBook;
import model.Book;
import model.EBook;
import repository.book.AudioBookRepository;
import repository.book.BookRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class AudioBookServiceImpl implements AudioBookService{
    private final AudioBookRepository bookRepository;

    public AudioBookServiceImpl(AudioBookRepository bookRepository){
        this.bookRepository = bookRepository;
    }
    @Override
    public List<AudioBook> findAllAudioBooks() {
        return bookRepository.findAllAudioBooks();
    }

    @Override
    public AudioBook findAudioBookById(Long id) {
        return bookRepository.findAudioBookById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book with id: %d not found".formatted(id)));
    }

    @Override
    public boolean saveAudioBook(AudioBook book) {
        return bookRepository.saveAudioBook(book);
    }

    @Override
    public int getAgeOfAudioBook(Long id) {
        AudioBook book = this.findAudioBookById(id);
        LocalDate now = LocalDate.now();
        return (int) ChronoUnit.YEARS.between(book.getPublishedDate(), now);
    }
}
