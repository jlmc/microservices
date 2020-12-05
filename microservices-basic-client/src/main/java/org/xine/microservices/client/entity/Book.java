package org.xine.microservices.client.entity;

public class Book {

    private Long id;

    private String title;

    private String isbn;

    private String author;

    private String description;

    private Double price;

    private Integer numberOfPages;

    private int version;

    protected Book() {
    }


    private Book(Long id, String title, String isbn, String author, String description, Double price,
            Integer numberOfPages) {
        super();
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.description = description;
        this.price = price;
        this.numberOfPages = numberOfPages;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getNumberOfPages() {
        return this.numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    protected int getVersion() {
        return this.version;
    }

    protected void setVersion(int version) {
        this.version = version;
    }

    public static class Builder {
        private Long id;
        private String title;
        private String isbn;
        private String author;
        private String description;
        private Double price;
        private Integer numberOfPages;

        private Builder() {
        }

        public static Builder init() {
            return new Builder();
        }

        public Book build() {
            return new Book(this.id, this.title, this.isbn, this.author, this.description, this.price,
                    this.numberOfPages);
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withIsbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        public Builder withAuthor(String author) {
            this.author = author;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withPrice(Double price) {
            this.price = price;
            return this;
        }

        public Builder withNumberOfPages(Integer numberOfPages) {
            this.numberOfPages = numberOfPages;
            return this;
        }
    }

}
