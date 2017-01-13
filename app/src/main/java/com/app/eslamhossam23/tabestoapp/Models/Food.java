package com.app.eslamhossam23.tabestoapp.Models;

/**
 * Created by Eslam on 0011 11/01/2017.
 */

public class Food {
    private String name;
    private String description;
    private int idImage;
    private double price;

    public Food(String name, int idImage, double price) {
        this.name = name;
        this.idImage = idImage;
        this.price = price;
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum a ipsum eget arcu fringilla malesuada nec ut nulla. Vestibulum mattis nunc lectus, a porta nisi dignissim a. Sed scelerisque ac justo in vulputate. Vestibulum risus ipsum, egestas ut facilisis eu, dapibus quis ligula. Curabitur ligula tortor, luctus et ante et, condimentum condimentum arcu. Quisque eu pellentesque arcu. Duis interdum accumsan rhoncus. Donec luctus in dolor quis efficitur. Nunc sollicitudin porttitor quam non vestibulum. Aliquam ultricies, orci eu commodo efficitur, augue velit rutrum eros, id tincidunt ante est eu metus. Nunc mollis vestibulum ex at maximus. Praesent in luctus risus, non dapibus ligula.";
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

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Food{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", idImage=" + idImage +
                '}';
    }
}
