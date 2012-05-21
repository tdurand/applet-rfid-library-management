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
        
        try {
            jso = JSObject.getWindow(this);
        } catch (JSException e) {
            // TODO Auto-generated catch block
        }
    }
    
    public void read() {
        String[] test;
        try {
            test = rfidReader.readEPC();
            System.out.println(test.length);
            for (int i = 0; i < test.length; i++) {
                idRead=test[i];
                System.out.println("idRead: "+idRead);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            idRead="error";
            e.printStackTrace();
        }
        
        printResult();
        readResult();
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
    
    public void readResult() {
        try {
            jso.call("readResult", new String[] {String.valueOf(idRead)});
        }
        catch (Exception ex) {
                ex.printStackTrace();
        }
    }
    
    public void writeResult(String result) {
        try {
            jso.call("writeResult", new String[] {result});
        }
        catch (Exception ex) {
                ex.printStackTrace();
        }
    }
    
    public void write(String id) {
        String query="UPDATE tag_id SET id="+id+" WHERE protocol_id='GEN2' AND antenna_id=1;";
        try {
            rfidReader.executeQuery(query);
            System.out.println("writed");
            writeResult("writed");
        } catch (IOException ex) {
            System.out.println(ex);
            writeResult("error");
        }
    }
    
    

}
