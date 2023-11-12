package repository.book;

import model.AudioBook;

import java.util.List;
import java.util.Optional;

public class AudioBookRepositoryCacheDecorator extends AudioBookRepositoryDecorator {
    private Cache<AudioBook> cache;

    public AudioBookRepositoryCacheDecorator(AudioBookRepository audioBookRepository, Cache<AudioBook> cache) {
        super(audioBookRepository);
        this.cache = cache;
    }

    @Override
    public List<AudioBook> findAllAudioBooks() {
        if (cache.hasResult()) {
            return cache.load();
        }
        List<AudioBook> audioBooks = decoratedRepository.findAllAudioBooks();
        cache.save(audioBooks);
        return audioBooks;
    }

    @Override
    public Optional<AudioBook> findAudioBookById(Long id) {
        return decoratedRepository.findAudioBookById(id);
    }

    @Override
    public boolean saveAudioBook(AudioBook audioBook) {
        cache.invalidateCache();
        return decoratedRepository.saveAudioBook(audioBook);
    }

    @Override
    public void removeAllAudioBooks() {
        cache.invalidateCache();
        decoratedRepository.removeAllAudioBooks();
    }
}