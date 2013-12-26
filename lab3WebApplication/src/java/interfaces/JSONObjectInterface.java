/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import Exceptions.WrongNumberValueException;
import org.json.simple.JSONObject;

/**
 *
 * @author ivan
 */
public interface JSONObjectInterface {
    
    public JSONObject getJSONObject();
    
    /**
     *
     * @return
     */
    public void loadParamsFromJson(JSONObject obj);
}
