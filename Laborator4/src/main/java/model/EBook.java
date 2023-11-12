package model;

public class EBook extends Book {
    private String format;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
    @Override
    public String toString(){
        return String.format("EBook author: %s | Title: %s | Published Date: %s | Format: %s.", this.getAuthor(), this.getTitle(), this.getPublishedDate(), this.getFormat());
    }
}
