/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



package Payments;
import interfaces.JSONObjectInterface;
import interfaces.LevyInterface;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import Exceptions.WrongNumberValueException;
import org.json.simple.JSONObject;
//import org.json.simple.JSONObject;

/**
 *
 * @author Kozodoy Ivan
 */
  abstract public class AbstractPayment implements LevyInterface , JSONObjectInterface{
    
    protected double paymentSum = 0.;
    protected double levyValue = 0.;
    protected String name = "Выплата без описания";
    protected Date paymentDate;
    
    /**
     * 
     * @param payment - сумма выплаты
     * @param description - описание выплаты
     * @throws WrongNumberValueException 
     */
    
    public AbstractPayment(JSONObject obj) {
        this.loadParamsFromJson(obj);
    }
    
    
    public AbstractPayment(double payment, String description, Date dateOfPayment) throws WrongNumberValueException {
        
        if (payment <=0) {
            throw new WrongNumberValueException("Невозможно создать отрицательную или нулевую выплату");
        } else if (dateOfPayment == null) {
            throw new WrongNumberValueException("Невозможно создать выплату без даты"); 
        }
        
        this.paymentSum = payment;
        this.paymentDate = dateOfPayment;
        this.name = description;
    }

    /**
     * 
     * @return возвращает сумму выплаты без учета налога
     */
    public double getRawSum() {
        return this.paymentSum;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Date getDate () {
        return this.paymentDate;
    }
    
    public boolean validForTimePeriod (Date start, Date end) throws WrongNumberValueException {
        boolean valid = true;
        if (start == null && end == null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime ( this.getDate() ); // convert your date to Calendar object
            int daysToDecrement = -1;
            cal.add(Calendar.DATE, daysToDecrement);
            start = cal.getTime(); //
            
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime ( this.getDate() ); // convert your date to Calendar object
            int daysToIncrement = 1;
            cal1.add(Calendar.DATE, daysToIncrement);
            end = cal1.getTime(); //
        } else if (start != null && end == null) {
            valid = this.getDate().after(start);
        } else if (start == null && end != null) {
            valid = this.getDate().before(end);
        } else if (start.after(end)) {
            valid = false;
            throw new WrongNumberValueException("Начальная дата должна быть раньше конечной"); 
        } else {
            valid = this.getDate().after(start) && this.getDate().before(end); 
        }
        
        return valid;
        
        
    }

    
    @Override
    public String toString() {
        return this.name + " со значением налога %: " + this.getLevy() + " и суммой: " + this.getRawSum() + "\n Итого: " + this.getTotalWithDate(null,null) + " - "  + this.paymentDate;
    }
    
    //========================================================
    @Override
    public double getLevy() {
        return this.levyValue;
    }
    @Override
    public void setLevy(double newLevy) throws WrongNumberValueException {
        if (newLevy < 0 || newLevy >=100) {
            throw new WrongNumberValueException("Невозможно создать выплату с данным значением налога");
        }
        this.levyValue = newLevy;
    }    
    @Override
    public double getRawSummWithDate(Date start, Date end) {
        try {
            if (this.validForTimePeriod(start, end)) {
                return this.getRawSum();
            }
        } catch (WrongNumberValueException ex) {
            return 0.;
        }
        return 0.;
    }
    @Override
    public double getTotalWithDate(Date start, Date end) {
        try {
            if (this.validForTimePeriod(start, end)) {
                double value = this.getRawSum() - this.getLevy()*0.01*this.getRawSum();
                return value;
            }
            
            return 0.;
            
        } catch (WrongNumberValueException ex) {
            return 0.;
        }

    }
    
    @Override
    public JSONObject getJSONObject() {
          JSONObject obj=new JSONObject();
          DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
          obj.put("paymentSum",this.paymentSum);
          obj.put("levyValue",this.levyValue);
          obj.put("name",this.name);
          obj.put("paymentDate",df.format(this.paymentDate));

          return obj;
    }

}
