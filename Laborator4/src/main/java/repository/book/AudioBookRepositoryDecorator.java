package repository.book;

public abstract class AudioBookRepositoryDecorator implements AudioBookRepository{
    protected AudioBookRepository decoratedRepository;

    public AudioBookRepositoryDecorator(AudioBookRepository audioBookRepository) {
        this.decoratedRepository = audioBookRepository;
    }
}
