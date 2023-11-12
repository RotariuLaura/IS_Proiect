package model;

public class AudioBook extends Book{
    private int runTime;

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }
    @Override
    public String toString(){
        return String.format("Audio Book author: %s | Title: %s | Published Date: %s | Run time: %s.", this.getAuthor(), this.getTitle(), this.getPublishedDate(), this.getRunTime());
    }
}
