/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UserMenu;

import interfaces.ExecInterface;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 *
 * @author ivan
 */
abstract public class SS_Menu implements ExecInterface{
    protected String menuDescription ="no description";
    protected HashMap map = new HashMap();
   
    
    public SS_Menu(String description) {
        this.menuDescription = description; 
    }
    
    /**
     *
     * @param key
     * @param sub
     */
    public final void ss_insertSubMenuForKey(int key, SS_Menu sub) {
        map.put(key, sub);
    }
    

    public String getString() {
        BufferedReader myReader = new BufferedReader(new InputStreamReader(System.in));
        String str="";
        try {
            str = myReader.readLine();
        } catch (Exception ioEx) {
            System.err.println("Input value error");
//            ioEx.printStackTrace();
            str = "";
        } 
        
        return str;
    }

    
    public int getInt() {
        BufferedReader myReader = new BufferedReader(new InputStreamReader(System.in));
        String str="";
        int retValue = 0;
        try {
            str = myReader.readLine();
            retValue = Integer.parseInt(str);
        } catch (IOException ioEx) {
            System.err.println("Input value error");
        } catch (NumberFormatException nfEx) { 
            System.err.println("number format Exeption");
        }
        
        return retValue;
    }

    
    public void showMeString(String str) {
        System.out.print(str);
    }

    
    public String toString() {
        return this.menuDescription;
    }
}
