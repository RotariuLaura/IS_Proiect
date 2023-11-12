package repository.book;

import model.EBook;

import java.util.List;
import java.util.Optional;

public class EBookRepositoryCacheDecorator extends EBookRepositoryDecorator{
    private Cache<EBook> cache;

    public EBookRepositoryCacheDecorator(EBookRepository audioBookRepository, Cache<EBook> cache) {
        super(audioBookRepository);
        this.cache = cache;
    }

    @Override
    public List<EBook> findAllEBooks() {
        if (cache.hasResult()) {
            return cache.load();
        }
        List<EBook> books = decoratedRepository.findAllEBooks();
        cache.save(books);
        return books;
    }

    @Override
    public Optional<EBook> findEBookById(Long id) {
        return decoratedRepository.findEBookById(id);
    }

    @Override
    public boolean saveEBook(EBook eBook) {
        cache.invalidateCache();
        return decoratedRepository.saveEBook(eBook);
    }

    @Override
    public void removeAllEBooks() {
        cache.invalidateCache();
        decoratedRepository.removeAllEBooks();
    }
}
