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
public class AppointmentWithType {
    String type,customer,phone,title,description,start,end;
        public AppointmentWithType(String customer, String phone, String title, String description, String start, String end, String type) {
        this.customer = customer;
        this.phone = phone;
        this.title = title;
        this.description = description;
        this.start = start;
        this.end = end;
        this.type = type;
    }

    public AppointmentWithType(String string, String string0, String string1, String string2, String sqlToLocalTime, String sqlToLocalTime0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
        
}
