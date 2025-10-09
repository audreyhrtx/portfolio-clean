package com.porfolio.portfolio.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class Paint {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int idPaint;
    private String name;
    @Column(length = 8000)
    private String description;
    @OneToMany(mappedBy = "paint", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaintImage> imagesUrl;
    private String category;
    private String dateCreation;
    private double price;
    @ElementCollection
    @CollectionTable(name = "paint_dimensions", joinColumns = @JoinColumn(name = "id_paint"))
    @Column(name = "dimension")
    private List<String> dimensions;
    private String status; // "available", "sold",

    @Column(nullable = false, columnDefinition = "integer default 1")
    private int quantity = 1;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getIdPaint() {
        return idPaint;
    }

    public void setIdPaint(int idPaint) {
        this.idPaint = idPaint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PaintImage> getImagesUrl() {
        return imagesUrl;
    }

    public void setImagesUrl(List<PaintImage> imagesUrl) {
        this.imagesUrl = imagesUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<String> getDimensions() {
        return dimensions;
    }

    public void setDimensions(List<String> dimensions) {
        this.dimensions = dimensions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Paint [idPaint=" + idPaint + ", name=" + name + ", description=" + description + ", imagesUrl="
                + imagesUrl + ", category=" + category + ", dateCreation=" + dateCreation + ", price=" + price
                + ", dimensions=" + dimensions + ", status=" + status + "quantity" + quantity + "]";
    }

    public double getTotalPrice() {
        return price * quantity;
    }

}
