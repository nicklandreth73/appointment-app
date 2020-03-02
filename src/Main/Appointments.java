/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

/**
 *
 * @author Nick
 */
public class Appointments {
 
    
    String customer,phone,title,description,start,end,type;

    public Appointments(String customer, String phone, String title, String description, String start, String end) {
        this.customer = customer;
        this.phone = phone;
        this.title = title;
        this.description = description;
        this.start = start;
        this.end = end;
    }

    public String getCustomer() {
        return customer;
    }

    public String getPhone() {
        return phone;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }
    
    
}
