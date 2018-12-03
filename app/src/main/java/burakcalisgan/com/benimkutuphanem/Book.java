package burakcalisgan.com.benimkutuphanem;

public class Book {
    private String id;
    private String bookName;
    private String author;
    private String numberOfPages;

    public Book(String id, String bookName, String author, String numberOfPages) {
        this.id = id;
        this.bookName = bookName;
        this.author = author;
        this.numberOfPages = numberOfPages;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(String  numberOfPages) {
        this.numberOfPages = numberOfPages;
    }
}
