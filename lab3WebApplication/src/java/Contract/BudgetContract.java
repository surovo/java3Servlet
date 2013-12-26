/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Contract;

import java.util.Date;
import Exceptions.WrongNumberValueException;
import Payments.AbstractPayment;
import Payments.PayCheck13;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;



/**
 *
 * @author ivan
 */
public class BudgetContract extends AbstractContract{
    
    public BudgetContract(JSONObject obj) {
        super (obj);
        contractUniqueId = this.getContractUniqueId();
        ++contractUniqueId;
    }
    
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

   
     @Override
    public void loadParamsFromJson(JSONObject obj) {

        JSONArray jsonPayments = (JSONArray) obj.get("payments");
        for (Iterator<JSONObject> it = jsonPayments.iterator(); it.hasNext();) {
            JSONObject ap = it.next();
            AbstractPayment newPay = new PayCheck13(ap);
            this.payments.add(newPay);
        }
      
          
          this.workPlace = (String) obj.get("workPlace");
          this.post = (String) obj.get("post");
          this.validDate = (Date) obj.get("validDate");
          this.forever = Boolean.parseBoolean( (String) obj.get("forever"));

    }



}
