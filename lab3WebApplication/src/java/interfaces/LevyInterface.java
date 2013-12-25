/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import java.util.Date;
import Exceptions.WrongNumberValueException;
import java.util.Calendar;
/**
 *
 * @author novyjpolzovatel
 */
public interface LevyInterface {
    
    /**
     * 
     * @return возвращает налог по данной выплате 
     */
    public double getLevy();
    
    /**
     * 
     * @param newLevy новое значение налога
     * @throws WrongNumberValueException 
     */
    public void setLevy(double newLevy) throws WrongNumberValueException; 
    
     /**
     * 
     * @return возвращает сумму выплаты с учетом налога
     */
    public double getTotalWithDate(Date start, Date end);
    
    public double getRawSummWithDate(Date start, Date end);
}
