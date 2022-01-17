package domain;

public class Movie {
    private String title;
    private String imdbId;
    private int year;
    private String poster_url;



    public Movie(String title, String imdbId, Integer year, String poster_url) {
        this.title = title;
        this.imdbId = imdbId;
        this.year = year;
        this.poster_url = poster_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getPoster_url() {
        return poster_url;
    }

    public void setPoster_url(String poster_url) {
        this.poster_url = poster_url;
    }
}
