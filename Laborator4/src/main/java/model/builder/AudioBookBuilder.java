package model.builder;

import model.AudioBook;
import model.Book;

import java.time.LocalDate;

public class AudioBookBuilder extends BookBuilder{
    private AudioBook audioBook;

    public AudioBookBuilder(){
        audioBook = new AudioBook();
    }
    public AudioBookBuilder setId(Long id){
        audioBook.setId(id);
        return this;
    }
    public AudioBookBuilder setAuthor(String author){
        audioBook.setAuthor(author);
        return this;
    }
    public AudioBookBuilder setTitle(String title){
        audioBook.setTitle(title);
        return this;
    }
    public AudioBookBuilder setPublishedDate(LocalDate date){
        audioBook.setPublishedDate(date);
        return this;
    }
    public AudioBookBuilder setRunTime(int runTime){
        audioBook.setRunTime(runTime);
        return this;
    }
    @Override
    public AudioBook build(){
        return audioBook;
    }
}
