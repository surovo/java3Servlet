/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Contract;

import java.util.Date;
import static Contract.AbstractContract.contractUniqueId;
import Exceptions.WrongNumberValueException;


/**
 *
 * @author ivan
 */
public class FinanceContract extends AbstractContract{
    private long contractId;
    
    public FinanceContract(String workPlace, String post) {
        super(workPlace, post);
        this.contractId = contractUniqueId;
    }
    
    public FinanceContract(String workPlace, String post, Date validDate) throws WrongNumberValueException {
        super(workPlace, post, validDate);
        this.contractId = contractUniqueId;
    }

    @Override
    public long getContractUniqueId() {
        return this.contractId;
    }

    @Override
    public String getType() {
        return "Не бюджетный № " + this.getContractUniqueId();
    }

   

}
