/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Payments;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Exceptions.WrongNumberValueException;
import org.json.simple.JSONObject;


/**
 *
 * @author novyjpolzovatel
 */
public class PayCheck13 extends AbstractPayment{
    
//    private final double levyValue = 13;

    public PayCheck13(double payment, String description, Date dateOfPayment) throws WrongNumberValueException {
        super(payment, description, dateOfPayment);
    }

    public PayCheck13(JSONObject ap) {
        super(ap);
    }

    @Override
    public void loadParamsFromJson(JSONObject obj) {
      
          this.paymentSum = Double.valueOf(obj.get("paymentSum").toString());
          this.levyValue =  Double.valueOf(obj.get("levyValue").toString());
          this.name = (String) obj.get("name");
          Date date;
        try {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            date = df.parse((String)obj.get("paymentDate"));
        } catch (ParseException ex) {
            date = new Date();
        }
          this.paymentDate = date ;


    }

}
