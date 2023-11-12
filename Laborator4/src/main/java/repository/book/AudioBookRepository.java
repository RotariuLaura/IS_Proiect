package repository.book;

import model.AudioBook;

import java.util.List;
import java.util.Optional;

public interface AudioBookRepository {
    List<AudioBook> findAllAudioBooks();
    Optional<AudioBook> findAudioBookById(Long id);
    boolean saveAudioBook(AudioBook audioBook);
    void removeAllAudioBooks();
}
