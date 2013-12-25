/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kozodoylab3var19.Exceptions;
import java.io.Serializable;

/**
 *
 * @author novyjpolzovatel
 */
public class WrongNumberValueException extends Exception implements Serializable{
    
    public WrongNumberValueException(String str) {
        super(str); //To change body of generated methods, choose Tools | Templates.
    }

    WrongNumberValueException() {
        super(); //To change body of generated methods, choose Tools | Templates.
    }
}
