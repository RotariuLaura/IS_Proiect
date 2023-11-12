package repository.book;

import model.AudioBook;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AudioBookRepositoryMock implements AudioBookRepository{
    private final List<AudioBook> books;

    public AudioBookRepositoryMock(){
        books = new ArrayList<>();
    }

    @Override
    public List<AudioBook> findAllAudioBooks() {
        return books;
    }

    @Override
    public Optional<AudioBook> findAudioBookById(Long id) {
        return books.parallelStream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean saveAudioBook(AudioBook book) {
        return books.add(book);
    }

    @Override
    public void removeAllAudioBooks() {
        books.clear();
    }
}
