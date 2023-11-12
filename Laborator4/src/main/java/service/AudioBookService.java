package service;

import model.AudioBook;
import model.Book;

import java.util.List;

public interface AudioBookService {
    List<AudioBook> findAllAudioBooks();
    AudioBook findAudioBookById(Long id);
    boolean saveAudioBook(AudioBook book);
    int getAgeOfAudioBook(Long id); //e business logic
}
