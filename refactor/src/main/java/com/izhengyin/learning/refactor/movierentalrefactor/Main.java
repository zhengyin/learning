package com.izhengyin.learning.refactor.movierentalrefactor;

/**
 * @author zhengyin zhengyinit@outlook.com
 * Created on 2021/4/15 4:18 下午
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(movierental());
    }

    public static String movierental(){
        Movie movie1 = new ChildrenMovie("儿童片");
        Movie movie2 = new RegularMovie("普通片");
        Movie movie3 = new NewReleaseMovie("新片");
        //当新增一个分类时，需要新增加对应类
        Customer customer = new Customer("张三");
        customer.addRentals(new Rental(movie1,1));
        customer.addRentals(new Rental(movie2,3));
        customer.addRentals(new Rental(movie3,5));
        return customer.statement();
    }


}
