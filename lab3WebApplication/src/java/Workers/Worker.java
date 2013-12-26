/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Workers;

import interfaces.JSONObjectInterface;
import interfaces.LevyInterface;
import java.util.Collection;

import java.util.Date;
import java.util.Iterator;
import java.util.TreeMap;
import Contract.AbstractContract;
import Contract.BudgetContract;
import Contract.FinanceContract;
import Exceptions.WrongNumberValueException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class Worker implements LevyInterface , JSONObjectInterface{
    
//    // Integration with the overall application
//enum WorkerType {
//    BUD,
//    CON
//}
    
    private String name = "Имя";
    private String surName = "Фамилия";
    private String middleName = "Отчество";
    static private long uniqueWorkerId = 0;
    boolean budget = true;
    private long currentWorkerId;
    private TreeMap contractsTable = new TreeMap();
     
    public Worker() {
        this.name = "";
        this.currentWorkerId = ++uniqueWorkerId;
    }
    
    public Worker(JSONObject obj) {
        this.loadParamsFromJson(obj);
        uniqueWorkerId = this.getUniqueWorkerId();
        ++uniqueWorkerId;
    }
     
    public Worker(String name) throws WrongNumberValueException {
        if (name == null || name.isEmpty() || name.replaceAll(" ", "").isEmpty()) {
            throw new WrongNumberValueException("Нельзя установить пустое имя для работника"); 
        }
        this.name = name;
        this.currentWorkerId = ++uniqueWorkerId;
    }
    
    public String getWorkerName() {
        return this.name;
    }
    
    public void setWorkerName(String name) throws WrongNumberValueException {
        if (name == null || name.isEmpty() || name.replaceAll(" ", "").isEmpty()) {
            throw new WrongNumberValueException("Нельзя установить пустое имя для работника"); 
        }
        this.name = name;
    }
    
    public String getWorkerSurName() {
        return this.surName;
    }
    
    public void setWorkerSurName(String name){
        if (name == null || name.isEmpty() || name.replaceAll(" ", "").isEmpty()) {
            this.surName = ""; 
        } else {
            this.surName = name; 
        }
    }
    
    
    public String getWorkerLastName() {
        return this.middleName;
    }
    
    public void setWorkerLastName(String name) {
        if (name == null || name.isEmpty() || name.replaceAll(" ", "").isEmpty()) {
            this.middleName = ""; 
        } else {
            this.middleName = name; 
        }
    }
    
    
    public void addContract(AbstractContract contract) throws WrongNumberValueException{
        if (contract == null) {
            throw new WrongNumberValueException("Попытка вставить null контракт в таблицу прервана");
        } else if (contract.getContractUniqueId() == 0 && this.contractsTable.get(Long.toString(contract.getContractUniqueId())) != null) {
            throw new WrongNumberValueException("Работник не может иметь два бюджетных контракта");
        }
        
        if(contract.getContractUniqueId() != 0) {
            this.budget = false;
        }
        
        this.contractsTable.put(Long.toString(contract.getContractUniqueId()), contract);
    }
    
    public AbstractContract getContractWithId(long id) throws WrongNumberValueException{ 
        
        AbstractContract tmpC = (AbstractContract) this.contractsTable.get(Long.toString(id));
        
        if (tmpC == null) {
            throw new WrongNumberValueException("Работник не имеет контракткта с таким id");
        }
        
        return tmpC; 
    }
    
    public Collection getAllContracts() {
        return this.contractsTable.values();
    }
    
    public void removeContractWithId(long id) throws WrongNumberValueException{ 
        
        if (((AbstractContract) this.contractsTable.get(Long.toString(id))) == null) {
            throw new WrongNumberValueException("Работник не имеет контракткта с таким id");
        } 
        
        this.contractsTable.remove(Long.toString(id));
        
        this.budget = !(!this.contractsTable.isEmpty() && this.contractsTable.get(Long.toString(0)) == null);
        
    }
    
    public String getType() {
        if (this.getAllContracts().isEmpty()) { 
            return "Не определен";
        }
        return this.isBudget() ? "Бюджетный" : "Контрактный";
    }
    
    public boolean isBudget() {
        try {
            return (this.getContractWithId(0)!=null && this.getAllContracts().size()>1);
        } catch (WrongNumberValueException ex) {
            return false;
        }
    }
    
    public long getUniqueWorkerId() {
        return this.currentWorkerId;
    }
    
    public String toString() {
        
        if(this.contractsTable.values().isEmpty()) {
            return "Работник: " + this.getWorkerName() + " с уникальным номером: " + this.getUniqueWorkerId() + " не имеет контрактов \n";
        }
        
        String value = "Работник: " + this.getWorkerName() + " с уникальным номером: " + this.getUniqueWorkerId() + " имеет следующие контракты \n";
        for (Iterator<AbstractContract> it = this.contractsTable.values().iterator(); it.hasNext();) {
            AbstractContract ac = it.next();
            value += ac.toString();
        }
        return value;
    }
    
    @Override
    public double getLevy() {
        double fullSumm = 0.;
        for (Iterator<AbstractContract> it = this.contractsTable.values().iterator(); it.hasNext();) {
            AbstractContract ac = it.next();
            fullSumm += ac.getLevy();
        }
        return fullSumm;
    }

    @Override
    public void setLevy(double newLevy) throws WrongNumberValueException {
        throw new WrongNumberValueException("В целях безопасности возможность установить единый налог для всех видов выплат для работника отключена");
    }

    @Override
    public double getTotalWithDate(Date start, Date end) {
        double fullSumm = 0.;
        for (Iterator<AbstractContract> it = this.contractsTable.values().iterator(); it.hasNext();) {
            AbstractContract ac = it.next();
            fullSumm += ac.getTotalWithDate(start, end);
        }
        return fullSumm;
    }

    @Override
    public double getRawSummWithDate(Date start, Date end) {
        double value = 0.;
       for (Iterator<AbstractContract> it = this.getAllContracts().iterator(); it.hasNext();) {
            AbstractContract c = it.next();
            value += c.getRawSummWithDate(start, end);
       }
       return value;
    }
    
    //===
        @Override
    public JSONObject getJSONObject() {
        JSONObject obj=new JSONObject();
              JSONArray list = new JSONArray();
        for (Iterator<AbstractContract> it = this.contractsTable.values().iterator(); it.hasNext();) {
            AbstractContract ac = it.next();
            list.add(ac.getJSONObject());
        }
              obj.put("contractsTable",list);
              obj.put("currentWorkerId",Long.toString(this.getUniqueWorkerId()));

              obj.put("middleName",this.middleName);
              obj.put("surName",this.surName);
              obj.put("name",this.name);
              obj.put("budget", this.budget);

              
              return obj;
    }

    @Override
    public void loadParamsFromJson(JSONObject obj) {
        JSONArray jsonContracts = (JSONArray) obj.get("contractsTable");
        for (Iterator<JSONObject> it = jsonContracts.iterator(); it.hasNext();) {
            JSONObject c = it.next();
            AbstractContract contract;
            if (Long.parseLong((String) c.get("id")) == 0) {
                contract = new BudgetContract(c);
                this.contractsTable.put(Long.toString(contract.getContractUniqueId()), contract);
            } else {
                contract = new FinanceContract(c);
                this.contractsTable.put(Long.toString(contract.getContractUniqueId()), contract);
            }
                
        }
      
          this.currentWorkerId = Long.parseLong((String)obj.get("currentWorkerId")); ;
          this.middleName = (String) obj.get("middleName");
          this.surName = (String) obj.get("surName");
          this.name = (String) obj.get("name");
    }

    
}
