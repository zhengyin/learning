package com.izhengyin.learning.refactor.movierentalrefactor;



import java.util.Enumeration;
import java.util.Vector;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2021/4/12 12:28 下午
 */
public class Customer {
    private String name;
    private Vector<Rental> rentals = new Vector<>();
    public Customer(String name) {
        this.name = name;
    }
    public void addRentals(Rental rental) {
        this.rentals.add(rental);
    }
    public String getName() {
        return name;
    }

    public String statement(){
        String result = "Rental Record for "+ getName()+" \n";
        Enumeration<Rental> elements = rentals.elements();
        while (elements.hasMoreElements()){
            Rental each = elements.nextElement();
            // 显示当前租聘价格
            result += "\t" + each.getMovie().getTitle() + " \t" +  String.valueOf(each.getRentAmount()) +" \n";
        }
        // add footer lines
        result += "Amount owed is "+ String.valueOf(getTotalAmount()) + "\n";
        result += "You earned "+ String.valueOf(getFrequentRenterPoints())+ " frequent renter points";
        return result;
    }

    private double getTotalAmount(){
        return rentals.stream()
                .mapToDouble(Rental::getRentAmount)
                .sum();
    }

    private int getFrequentRenterPoints(){
        return rentals.stream()
                .mapToInt(Rental::getFrequentRenterPoint)
                .sum();
    }
}