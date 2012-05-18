package rfid;

import java.applet.Applet;
import java.awt.Graphics;
import java.io.IOException;
import java.util.Properties;

import netscape.javascript.JSException;
import netscape.javascript.JSObject;

import br.com.rfidcoe.yarf.device.RFIDReaderFactory;
import br.com.rfidcoe.yarf.device.reader.RFIDReader;

public class RFIDAccess extends Applet {
    
    public String toDraw;
    public Properties readerProperties;
    public RFIDReader rfidReader;
    private JSObject jso;
    public String idRead;

    @Override
    public void init() {
        
    }

    @Override
    public void start() {
        
        readerProperties=new Properties();
        readerProperties.setProperty("yarf.device.model", "MERCURY5");
        readerProperties.setProperty("reader.power", "3000");
        readerProperties.setProperty("yarf.device.host", "10.102.10.88");
        
        rfidReader = new RFIDReaderFactory().createReader(readerProperties);
        
        connect();
        
        toDraw="initdone";
        
        try {
            jso = JSObject.getWindow(this);
        } catch (JSException e) {
            // TODO Auto-generated catch block
            toDraw="error jsobject";
        }
        
        
        repaint();
    }
    
    public void paint(Graphics g)
    { 
//method to draw text on screen 
// String first, then x and y coordinate. 
     g.drawString("Hey "+ toDraw,20,20); 

    }
    
    public void read() {
        String[] test;
        try {
            test = rfidReader.readEPC();
            System.out.println(test.length);
            for (int i = 0; i < test.length; i++) {
                toDraw=test[i];
                idRead=toDraw;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        repaint();
        
        printResult();
    }
    
    public void connect() {
        try {
            rfidReader.connect();
            rfidReader.setPower(Integer.valueOf(readerProperties
                    .getProperty("reader.power")));
        } catch (IOException ex) {
            
        }
    }
    
    public void printResult() {
        try {
            jso.call("printResult", new String[] {String.valueOf(idRead)});
        }
        catch (Exception ex) {
                ex.printStackTrace();
        }
    }
    
    

}
