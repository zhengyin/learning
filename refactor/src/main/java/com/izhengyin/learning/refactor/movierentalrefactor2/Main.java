package com.izhengyin.learning.refactor.movierentalrefactor2;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2021/4/15 4:35 下午
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(movierental());
    }

    public static String movierental(){
        Movie movie1 = new Movie("儿童片", PriceCode.CHILDRENS);
        Movie movie2 = new Movie("普通片", PriceCode.REGULAR);
        Movie movie3 = new Movie("新片", PriceCode.NEW_RELEASE);
    //    Movie movie4 = new Movie("恐怖片", PriceCode.REGULAR);
    //    Movie movie5 = new Movie("纪录片", PriceCode.DOCUMENTARY);
        Customer customer = new Customer("张三");
        customer.addRentals(new Rental(movie1,1));
        customer.addRentals(new Rental(movie2,3));
        customer.addRentals(new Rental(movie3,5));
        return customer.statement();
    }
}
