package dev.likelion.momeal.dto;

public class MenuDto {
    private Long menuId;
    private String title;
    private int price;
    private String picture;
    private String restaurant;

    public MenuDto() {
    }

    public MenuDto(Long menuId, String title, int price, String picture, String restaurant) {
        this.menuId = menuId;
        this.title = title;
        this.price = price;
        this.picture = picture;
        this.restaurant = restaurant;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "MenuDto{" +
                "menuId=" + menuId +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", picture='" + picture + '\'' +
                ", restaurant='" + restaurant + '\'' +
                '}';
    }
}
