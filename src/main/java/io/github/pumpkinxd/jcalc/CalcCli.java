package io.github.pumpkinxd.jcalc;

import java.util.Stack;
import java.util.Vector;
import java.util.Scanner;

import java.util.Iterator;

/**
 * CalcCli
 * 是
 * 计算器的运算部分实现
 * 嗯，写的非常的C/CPP，算法质量还不太好
 * 目前支持加减乘除与乘方运算（符号为+ - * / ^或E)
 * 输入非法或输入数不符合要求（如除以0或者对负数进行开偶次方(指数为分数时相当于开方))时返回NaN
 * main()只用于命令行测试
 * 噢对了 似乎直接以'+','*','/'等形式开头的式子目前会导致直接返回NaN
 */
public class CalcCli {


    public static void main(String[] args) {//test those stuff
        String midRaw="2.5^2+4*4";//字符串形式的算式
        String newmid=preConvt(midRaw);//Convt其实应该是"convert"之类的，懒得改了
        //System.out.println(newmid);
        Vector RPN2=ToRPNV2(newmid);
        System.out.println(RPN2);//输出逆波兰式，debug用
        double val = RPNcalc(RPN2);//注意不要把换行符传入其中,不然100%返回NaN
        System.out.println(val);//debug用的结果输出，我想不直接调用这个类的main的话应该看不到的
    }


    public static String preConvt(String arg) { //replace negative signs with "N"
        String retExp="";
        if (arg.charAt(0)=='-') {
            if (Character.isDigit(arg.charAt(1))||arg.charAt(1)=='('||arg.charAt(1)=='-'||arg.charAt(1)=='.') {
                retExp+="N";
            }
            else
            {
                retExp+=arg.charAt(0);
            }
        }
        else
        {
            retExp+=arg.charAt(0);
        }
        //System.out.println(retExp);
        for (int i = 1; i < arg.length(); i++) {
            if(arg.charAt(i)=='-')
            {
                if(!Character.isDigit(arg.charAt(i-1))||arg.charAt(i-1)==')'||arg.charAt(i-1)=='+'||arg.charAt(i-1)=='-'||arg.charAt(i-1)=='*'||arg.charAt(i-1)=='/'||arg.charAt(i-1)=='^')
                {
                    retExp+='N';
                }
                else{
                    retExp+='-';
                }
            }
            else{
                retExp+=arg.charAt(i);
            }
        }
        retExp=retExp.replace('^','E');//replace '^' with 'E', E stands for "Exponentiation"
        return retExp;
    }

    public static boolean cmpOpPriority(char top,char cur)//returns true if top is higher than cur
    {//懒得优化了，哦对还有未来可能引入别的运算
        if ((top == '+' || top == '-') && (cur == '+' || cur == '-'))
            return true;
        if ((top == '*' || top == '/') && (cur == '+' || cur == '-' || top == '*' || top == '/'))
            return true;
        if ((top=='E') && (cur == '+' || cur == '-' || cur == '*' || cur == '/' || top == 'E'))
            return true;
        if (cur == ')')
            return true;
        return false;
    }

    /**
     *
     * 这个懒得删了，记得不要用就对了
     *
     */
    @Deprecated
    public static Vector<String> ToRPN(String arg) {
        Stack<Character> opstack=new Stack<Character>();
        Vector<String> RPNexp=new Vector<String>();
        // System.out.print("b4 loop\n");
        for (int i = 0; i < arg.length(); i++) {
            String temp="";
            // System.out.print("in loop01\n");
            if(Character.isDigit(arg.charAt(i)))
            {
                temp+=arg.charAt(i);
                while (i+1<arg.length()&&(Character.isDigit(arg.charAt(i+1)))||arg.charAt(i+1)=='.') {
                    //  System.out.print("in loop01_00\n");
                    temp+=arg.charAt(i+1);
                    ++i;
                }
                RPNexp.add(temp);
            }
            else if(opstack.empty()||arg.charAt(i)=='(')
            {
                opstack.push(arg.charAt(i));
            }
            else if(cmpOpPriority(opstack.peek(), arg.charAt(i)))
            {
                if(arg.charAt(i)==')')
                {
                    while (!opstack.empty()&&opstack.peek()!='(') {
                        //System.out.print("in loop01_01\n");
                        temp+=opstack.peek();
                        opstack.pop();
                        RPNexp.addElement(temp);
                        temp="";
                    }
                    opstack.pop();
                }
                else
                {
                    while (!opstack.empty()&&cmpOpPriority(opstack.peek(), arg.charAt(i))) {
                        //  System.out.print("in loop01_02\n");
                        temp+=opstack.pop();
                        RPNexp.add(temp);
                        temp="";
                    }
                    opstack.push(arg.charAt(i));
                }
            }
            else
            {
                opstack.push(arg.charAt(i));
            }

        }
        while (!opstack.empty()) {
            //System.out.print("in loop02\n");
            String temp="";
            temp+=opstack.pop();
            RPNexp.add(temp);
        }
        return RPNexp;
    }

    /**
     *
     *
     * 传入经过 preConvt 方法处理后的中辍表达式
     * 返回以字符串序列表示的逆波兰表达式
     *
     */
    public static Vector<String> ToRPNV2(String arg) {
        /***
         * this version support negative number
         */

        Stack<Character> opstack=new Stack<Character>();
        Vector<String> RPNexp=new Vector<String>();
        // System.out.print("b4 loop\n");
        for (int i = 0; i < arg.length(); i++) {
            String temp="";

            if(Character.isDigit(arg.charAt(i))||arg.charAt(i)=='N')
            {
                temp+=arg.charAt(i);
                while (i+1<arg.length()&&(Character.isDigit(arg.charAt(i+1))||arg.charAt(i+1)=='N'||arg.charAt(i+1)=='.')) {
                    temp+=arg.charAt(i+1);
                    ++i;
                }
                RPNexp.add(temp);
            }
            else if(opstack.empty()||arg.charAt(i)=='(')
            {
                opstack.push(arg.charAt(i));
            }
            else if(cmpOpPriority(opstack.peek(), arg.charAt(i)))
            {
                if(arg.charAt(i)==')')
                {
                    while (!opstack.empty()&&opstack.peek()!='(') {
                        temp+=opstack.peek();
                        opstack.pop();
                        RPNexp.addElement(temp);
                        temp="";
                    }
                    opstack.pop();
                }
                else
                {
                    while (!opstack.empty()&&cmpOpPriority(opstack.peek(), arg.charAt(i))) {
                        //  System.out.print("in loop01_02\n");
                        temp+=opstack.pop();
                        RPNexp.add(temp);
                        temp="";
                    }
                    opstack.push(arg.charAt(i));
                }
            }
            else
            {
                opstack.push(arg.charAt(i));
            }

        }
        while (!opstack.empty()) {
            String temp="";
            temp+=opstack.pop();
            RPNexp.add(temp);
        }
        for (int i=0;i<RPNexp.size();i++) {
            String token=RPNexp.elementAt(i);
            if(token.length()>1&&Character.isDigit(token.charAt(token.length()-1)))
            {
                token=token.replace("N", "-");//把N替换回‘-’，方便RPNcalc直接将字符串转为数字
                token=token.replace("--","");
                //token=token.replace(" ", "");
                RPNexp.set(i, token);
            }

        }

        return RPNexp;
    }

    /**
     *
     * 对传入的逆波兰式求值
     * 非法格式一律返回NaN
     * 无效计算也会返回NaN
     *
     */
    public static double RPNcalc(Vector<String> tokens) {
        Stack<Double> num_stack=new Stack<Double>();

        for ( Iterator iter=tokens.iterator(); iter.hasNext();) {
            String token=(String)iter.next();
            if(Character.isDigit(token.charAt(token.length()-1))) {
                try {
                    num_stack.push(Double.valueOf(token));
                }catch (Exception e)//这下不会再有人多写小数点了吧
                {
                    return Double.NaN;
                }
            }
            else if (token.equals("+")) {
                try {//先这样写吧 回头再考虑要不要增加正号判断
                    double op1 = num_stack.pop();
                    num_stack.push(num_stack.pop() + op1);
                    //System.out.println(num_stack.peek());
                }catch (Exception e){return Double.NaN;}
            }
            else if (token.equals("-")) {
                double op1=num_stack.pop();
                num_stack.push(num_stack.pop()-op1);
                //System.out.println(num_stack.peek());
            }
            else if (token.equals("/")) {
                try{
                double op1=num_stack.pop();
                if(op1==0)//处理除以0，（话说混用汉语和英语写注释真的好么）
                {
                    return Double.NaN;//not a number
                }
                num_stack.push(num_stack.pop()/op1);
                }catch (Exception e){return Double.NaN;}
            }
            else if (token.equals("*")) {
                try {
                    double op1 = num_stack.pop();
                    num_stack.push(num_stack.pop() * op1);
                }catch (Exception e){return Double.NaN;}
            }
            else if (token.equals("E")){
                try {
                    double op1 = num_stack.pop();
                    num_stack.push(Math.pow(num_stack.pop(), op1));
                    if (num_stack.peek().isNaN()) return Double.NaN;
                }catch (Exception e){return Double.NaN;}
            }
            else{//处理非法输入
                System.out.println("非法输入");
                return Double.NaN;
            }


        }
        double retVal=num_stack.pop();
        return retVal;

    }




}