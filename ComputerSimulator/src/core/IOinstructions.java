package core;
import gui.controllers.EngineerConsoleController;
import gui.controllers.UserInterfaceController;
/*DEVID	Device
        0	Console Keyboard
        1	Console Printer
        2	Card Reader
        3-31	Console Registers, switches, etc*/
public class IOinstructions extends ISA {
	
	//*************************************************************************************************************************
    //Input Character To Register from Device, r = 0..3
    public static void IN(){
        Halt.halt();
        EngineerConsoleController.setStepInformation("Please enter a number",false);
        Register r = null;
        //get the Register according to R
        switch (R){
            case "00": r = cpu.getR0(); break;
            case "01": r = cpu.getR1();break;
            case "10": r = cpu.getR2();break;
            case "11": r = cpu.getR3();break;
        }
        // c(r) <- IOmemory(DevID)
        EngineerConsoleController.setStepInformation("Please Input Data!!!",false);
        Halt.halt();
        r.setContent(IOmemory.getInstance().getContent(DevID));
        EngineerConsoleController.setStepInformation(r.getName()+"<-I/O",false);
        CPU.cyclePlusOne();
        Halt.halt();
    }
    
    //Input without halt
    public static void INNHLT(){
        Register r = null;
        //get the Register according to R
        switch (R){
            case "00": r = cpu.getR0(); break;
            case "01": r = cpu.getR1();break;
            case "10": r = cpu.getR2();break;
            case "11": r = cpu.getR3();break;
        }
        // c(r) <- IOmemory(DevID)
        EngineerConsoleController.setStepInformation("Please Input Data!!!",false);
        UserInterfaceController.setStepInformation("Please Input Data!");
        Halt.halt();
        r.setContent(IOmemory.getInstance().getContent(DevID));
        EngineerConsoleController.setStepInformation(r.getName()+"<-I/O",false);
        CPU.cyclePlusOne();
        EngineerConsoleController.setStepInformation("Input success, press \"Next\" to continue!!!",false);
        UserInterfaceController.setStepInformation("Input success, press \"Run\" to continue!!!");
        Halt.halt();
    }
    
//*************************************************************************************************************************
//    Output Character to Device from Register, r = 0..3
    public static void OUT(){
        Register r = null;
        //get the Register according to R
        switch (R){
            case "00": r = cpu.getR0(); break;
            case "01": r = cpu.getR1();break;
            case "10": r = cpu.getR2();break;
            case "11": r = cpu.getR3();break;
        }
        // IOmemory(DevID)<-c(r)
        IOmemory.getInstance().setContent(DevID,r.getContent());
        EngineerConsoleController.setStepInformation(r.getName()+"<-I/0",false);
        CPU.cyclePlusOne();
        Halt.halt();
    }
    
  //Output without halt
    public static void OUTNHLT(){
        Register r = null;
        //get the Register according to R
        switch (R){
            case "00": r = cpu.getR0(); break;
            case "01": r = cpu.getR1();break;
            case "10": r = cpu.getR2();break;
            case "11": r = cpu.getR3();break;
        }
        // IOmemory(DevID)<-c(r)
        IOmemory.getInstance().setContent(DevID,r.getContent());
        CPU.cyclePlusOne();
        
    }

    //*************************************************************************************************************************
//  Check device status

    public static void CHK(){
        Register r = null;
        //get the Register according to R
        switch (R){
            case "00": r = cpu.getR0(); break;
            case "01": r = cpu.getR1();break;
            case "10": r = cpu.getR2();break;
            case "11": r = cpu.getR3();break;
        }
        // c(r) <- IOmemorystatus(DevID)
        r.setContent(IOmemory.getInstance().getStatus(DevID));
        EngineerConsoleController.setStepInformation("I/0->"+r.getName(),false);
        CPU.cyclePlusOne();
        Halt.halt();
    }
    //Output without halt
    public static void CHKNHLT(){
        Register r = null;
        //get the Register according to R
        switch (R){
            case "00": r = cpu.getR0(); break;
            case "01": r = cpu.getR1();break;
            case "10": r = cpu.getR2();break;
            case "11": r = cpu.getR3();break;
        }
        // c(r) <- IOmemorystatus(DevID)
        r.setContent(IOmemory.getInstance().getStatus(DevID));
    }



}
