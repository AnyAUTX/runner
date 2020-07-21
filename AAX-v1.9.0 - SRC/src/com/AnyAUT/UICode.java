package com.AnyAUT;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.filechooser.FileNameExtensionFilter;

public class UICode extends javax.swing.JFrame {
    
	static String strTestRunName, strTestRunBy, strTestRunLocation, strBrowserTimeout;
    public UICode() {
         initComponents();
        
         setIconImage((new ImageIcon("icon.png")).getImage());
         
        // Window position
       Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
       this.setLocation(dim.width/2-this.getSize().width/2, (dim.height/2-this.getSize().height/2)-80);

       this.setResizable(false);
       this.setSize(860, 700);
       this.getContentPane().setBackground(new java.awt.Color(255, 255, 255));
         
        // Window Title
        JTextField newTitle = new JTextField("AnyAUT-XL1.8.1 - Excel Driven Test Automation Framework. Demo for upto 500 Test Steps.");
        this.setTitle(newTitle.getText());
         
        // For browsing an Excel
        chooseExcelFile = new JFileChooser();
        chooseExcelFile.setCurrentDirectory(new File("C:\\"));
        chooseExcelFile.setDialogTitle("Select AnyAUTxl Regression Excel");
        chooseExcelFile.setFileFilter(new FileNameExtensionFilter("Excel Files", "xls"));
        
        // Label Font Setting
        labelTestRunName.setFont(new java.awt.Font("Calibri", 1, 16)); // NOI16N
        labelTestRunName.setText("Test Run Name :");

        labelTestRunBy.setFont(new java.awt.Font("Calibri", 1, 16)); // NOI16N
        labelTestRunBy.setText("Test Run By :");

        labelSelectTestRun.setFont(new java.awt.Font("Calibri", 1, 16)); // NOI16N
        labelSelectTestRun.setText("Select Test Excel :");

        labelBrowserTimeout.setFont(new java.awt.Font("Calibri", 1, 16)); // NOI16N
        labelBrowserTimeout.setText("Browser Timeout :");

        buttonBrowseXL.setFont(new java.awt.Font("Calibri", 1, 16)); // NOI16N
        buttonBrowseXL.setText("Browse");

        //buttonExecute.setBackground(new java.awt.Color(76, 69, 69));
        buttonExecute.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        buttonExecute.setText("Execute Automation Test");
        
        labelCopyright.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        labelCopyright.setText("AnyAUTxl Test Automation Framework.      ");

        //set images
        logoAnyAUT.setIcon(new javax.swing.ImageIcon("logo.png")); // NOI18N

        imageBot.setIcon(new javax.swing.ImageIcon("anyautBot-small.png")); // NOI18N

        imagePoweredBy.setIcon(new javax.swing.ImageIcon("powered by-small.png")); // NOI18N

        
        // Set tool tips
        String vTT1 =
                "<html><p><font color=\"#000\" " +
                "size=\"18\" face=\"Calibri\">Give your Test a name." +
                "</font></p></html>";
        
        String vTT2 =
                "<html><p><font color=\"#000\" " +
                "size=\"18\" face=\"Calibri\">Tester's name." +
                "</font></p></html>";
        
        String vTT3 =
                "<html><p><font color=\"#000\" " +
                "size=\"18\" face=\"Calibri\">AnyAUTxl Regression Excel" +
                "</font></p></html>";
       
        String vTT4 =
                "<html><p><font color=\"#000\" " +
                "size=\"18\" face=\"Calibri\">In seconds." +
                "</font></p></html>";


  		DateFormat df = new SimpleDateFormat("ddMMM_hhmm");  
  		String currentDateTime = df.format(new Date());

  		vTestRunName.setText(currentDateTime);
  		vUserName.setText("User");
  		vRunExcelPath.setText(System.getProperty("user.dir")+"\\Demo.xls");
  		vBrowserTimeout.setText("6");
        vTestRunName.setToolTipText(vTT1);
        vUserName.setToolTipText(vTT2);
        vRunExcelPath.setToolTipText(vTT3);
        vBrowserTimeout.setToolTipText(vTT4);
     
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

    	// Field Labels           
        labelTestRunName = new javax.swing.JLabel();
    	labelTestRunBy = new javax.swing.JLabel();
        labelSelectTestRun = new javax.swing.JLabel();
        labelBrowserTimeout = new javax.swing.JLabel();
    	
        // Panel 
        panelForm = new javax.swing.JPanel();
        
        // Field Values
    	vTestRunName = new javax.swing.JTextField();
        vUserName = new javax.swing.JTextField();
        vRunExcelPath = new javax.swing.JTextField();
        vBrowserTimeout = new javax.swing.JTextField();
        
        buttonBrowseXL = new javax.swing.JButton();
        buttonExecute = new javax.swing.JButton();
        
        
        logoAnyAUT = new javax.swing.JLabel();
        imageBot = new javax.swing.JLabel();
        imagePoweredBy = new javax.swing.JLabel();
        
        labelCopyright = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        //setBackground(new java.awt.Color(153, 153, 255));
        setBackground(new java.awt.Color(255, 255, 255));

        //vTestRunName.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        vTestRunName.setHorizontalAlignment(javax.swing.JTextField.LEADING);
        vTestRunName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vTestRunNameActionPerformed(evt);
            }
        });

        vUserName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vUserNameActionPerformed(evt);
            }
        });

        labelTestRunBy.setText("Test Run By");

        labelTestRunName.setText("Test Run Name");

        labelSelectTestRun.setText("Select Test Run Excel");

        vRunExcelPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vRunExcelPathActionPerformed(evt);
            }
        });

        buttonBrowseXL.setText("Browse");

        buttonBrowseXL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBrowseXLActionPerformed(evt);
            }
        });

        // buttonExecute.setBackground(new java.awt.Color(255, 128, 128));
        buttonExecute.setText("Execute Automation Test");
        buttonExecute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExecuteActionPerformed(evt);
            }
        });

        labelBrowserTimeout.setText("Default Browser Timeout");

        vBrowserTimeout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vBrowserTimeoutActionPerformed(evt);
            }
        });
        // [KK] - Need to add logo to our folder and give the relative path
        logoAnyAUT.setIcon(new javax.swing.ImageIcon("logo-anyaut-small.png"));
        //logoAnyAUT.setIcon(new javax.swing.ImageIcon("http://anyaut.com/wp-content/uploads/2016/11/logo-anyaut.png"));
        
       // setBackground(new java.awt.Color(66, 238, 244));
     // 	panelForm.setBackground(new java.awt.Color(255, 137, 28));
     panelForm.setBackground(new java.awt.Color(247, 125, 116));
     // panelForm.setBackground(new java.awt.Color(250, 231, 170));
     // panelForm.setBackground(new java.awt.Color(119, 191, 239));
    //    panelForm.setBackground(new java.awt.Color(250, 248, 118));
        panelForm.setBorder(new javax.swing.border.MatteBorder(null));
        
        
        javax.swing.GroupLayout panelFormLayout = new javax.swing.GroupLayout(panelForm);
        panelForm.setLayout(panelFormLayout);
        panelFormLayout.setHorizontalGroup(
            panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelFormLayout.createSequentialGroup()
                        .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelTestRunBy)
                            .addComponent(labelTestRunName)
                            .addComponent(labelBrowserTimeout)
                            .addComponent(labelSelectTestRun))
                        .addGap(39, 39, 39)
                        .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(vTestRunName, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(vUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(vRunExcelPath, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(vBrowserTimeout, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(buttonExecute, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonBrowseXL, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 37, Short.MAX_VALUE))
        );
        panelFormLayout.setVerticalGroup(
            panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTestRunName)
                    .addComponent(vTestRunName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTestRunBy)
                    .addComponent(vUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelSelectTestRun)
                    .addComponent(vRunExcelPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonBrowseXL))
                .addGap(38, 38, 38)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelBrowserTimeout)
                    .addComponent(vBrowserTimeout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(buttonExecute, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(labelCopyright)
                .addGap(211, 211, 211))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(logoAnyAUT)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(imageBot))
                            .addComponent(panelForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(247, 247, 247)
                        .addComponent(imagePoweredBy)))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(imageBot)
                    .addComponent(logoAnyAUT))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(imagePoweredBy)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelCopyright))
        );

            revalidate();
            pack();
    }// </editor-fold>
    
    public static void setUIFont (javax.swing.plaf.FontUIResource f){
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
          Object key = keys.nextElement();
          Object value = UIManager.get (key);
          if (value != null && value instanceof javax.swing.plaf.FontUIResource)
            UIManager.put (key, f);
          }
        } 
    

    private void vUserNameActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
    }                                         

    private void vRunExcelPathActionPerformed(java.awt.event.ActionEvent evt) {                                              
        // TODO add your handling code here:
    }                                             

    private void vTestRunNameActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
    }                                            

    private void vBrowserTimeoutActionPerformed(java.awt.event.ActionEvent evt) {                                                
        // TODO add your handling code here:

    }                                               

    private void buttonBrowseXLActionPerformed(java.awt.event.ActionEvent evt) {                                                
        
        int returnValue = chooseExcelFile.showOpenDialog(null);
        
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            vChosenExcelPath = chooseExcelFile.getSelectedFile().getPath();
            vRunExcelPath.setText(vChosenExcelPath);
        } else {
            showMessageDialog(null, "No file chosen!");
        }
        
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			vChosenExcelPath = chooseExcelFile.getSelectedFile().getPath();
			vRunExcelPath.setText(vChosenExcelPath);
		} else { 
			if ((vRunExcelPath.getText().equals(null)) || (vRunExcelPath.getText().equals("")))// check if there is default values in textbox
				showMessageDialog(null, "No file chosen!");
			else; 
		}
        
    }                                               

    private void buttonExecuteActionPerformed(java.awt.event.ActionEvent evt) {      
    	
    	strTestRunName = vTestRunName.getText();
		strTestRunBy = vUserName.getText();
		strTestRunLocation =vRunExcelPath.getText();
		strBrowserTimeout = vBrowserTimeout.getText();
		
		
		if (strTestRunName.length() == 0) {
			JOptionPane.showMessageDialog(null, "Please enter the Test Run Name", "Test Run Name", JOptionPane.INFORMATION_MESSAGE);
		} else if (strTestRunBy.length() == 0) {
			JOptionPane.showMessageDialog(null, "Please enter the Test Run By",  "Test Run By", JOptionPane.INFORMATION_MESSAGE);
		
		} else if (strTestRunLocation.length() == 0) {
			JOptionPane.showMessageDialog(null, "Please enter the File location", "File location", JOptionPane.INFORMATION_MESSAGE);
			
		} else if (strBrowserTimeout.length() == 0) {
			JOptionPane.showMessageDialog(null, "Please enter the default browser time out", "Browser Timeout", JOptionPane.INFORMATION_MESSAGE);

    	} else {
    		this.setState(Frame.ICONIFIED);
	    	CallDriverScripts.runDriverScripts(strTestRunName);
			
			//quit the program
			System.exit(0);
		}
    } 
    
    //returns the value of one the entries made in the UI based on the paramter passed
    public String returnValues(String strParam) {
		
		String strValue = null;
		System.out.println("Called returnValue :"+ strParam);
			switch(strParam)
			{
				case "TestRunName":
					strValue = strTestRunName;
					System.out.println("Run name:  "+ strTestRunName);
					return strValue;
				case "TestRunBy":
					strValue = strTestRunBy;
					return strValue;			
				case "TestRunLocation":
					strValue = strTestRunLocation;
					System.out.println("Run location  "+ strTestRunLocation);
					return strValue;
				case "BrowserTimeOut":
					strValue = strBrowserTimeout;
					return strValue;
			}
			
			return null;
			
		}

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
    	if (args.length > 0) 
        { 
            //System.out.println("The command line argument is: " + args[0]); 
            ExecuteTest.logger.info("The command line argument is: " + args[0]);
  
      		DateFormat df = new SimpleDateFormat("ddMMM_hhmm");  
      		String currentDateTime = df.format(new Date());
      		
    		strTestRunBy = "User";
    		strTestRunLocation = args[0];
    		strBrowserTimeout = "10";
    		    	
    		File f = new File(strTestRunLocation);
    		String vFileName = (f.getName()).toString();
    		String[] vExcelName =  vFileName.split("\\.");
          	strTestRunName = currentDateTime + "_" + vExcelName[0];
    		f = null;
    	
	    	CallDriverScripts.runDriverScripts(strTestRunName);
			
			//quit the program
			System.exit(0);
        	
        } 
        else {
            System.out.println();
            ExecuteTest.logger.info("No command line arguments found.");
	        try {
	            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
	                if ("Nimbus".equals(info.getName())) {
	                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
	                    
	                    break;
	                }
	            }
	        } catch (ClassNotFoundException ex) {
	            java.util.logging.Logger.getLogger(UICode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (InstantiationException ex) {
	            java.util.logging.Logger.getLogger(UICode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (IllegalAccessException ex) {
	            java.util.logging.Logger.getLogger(UICode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
	            java.util.logging.Logger.getLogger(UICode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        }
	        //</editor-fold>
	
	        /* Create and display the form */
	        java.awt.EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                new UICode().setVisible(true);
	                }
	        });
    	} 

    }

    // Variables declaration - do not modify
    
    private javax.swing.JPanel panelForm;
    private javax.swing.JButton buttonBrowseXL;
    private javax.swing.JButton buttonExecute;
    
    private javax.swing.JLabel labelTestRunBy;
    private javax.swing.JLabel labelTestRunName;
    private javax.swing.JLabel labelSelectTestRun;
    private javax.swing.JLabel labelBrowserTimeout;
    
    private javax.swing.JLabel labelCopyright;
    
    private javax.swing.JLabel logoAnyAUT;
    private javax.swing.JLabel imageBot;
    private javax.swing.JLabel imagePoweredBy;
        
    private javax.swing.JTextField vTestRunName;
    private javax.swing.JTextField vUserName;
    private javax.swing.JTextField vRunExcelPath;
    private javax.swing.JTextField vBrowserTimeout;
    
    private final JFileChooser chooseExcelFile;
    private String vChosenExcelPath;
    // End of variables declaration                   
}
