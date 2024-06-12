package org.example;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        WendysPage wendys = new WendysPage(driver);
        wendys.acceptCookies();
        wendys.selectLocation("32819");
        wendys.selectCategory("Combos");
        wendys.selectItem(new String[]{"Spicy Chicken Sandwich Combo"});
        wendys.selectDrink(new String[]{"Coca-Cola Freestyle", "Dr Pepper™", "No Flavor"});
        wendys.customize.add("Ketchup");
        wendys.customize.remove(new String[]{"Mayonnaise","Lettuce","Tomato"});
    }
}

class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

class Vector {
    private double i;
    private double j;
    private double k;

    public Vector (double i, double j, double k) {
        this.i = i;
        this.j = j;
        this.k = k;
    }

    public Vector  crossProduct(Vector v) {
        return new Vector(this.j * v.k - this.k * v.j, this.k * v.i - this.i * v.k, this.i * v.j - this.j * v.i);
    }

    public double dotProduct(Vector v) {
        return this.i * v.i + this.j * v.j + this.k * v.k;
    }

    public double magnitude() {
        return Math.sqrt(this.i * this.i + this.j * this.j + this.k * this.k);
    }

    public Vector add(Vector v) {
        return new Vector(this.i + v.i, this.j + v.j, this.k + v.k);
    }

    public Vector subtract(Vector v) {
        return new Vector(this.i - v.i, this.j - v.j, this.k - v.k);
    }

    public Vector scale(int scalar) {
        return new Vector(this.i * scalar, this.j * scalar, this.k * scalar);
    }

    public void print() {
        System.out.println("i: " + this.i + ", j: " + this.j + ", k: " + this.k);
    }

}

