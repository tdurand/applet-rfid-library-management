package rfid;

import java.applet.Applet;
import java.awt.Graphics;
import java.io.IOException;
import java.util.Properties;

import br.com.rfidcoe.yarf.device.RFIDReaderFactory;
import br.com.rfidcoe.yarf.device.reader.RFIDReader;

public class RFIDAccess extends Applet {
    
    public String identification;

    @Override
    public void init() {
        
    }

    @Override
    public void start() {
        Properties readerProperties=new Properties();
        
        readerProperties.setProperty("yarf.device.model", "MERCURY5");
        readerProperties.setProperty("reader.power", "3000");
        readerProperties.setProperty("yarf.device.host", "10.102.10.1");
        
        
        RFIDReader rfidReader = new RFIDReaderFactory().createReader(readerProperties);
        
        try {
            rfidReader.connect();
            rfidReader.setPower(Integer.valueOf(readerProperties
                    .getProperty("reader.power")));
        } catch (IOException ex) {
        }
        
        String[] test;
        try {
            test = rfidReader.readEPC();
            System.out.println(test.length);
            for (int i = 0; i < test.length; i++) {
                identification=test[i];
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        repaint();
    }
    
    public void paint(Graphics g)
    { 
//method to draw text on screen 
// String first, then x and y coordinate. 
     g.drawString("Hey "+ identification,20,20); 

    }
    
    

}
