package com.whatsoever.oskar.pizzaapp;

/**
 * Created by Oskar on 13.12.2016.
 */

public class Pizza {

    private String name;
    private double price;
    private boolean meat;
    private boolean fish;

    public Pizza (String name, double price, boolean meat, boolean fish){
        this.setName(name);
        this.setPrice(price);
        this.setMeat(meat);
        this.setFish(fish);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isMeat() {
        return meat;
    }

    public void setMeat(boolean meat) {
        this.meat = meat;
    }

    public boolean isFish() {
        return fish;
    }

    public void setFish(boolean fish) {
        this.fish = fish;
    }



    @Override
    public String toString () {
        return this.getName() + " " + this.getPrice();//<- price is float.. check if it works !!
    }
}
