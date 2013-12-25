/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Contract;

import interfaces.JSONObjectInterface;
import interfaces.LevyInterface;
//import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import Exceptions.WrongNumberValueException;
import Payments.AbstractPayment;

/**
 *
 * @author novyjpolzovatel
 */
public abstract class AbstractContract implements LevyInterface{
    static long contractUniqueId = 0;
    public List<AbstractPayment> payments;
    protected String workPlace = "";
    protected String post = "";
    protected Date validDate;
    protected boolean forever = false;
    
    
    /**
     * 
     * @param workPlace - место работы
     * @param post - должность
     */
    public AbstractContract(String workPlace, String post) {
        this.workPlace = workPlace;
        this.post = post;
        this.payments = new ArrayList<AbstractPayment>();
        contractUniqueId++;
        this.forever = true;
    }
    /**
     * 
     * @param workPlace - место работы
     * @param post - должность
     * @param validDate - дата окончания контракта
     * @throws WrongNumberValueException 
     */
   public AbstractContract(String workPlace, String post, Date validDate) throws WrongNumberValueException {
       
        if (validDate.compareTo(new Date())<=0) {
            throw new WrongNumberValueException("Невозможно создать контракт с указанным сроком действия");
        } else if (post.length() == 0) {
            throw new WrongNumberValueException("Невозможно создать контракт без указания должности");
        }
       
        this.workPlace = workPlace;
        this.post = post;
        this.payments = new ArrayList<AbstractPayment>();
        contractUniqueId++;
        this.forever = false;
    }
   
   abstract public long getContractUniqueId();
   
   public void addPayment(AbstractPayment p) throws WrongNumberValueException {
       if (p == null) {
            throw new WrongNumberValueException("Попытка вставить null выплату в таблицу прервана");
        } 
       
       this.payments.add(p);
   }
   
   public Collection getAllPaymentsWithTimePeriod(Date start, Date end) throws WrongNumberValueException {
       ArrayList<AbstractPayment> allPayments = new ArrayList<AbstractPayment>();
       for(Iterator<AbstractPayment> i = this.payments.iterator(); i.hasNext(); ) {
           AbstractPayment payment = i.next();
           
           if (payment.validForTimePeriod(start, end)) {
               allPayments.add(payment);
           }
       }
       
       return allPayments;
   }
   
   public String getPost() {
       return this.post;
   }
   
   public String getWorkPlace() {
       return this.workPlace;
   }

    //========================================================
   abstract public String getType();
   
    @Override
   public String toString() {
       String value = "";
       if (this.getContractUniqueId() == 0) {
           value = " Бюджетный контракт ";
       } else {
           value = " Контракт с уникальным номером " + this.getContractUniqueId();
       } 
       
       value = "по должности " + this.post + " и месту работы:" + this.workPlace + " имеет следующие выплаты: \n";
       for (Iterator<AbstractPayment> it = this.payments.iterator(); it.hasNext();) {
            AbstractPayment ap = it.next();
            value += ap.toString()+"\n";
        }
       
        return value;
       
   }

    @Override
    public double getTotalWithDate(Date start, Date end) {
        double fullSumm = 0.;
        for (Iterator<AbstractPayment> it = this.payments.iterator(); it.hasNext();) {
            AbstractPayment ap = it.next();
            fullSumm += ap.getTotalWithDate(  start,   end);
        }
        return fullSumm;
    }

    @Override
    public double getLevy() {
        double fullLevySumm = 0.;
        
        for (Iterator<AbstractPayment> it = this.payments.iterator(); it.hasNext();) {
            AbstractPayment ap = it.next();
            fullLevySumm += ap.getRawSum() - ap.getTotalWithDate(null , new Date());
        }
        
        return fullLevySumm;
    }

    @Override
    public void setLevy(double newLevy) throws WrongNumberValueException {
        if (newLevy >= 100) {
            throw new WrongNumberValueException("Налог более 99% недопустим!");
        } else if (newLevy<0) {
            throw new WrongNumberValueException("Налог менее 0% недопустим!");
        }
                
        for (AbstractPayment ap : this.payments) {
            ap.setLevy(newLevy);
        }
    }
    
    @Override
    public double getRawSummWithDate(Date start, Date end) {
        double returnValue = 0.;
        for (Iterator<AbstractPayment> it = this.payments.iterator(); it.hasNext();) {
            AbstractPayment ap = it.next();
            returnValue += ap.getRawSummWithDate( start,  end);
        }
        return returnValue;
    }
    
       
    
}
