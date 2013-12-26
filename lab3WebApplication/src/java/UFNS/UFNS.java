/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UFNS;

import Workers.Worker;
import interfaces.JSONObjectInterface;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Observable;
import java.util.TreeMap;
import Contract.AbstractContract;
import Exceptions.WrongNumberValueException;
import Payments.AbstractPayment;
import Payments.PayCheck13;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;

/**
 *
 * @author ivan
 */

//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;
//
//@ManagedBean(name = "userData", eager = true)
//@SessionScoped

public class UFNS extends Observable implements  JSONObjectInterface{
    private static volatile UFNS instance;
    private TreeMap workersTable = new TreeMap();
    public static UFNS getInstance() {
        UFNS localInstance = instance;
        if (localInstance == null) {
            synchronized (UFNS.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new UFNS();
                }
            }
        }
        return localInstance;
    }
    
    public void addWorkerWithName(String workerName) throws WrongNumberValueException {
        Worker w = new Worker(workerName);
        this.addWorker(w);
        setChanged(); // the two methods of Observable class
        notifyObservers(w);
    }
    
    private void addWorker(Worker newWorker) throws WrongNumberValueException{
        if (newWorker == null) {
            throw new WrongNumberValueException("Попытка вставить null пользователя в таблицу прервана");
        } else if (this.workersTable.get(newWorker.getUniqueWorkerId()) != null) {
            throw new WrongNumberValueException("Работник с таким id уже есть в таблице");
        }
        
        this.workersTable.put(newWorker.getUniqueWorkerId(), newWorker);
        this.notifyObservers();
    }
    
    public Worker getWorkerWithId(long id) throws WrongNumberValueException{ 
        
        Worker tmpW = (Worker) this.workersTable.get(id);
        
        if (tmpW == null) {
            throw new WrongNumberValueException("Работник с id: " + id + " отсутствует в таблице");
        }
        
        return tmpW; 
    }
    
    public Collection getAllWorkers() {
        return this.workersTable.values();
    }
    
    public Collection getAllContractsForWorkerWithId(long id) throws WrongNumberValueException {
        return this.getWorkerWithId(id).getAllContracts();
    }
    
    public void removeWorkerWithId(long id) throws WrongNumberValueException {
        Worker tmpWorker = this.getWorkerWithId(id);
        this.workersTable.remove(tmpWorker.getUniqueWorkerId());
        setChanged();
        notifyObservers(tmpWorker);
    }
    
    public void addContractToWorkerWithId(long id, AbstractContract newContract) throws WrongNumberValueException {
        Worker tmpWorker = this.getWorkerWithId(id);
        this.getWorkerWithId(id).addContract(newContract);
        setChanged();
        notifyObservers(newContract);
    }
    
    public void removeContractFromWorkerWithId(long id, long contractId) throws WrongNumberValueException {
        Worker tmpWorker = this.getWorkerWithId(id);
        AbstractContract contract = this.getWorkerWithId(id).getContractWithId(contractId);
        tmpWorker.removeContractWithId(contract.getContractUniqueId());
        setChanged();
        notifyObservers(contract);
    }
    
    public void addPayCheck13ToWorkerWithIdAndContractId(long workerId, long contractId, Date pDate, double sum, double levy, String pName) throws WrongNumberValueException {
        Worker w = this.getWorkerWithId(workerId);
        AbstractContract c = w.getContractWithId(contractId);
        AbstractPayment p = new PayCheck13(sum, pName, pDate);
        p.setLevy(levy);
        c.addPayment(p);
        setChanged();
        notifyObservers(p);
    }
    
    public void clearAll() {
        this.workersTable.clear();
        setChanged();
        notifyObservers();
    }
    
    //================================================================

    @Override
    public JSONObject getJSONObject() {
       JSONObject obj=new JSONObject();
              JSONArray list = new JSONArray();
        for (Iterator<Worker> it = this.getAllWorkers().iterator(); it.hasNext();) {
            Worker w = it.next();
            list.add(w.getJSONObject());
        }
              obj.put("workers",list);
              return obj;
    }
    
    public void readFromJSON(String filename) throws WrongNumberValueException  {
        JSONParser parser = new JSONParser();
 
	try {
 
		Object obj = parser.parse(new FileReader(filename));
 
		JSONObject jsonObject = (JSONObject) obj;
 
		this.loadParamsFromJson(jsonObject);
 
 
	} catch (FileNotFoundException e) {
//		e.printStackTrace();
            this.clearAll();
            throw new WrongNumberValueException("Не удалось прочитать из файла");
	} catch (IOException e) {
            this.clearAll();
            throw new WrongNumberValueException("Не удалось прочитать из файла");
        
	} catch (org.json.simple.parser.ParseException e) {
            this.clearAll();
            throw new WrongNumberValueException("Не удалось прочитать из файла");
            
//            e.printStackTrace();
	}
    }

    @Override
    public void loadParamsFromJson(JSONObject obj)  {
        JSONArray jsonWorkers = (JSONArray) obj.get("workers");
        for (Iterator<JSONObject> it = jsonWorkers.iterator(); it.hasNext();) {
            JSONObject w = it.next();
            Worker newWorker = new Worker(w);
            try {
                this.addWorker(newWorker);
            } catch (WrongNumberValueException ex) {
                ex.printStackTrace();
            }
        }
        setChanged();
        notifyObservers();
    }
    
    

}
