/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Contract;

import java.util.Date;
import static Contract.AbstractContract.contractUniqueId;
import Exceptions.WrongNumberValueException;
import Payments.PayCheck13;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/**
 *
 * @author ivan
 */
public class FinanceContract extends AbstractContract{
    private long contractId;
    
    public FinanceContract(JSONObject obj) {
        super (obj);
        contractUniqueId = this.getContractUniqueId();
        ++contractUniqueId;
    }
        
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

    
    @Override
    public void loadParamsFromJson(JSONObject obj) {
        JSONArray jsonPayments = (JSONArray) obj.get("payments");
        for (Iterator<JSONObject> it = jsonPayments.iterator(); it.hasNext();) {
            JSONObject ap = it.next();
            this.payments.add(new PayCheck13(ap));
        }
      
          this.contractId = Long.parseLong(obj.get("id").toString());
          this.workPlace = (String) obj.get("workPlace");
          this.post = (String) obj.get("post");
          this.validDate = (Date) obj.get("validDate");
          this.forever = Boolean.parseBoolean(obj.get("forever").toString());
    }
   

}
