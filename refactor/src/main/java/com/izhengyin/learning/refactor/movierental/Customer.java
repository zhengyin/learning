package com.izhengyin.learning.refactor.movierental;

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
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        String result = "Rental Record for "+ getName()+" \n";
        Enumeration<Rental> elements = rentals.elements();
        while (elements.hasMoreElements()){
            double thisAmount = 0;
            Rental each = elements.nextElement();
            switch (each.getMovie().getPriceCode()){
                case Movie.REGULAR:
                        thisAmount += 2;
                        if(each.getDaysRented() > 2){
                            thisAmount += (each.getDaysRented() - 2) * 1.5;
                        }
                    break;
                case Movie.NEW_RELEASE:
                    thisAmount += each.getDaysRented();
                    break;
                case Movie.CHILDRENS:
                    thisAmount += 1.5;
                    if(each.getDaysRented() > 3){
                        thisAmount += (each.getDaysRented() - 3) * 1.5;
                    }
                    break;
                default:
                    //nothings todo
                    break;
            }
            // 添加租客积分
            frequentRenterPoints ++;
            if(each.getMovie().getPriceCode().equals(Movie.NEW_RELEASE) && each.getDaysRented() > 1){
                frequentRenterPoints ++;
            }
            // 显示当前租聘价格
            result += "\t" + each.getMovie().getTitle() + " \t" +  String.valueOf(thisAmount) +" \n";
            totalAmount += thisAmount;
        }
        // add footer lines
        result += "Amount owed is "+ String.valueOf(totalAmount) + "\n";
        result += "You earned "+ String.valueOf(frequentRenterPoints)+ " frequent renter points";
        return result;
    }
}