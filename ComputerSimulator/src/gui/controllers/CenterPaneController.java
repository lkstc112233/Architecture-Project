package gui.controllers;

import gui.Controller;
import javafx.fxml.FXML;
import core.*;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CenterPaneController implements Controller {
    @FXML private TextField StepInformation;
    @FXML private TextField Address;
    @FXML private TextField Content;
    @FXML private TextField InstructionField;
    @FXML private Text cycle;

    @FXML private TextField PC;
    @FXML private TextField MAR;
    @FXML private TextField MBR;
    @FXML private TextField IRR;
    @FXML private TextField IAR;
    @FXML private TextField IR;
    @FXML private TextField Y;
    @FXML private TextField Z;
    @FXML private TextField X1;
    @FXML private TextField X2;
    @FXML private TextField X3;
    @FXML private TextField R0;
    @FXML private TextField R1;
    @FXML private TextField R2;
    @FXML private TextField R3;
    @FXML private TextField CC;
    @FXML private TextField MFR;
    @FXML private TextField QR;
    @FXML private TextField MLR;
    @FXML private TextField PR;
    @FXML private TextField RR;

    private static boolean open = false;
    private boolean loadStatus = false;
    private static String stepInformation = "";
    private static boolean memoryInformation;
    static String[]  instruction={"","LDR:direct, no indexing","LDR:indirect, no indexing","STR:direct, no indexing","LDA:direct, no indexing","LDX:direct, indexing",
            "LDR:indirect,indexing", "LDX:direct, indexing","LDX:direct, indexing","STX:direct, indexing"};
    public static int instructionNum;
    Halt halt = new Halt();

    @Override
    public void initialise(){

    }

    public void switchOnOff() {
        if (!open) {
            CPU.getInstance().clearAll();
            CPU.getInstance().getMemory().clear();
            instructionNum = 0;
            loadStatus = false;
            stepInformation = "";
            update();
            open = !open;
        }
        else {
            shutdown();
            open = !open;
        }
    }

    public void loadInstruction() {
        if(open){
            if(loadStatus) {
                update();
            }else {
                BufferedReader br=null;
                FileReader fr=null;
                try{
                    String txt="instruction.txt";
                    fr=new FileReader(txt);
                    br=new BufferedReader(fr);
                    String line;
                    String address="000000001000";
                    // flag is used to mark whether the loaded line is data or instruction
                    Boolean flag = true;
                    while((line=br.readLine())!=null){
                        if(line.substring(0,2).equals("//"))
                            continue;
                        if(line.substring(0,4).equals("DATA")){
                            flag = true;
                            continue;
                        }
                        else if(line.substring(0, 12).equals("INSTRUCTIONS")){
                            flag = false;
                            continue;
                        }
                        // when flag is true, the loaded content is data, put it into the memory
                        if(flag){
                            String[] contents = line.split(",");
                            CPU.getInstance().getMemory().setContent(contents[0], contents[1]);
                        }
                        // when flag is false, the loaded content is instruction, put it into the memory
                        else{
                            CPU.getInstance().getMemory().setContent(address,line);
                            address=CPU.getInstance().getMemory().addressAddone(address);
                        }
                    }
                    stepInformation="Load success";
                    loadStatus = true;
                    //put the beginning address of a program into PC.
                    CPU.getInstance().getPC().setContent("000000001000");
                    update();
                    new Thread(halt).start();

                }catch (IOException e){
                    stepInformation="Load error";
                    update();
                    System.out.println(e.toString());
                    //show the error on the Monitor
                }
            }
        }
    }

    public void nextStep() {
        Halt.flag = false;
        update();
    }

    public void searchMemory() {
        String searchField = Address.getText();
        Content.setText(CPU.getInstance().getMemory().getContent(searchField));
    }

    private void update() {
        StepInformation.setText(stepInformation);
        if (memoryInformation) {
            Address.setText(CPU.getInstance().getMAR().getContent());
            Content.setText(CPU.getInstance().getMBR().getContent());
        }
        InstructionField.setText(instruction[instructionNum]);
        cycle.setText(String.valueOf(CPU.getCycle()));


        PC.setText(CPU.getInstance().getPC().getContent().substring(4, 16));
        MAR.setText(CPU.getInstance().getMAR().getContent());
        MBR.setText(CPU.getInstance().getMBR().getContent());
        IRR.setText(CPU.getInstance().getIRR().getContent());
        IAR.setText(CPU.getInstance().getIAR().getContent());
        IR.setText(CPU.getInstance().getIR().getContent());
        Y.setText(CPU.getInstance().getY().getContent());
        Z.setText(CPU.getInstance().getZ().getContent());
        X1.setText(CPU.getInstance().getX1().getContent());
        X2.setText(CPU.getInstance().getX2().getContent());
        X3.setText(CPU.getInstance().getX3().getContent());
        R0.setText(CPU.getInstance().getR0().getContent());
        R1.setText(CPU.getInstance().getR1().getContent());
        R2.setText(CPU.getInstance().getR2().getContent());
        R3.setText(CPU.getInstance().getR3().getContent());
        CC.setText(CPU.getInstance().getCC().getContent());
        MFR.setText(CPU.getInstance().getMFR().getContent());
//        QR.setText(CPU.getInstance().getQR().getContent());
    }

    private void shutdown() {
        StepInformation.setText("");
        Address.setText("");
        Content.setText("");
        InstructionField.setText("");

        PC.setText("");
        MAR.setText("");
        MBR.setText("");
        IRR.setText("");
        IAR.setText("");
        IR.setText("");
        Y.setText("");
        Z.setText("");
        X1.setText("");
        X2.setText("");
        X3.setText("");
        R0.setText("");
        R1.setText("");
        R2.setText("");
        R3.setText("");
        CC.setText("");
        MFR.setText("");
    }

    public static boolean getOpen() {
        return open;
    }

    public static void setStepInformation(String stepInformation, boolean memoryInformation) {
        CenterPaneController.stepInformation = stepInformation;
        CenterPaneController.memoryInformation = memoryInformation;
    }
}