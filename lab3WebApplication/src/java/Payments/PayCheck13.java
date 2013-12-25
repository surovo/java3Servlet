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


/**
 *
 * @author novyjpolzovatel
 */
public class PayCheck13 extends AbstractPayment{
    
//    private final double levyValue = 13;

    public PayCheck13(double payment, String description, Date dateOfPayment) throws WrongNumberValueException {
        super(payment, description, dateOfPayment);
    }




}
