package com.hialan.hv.hibernate3.search.model;

/**
 * Created with IntelliJ IDEA.
 * User: Alan Yang
 * Date: 27/4/13
 * Time: 5:21 PM
 */
public class Customer {

    private Long id;
    private String name;

    private User loginUser;

    private CustomerType customerType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public User getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }
}
