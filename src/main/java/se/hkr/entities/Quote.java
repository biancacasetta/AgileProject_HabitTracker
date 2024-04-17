package se.hkr.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Quote")
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(nullable = false)
    private String text;

    @ManyToOne (optional = false)
    private Category category;

    public Quote (int Id, String text) {
        this.Id = Id;
        this.text = text;
    }

    public Quote () {

    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
