package io.github.pumpkinxd.jcalc;
import java.awt.*;
import java.awt.event.*;
import io.github.pumpkinxd.jcalc.CalcCli;

public class JcalcGui {

    private Frame Calcwindow=new Frame("计算器");
    //private TextArea t1=new TextArea(15,1);
    private TextField t0=new TextField();
    private Panel p1;
    private Panel p2;
    NumberButtonMonitor nbm;
    Button Clear=new Button("清除");
    Button plus=new Button("+");
    Button minus=new Button("-");
    Button multiply=new Button("×");
    Button divide=new Button("÷");
    Button power=new Button("xʸ");
    Button eq_op=new Button("=");
    Button point=new Button(".");
    Button Backspace=new Button("退格");
    Button parenthesis[]=new Button[2];
    Button nums[]=new Button[10];
    //private String formula;
    //private double result;

    public void init()
    {
        p1=new Panel();
        p2=new Panel();
        Calcwindow.setBounds(20,20,1024,1024);
        GridLayout window_grid=new GridLayout(3,1);
        nbm=new NumberButtonMonitor();
        //Panel p = new Panel();
        //p.setLayout(new GridLayout(5,4));

        parenthesis[0]=new Button("(");
        parenthesis[0].addActionListener(nbm);
        parenthesis[0].setActionCommand("button_(");
        parenthesis[1]=new Button(")");
        parenthesis[1].addActionListener(nbm);
        parenthesis[1].setActionCommand("button_)");

        for (int i = 0; i < 10; i++) {
        nums[i]=new Button(String.valueOf(i));
        nums[i].addActionListener(nbm);
        nums[i].setActionCommand("button_"+String.valueOf(i));
        }
        //Button negative=new Button("±");
        //Clear.addActionListener();
        //Calcwindow.add(Clear,BorderLayout.CENTER);
        GridLayout grid=new GridLayout(4,5);
        GridLayout grid2=new GridLayout(1,5);
        {
            p1.setLayout(grid);
            p2.setLayout(grid2);
            Calcwindow.setLayout(window_grid);
            Calcwindow.add(t0);
            Calcwindow.add(p1);
            Calcwindow.add(p2);

            t0.setSize(1024,256);
            p1.setSize(1024,512);

            p1.add(nums[7]);
            p1.add(nums[8]);
            p1.add(nums[9]);


            p1.add(plus);
            plus.addActionListener(nbm);
            plus.setActionCommand("button_plus");
            p1.add(Backspace);
            Backspace.addActionListener(nbm);
            Backspace.setActionCommand("button_Backspace");
            p1.add(nums[4]);

            p1.add(nums[5]);
            p1.add(nums[6]);

            p1.add(minus);
            minus.addActionListener(nbm);
            minus.setActionCommand("button_minus");
            p1.add(parenthesis[0]);
            p1.add(nums[1]);

            p1.add(nums[2]);
            p1.add(nums[3]);

            p1.add(multiply);
            multiply.addActionListener(nbm);
            multiply.setActionCommand("button_multiply");
            p1.add(parenthesis[1]);
            p1.add(nums[0]);
            p1.add(point);
            point.addActionListener(nbm);
            point.setActionCommand("button_point");
            p1.add(Clear);
            Clear.addActionListener(nbm);
            Clear.setActionCommand("button_Clear");
            p1.add(divide);
            divide.addActionListener(nbm);
            divide.setActionCommand("button_divide");
            p1.add(power);
            power.addActionListener(nbm);
            power.setActionCommand("button_power");
            p2.add(eq_op);
            eq_op.addActionListener(nbm);
            eq_op.setActionCommand("button_eq");

        }





        Calcwindow.setVisible(true);
        Calcwindow.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

    }

    public static void main(String args[]) {

        JcalcGui cg=new JcalcGui();
        cg.init();
        //int retval=C_Style_main(args);
        //System.exit(retval);
    }
    public static int C_Style_main(String args[]){
        JcalcGui cg=new JcalcGui();
        cg.init();
        return 0;
    }

    class NumberButtonMonitor implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
        String WhichButton=e.getActionCommand();

        if(WhichButton.equals("button_eq"))//等号事件
        {
            int CaretPos=t0.getCaretPosition();
            String exp= t0.getText();
            double result;
            if(!exp.isEmpty()) {
                result=CalcCli.RPNcalc(CalcCli.ToRPNV2(CalcCli.preConvt(exp)));//调用计算器核心
                t0.setText(String.valueOf(result));
            }

        }
        if(WhichButton.equals("button_power"))
        {
            //F代表算式
            int CaretPos=t0.getCaretPosition();
            String old_F=t0.getText();

            String new_F;
            if (CaretPos==0)
            new_F=old_F+'^';
            else new_F=old_F.substring(0,CaretPos)+'^'+old_F.substring(CaretPos);
            t0.setText(new_F);
            if(CaretPos>0)
                t0.setCaretPosition(CaretPos+1);
            //call setCaretPosition() later
        }
        if(WhichButton.equals("button_divide"))
        {
            int CaretPos=t0.getCaretPosition();
            String old_F=t0.getText();
            String new_F;
            if (CaretPos==0)
            new_F=old_F+'/';
            else new_F=old_F.substring(0,CaretPos)+'/'+old_F.substring(CaretPos);
            t0.setText(new_F);
            if(CaretPos>0)
                t0.setCaretPosition(CaretPos+1);
        }
        if(WhichButton.equals("button_Clear"))
        {
            t0.setText("");
        }
        if(WhichButton.equals("button_point"))
        {
            int CaretPos=t0.getCaretPosition();
            String old_F=t0.getText();
            String new_F;
            if (CaretPos==0)
            new_F=old_F+'.';
            else new_F=old_F.substring(0,CaretPos)+'.'+old_F.substring(CaretPos);
            t0.setText(new_F);
            if(CaretPos>0)
                t0.setCaretPosition(CaretPos+1);
        }
        if(WhichButton.equals("button_multiply"))
        {
            int CaretPos=t0.getCaretPosition();
            String old_F=t0.getText();
            String new_F;
            if (CaretPos==0)
                new_F=old_F+'*';
            else new_F=old_F.substring(0,CaretPos)+'*'+old_F.substring(CaretPos);
                t0.setText(new_F);
            if(CaretPos>0)
                t0.setCaretPosition(CaretPos+1);
            }
        if(WhichButton.equals("button_minus"))
        {
            int CaretPos=t0.getCaretPosition();
            String old_F=t0.getText();
            String new_F;
            if(CaretPos==0) new_F=old_F+'-';
            else new_F=old_F.substring(0,CaretPos)+'-'+old_F.substring(CaretPos);
            t0.setText(new_F);
            if(CaretPos>0)
                t0.setCaretPosition(CaretPos+1);

        }
        if (WhichButton.equals("button_Backspace"))
        {
            int CaretPos=t0.getCaretPosition();
            String old_F=t0.getText();
            if(!old_F.isEmpty())
            {

                String new_F;
                if (CaretPos==0) new_F = old_F.substring(0,old_F.length() - 1);
                else new_F=old_F.substring(0,CaretPos-1)+old_F.substring(CaretPos);
                t0.setText(new_F);
                if((CaretPos-1)>0)
                    t0.setCaretPosition(CaretPos-1);
            }

        }
        if(WhichButton.equals("button_plus"))
        {
            int CaretPos=t0.getCaretPosition();
            String old_F=t0.getText();
            String new_F;
            if (CaretPos==0) new_F=old_F+'+';
            else new_F=old_F.substring(0,CaretPos)+'+'+old_F.substring(CaretPos);
            t0.setText(new_F);
            if(CaretPos>0)
                t0.setCaretPosition(CaretPos+1);
        }
        if(WhichButton.equals("button_("))
        {
            int CaretPos=t0.getCaretPosition();
            String old_F=t0.getText();
            String new_F;
            if (CaretPos==0)
            new_F=old_F+'(';
            else new_F=old_F.substring(0,CaretPos)+'('+old_F.substring(CaretPos);
            t0.setText(new_F);
            if(CaretPos>0)
                t0.setCaretPosition(CaretPos+1);
        }

        if(WhichButton.equals("button_)"))
        {
            int CaretPos=t0.getCaretPosition();
            String old_F=t0.getText();
            String new_F;
            if (CaretPos==0)
                new_F=old_F+')';
            else new_F=old_F.substring(0,CaretPos)+')'+old_F.substring(CaretPos);
            t0.setText(new_F);
            if(CaretPos>0)
                t0.setCaretPosition(CaretPos+1);
            }
        if(Character.isDigit(WhichButton.charAt(WhichButton.length()-1)))
        {
            int CaretPos=t0.getCaretPosition();
            String old_F=t0.getText();
            String new_F;
            if(CaretPos==0)
            new_F= old_F + WhichButton.charAt(WhichButton.length() - 1);
            else new_F=old_F.substring(0,CaretPos)+WhichButton.charAt(WhichButton.length() - 1)+old_F.substring(CaretPos);
            t0.setText(new_F);
            if(CaretPos>0)
            t0.setCaretPosition(CaretPos+1);

        }
        }
    }

}

