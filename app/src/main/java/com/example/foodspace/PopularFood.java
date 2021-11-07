package com.example.foodspace;

public class PopularFood {
    String name;
    String price;
    String typeFood;
    String halRest;
    String rating;
    Integer foodImageURl;
    public PopularFood(String rating,String name,String price,String typeFood,String halRest,Integer foodImageURl)
    {
        this.name=name;
        this.price=price;
        this.typeFood=typeFood;
        this.halRest=halRest;
        this.rating=rating;
        this.foodImageURl=foodImageURl;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTypeFood() {
        return typeFood;
    }

    public void setTypeFood(String typeFood) {
        this.typeFood = typeFood;
    }

    public String getHalRest() {
        return halRest;
    }

    public void setHalRest(String halRest) {
        this.halRest = halRest;
    }

    public Integer getFoodImageURl() {
        return foodImageURl;
    }

    public void setFoodImageURl(Integer foodImageURl) {
        this.foodImageURl = foodImageURl;
    }
}
