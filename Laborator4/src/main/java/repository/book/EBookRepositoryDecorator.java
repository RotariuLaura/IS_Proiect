package repository.book;

public abstract class EBookRepositoryDecorator implements EBookRepository{
    protected EBookRepository decoratedRepository;

    public EBookRepositoryDecorator(EBookRepository bookRepository)
    {
        this.decoratedRepository = bookRepository;
    }
}
