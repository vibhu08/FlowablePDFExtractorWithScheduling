package PDFTest;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Component
public class ScheduledTasks {
	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 120000)
    public void reportCurrentTime() {
    	File file = new File("D:/PDFWork/MY_PDF_DIRECTORY"); 
    	String s[]=file.list();
    	
    	for(String s1 :s )
    	{
    		
    		 File f=new File("D:/PDFWork/MY_PDF_DIRECTORY/"+s1);
    		 PDDocument document = null;
			try {
				document = PDDocument.load(f);
			} catch (InvalidPasswordException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
    		 PDFTextStripper pdfStripper = null;
			try {
				pdfStripper = new PDFTextStripper();
			} catch (IOException e) {
				e.printStackTrace();
			}
    		 pdfStripper.setParagraphStart("/t");
    		    
    		ArrayList<String> l= new ArrayList<String>();
    		int n=0;

    		    try {
					for (String line: pdfStripper.getText(document).split(pdfStripper.getParagraphStart()))
					        {
					            int c=0;
					            line=line.trim();
						        for(int i=0;i<line.length();i++)
					            {
					               
					                if( ((i>0)&&(line.charAt(i)!=' ')&&(line.charAt(i-1)==' ')) || ((line.charAt(i)!=' ')&&(i==0)) )
					                {
					                c++;
					                }
					                
					            }
						        if(c<=5 && c>0)
					            {
						        	l.add(line);
						        	n++;
					            	System.out.println(line);
					            System.out.println("********************************************************************");
					            }  
					        }
				} catch (IOException e) {
					e.printStackTrace();
				}
    		    
    		    BufferedWriter br = null;
				try {
					br = new BufferedWriter(new FileWriter("D://PDFWork//PdfBox_Examples//"+s1+"_Heading"+".txt"));
				} catch (IOException e) {
					e.printStackTrace();
				}
    		    for(int i=0;i<n;i++)
    		    {
    		    	try {
						br.write(l.get(i));
					} catch (IOException e) {
						e.printStackTrace();
					}
    		    	try {
						br.newLine();
					} catch (IOException e) {
						e.printStackTrace();
					}
    		    }
    		    try {
					br.close();
					document.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
    	          f.delete();
    	}
        log.info("The time is now {}", dateFormat.format(new Date()));
    }
}
