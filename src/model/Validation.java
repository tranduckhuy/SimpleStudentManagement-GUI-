
package model;

import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author ADMIN
 */
public class Validation {
    
    private static Scanner sc = new Scanner(System.in);
    
    public static String getStringNonBlank(String msg) {
        String n;
        while(true) {
            n = sc.nextLine().trim();
            if(!n.isEmpty()) {
                break;
            }
            JOptionPane.showMessageDialog(null, msg + "must not be empty!");
        }
        return n.replaceAll("\\s+", " ");
    }
    
    public static String getStringPattern(String msg, String pattern) {
        String n;
        while(true) {
            n = sc.nextLine().trim();
            if(!n.isEmpty() && n.matches(pattern)) {
                break;
            }
            JOptionPane.showMessageDialog(null, msg + "is invalid!");
        }
        return n.replaceAll("\\s+", " ");
    }
    
}
