/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Contract;

import java.util.Date;
import Exceptions.WrongNumberValueException;



/**
 *
 * @author ivan
 */
public class BudgetContract extends AbstractContract{
    

    
    public BudgetContract(String workPlace, String post) {
        super(workPlace, post);
    }
    
    public BudgetContract(String workPlace, String post, Date validDate) throws WrongNumberValueException {
        super(workPlace, post, validDate);
    }

    @Override
    public long getContractUniqueId() {
        return 0;
    }

    @Override
    public String getType() {
        return "Бюджетный";
    }

   



}
