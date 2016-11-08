package test;
/**
 * Created by Administrator on 2016/11/7.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test {

    public String expression(String s){
        int a[];
        int n,m,k;
        String[] arr;
        k = 0;
        for(int i = 0; i <s.length(); i++){
            if  ((s.charAt(i)!='+')&&(s.charAt(i)!='*')){
                if (Character.isLetterOrDigit( s.charAt(i) )==false) {
                    k=1;
                }
            }
            if (i>0){
                if  (((s.charAt(i)=='+')||(s.charAt(i)=='*'))&&((s.charAt(i-1)=='+')||(s.charAt(i-1)=='*')))
                {
                    k=1;
                }
                if  ((Character.isLetterOrDigit( s.charAt(i) )==true)&&(Character.isLetterOrDigit( s.charAt(i-1) )==true))
                {
                    k=1;
                    if ((Character.isDigit( s.charAt(i) )==true)&&(Character.isDigit( s.charAt(i-1) )==true)){
                        k=0;
                    }
                }
            }
        }
        if (Character.isLetterOrDigit( s.charAt(0) )==false) k=1;
        if (Character.isLetterOrDigit( s.charAt(s.length()-1) )==false) k=1;
        if (k==1){
            s="Error";
        }
        return s;

    }
    public String putin(String s,String exp) {
        int k = 0;
        String x;
        String y;
        String[] arr;
        arr = s.split(" ");
        if (arr[0]!="!simplify")
        {
            k=1;
        }
        //System.out.println(exp+"+++");
        for(int i = 1; i <arr.length; i++)
        {
            //System.out.println(exp);
            String[] arr1=arr[i].split("=");
            x=arr1[0];
            y=arr1[1];
            String[] str=exp.split("\\+");                        // exp   3*4*x+2*y
            exp="";
            for(int j = 0; j <str.length; j++)
            {
                //System.out.println(str[j]);                                                //  str   3*4*x   2*y
                String[] str1=str[j].split("\\*");                 //str1   3 4 x     2 y
                str[j]="";
                for(int o = 0; o <str1.length; o++)              //!simplify x=22 y=1                 31*4*x+2*y
                {
                    if (str1[o].equals(x)==true)
                    {
                        str[j]=str[j]+y;
                    }else
                    {
                        str[j]=str[j]+str1[o];
                    }
                    if (o<str1.length-1)
                    {  str[j]=str[j]+"*"; }
                }
                exp=exp+str[j];
                if (j<str.length-1)
                {  exp=exp+"+"; }
            }
        }
        //System.out.println(exp);              /////////////////////////////////////

        return exp;
    }

    public String simplify(String exp)
    {
        String[] str=exp.split("\\+");
        exp="";
        int q=1;
        String exp1="";
        for(int i = 0; i <str.length; i++)
        {
            q=1;
            String[] str1=str[i].split("\\*");
            exp1="";
            for(int j = 0; j <str1.length; j++)
            {
                if (Character.isDigit( str1[j].charAt(0) )==true)
                {
                    int w=  Integer.parseInt(str1[j]);
                    q= q * w;
                }else
                {
                    exp1=exp1+"*"+str1[j];
                }
            }
            exp1=String.valueOf(q)+exp1;
            exp=exp+exp1;
            if (i<str.length-1)
            {  exp=exp+"+"; }
        }
//////////////////////////////////////////////////////////////////////////////////////////////////
        exp="0+"+exp;
        //System.out.println(exp); ////////////////////////////////////
        str=exp.split("\\+");
        exp="";
        for(int i = 0; i <str.length; i++)
        {
            String[] str1=str[i].split("\\*");
            //int r=0;
            for(int j = 0; j <i; j++)
            {
                String[] str2=str[j].split("\\*");
                int e=0;
                if (str[j]==""){e=1;}
                if (str1.length==str2.length)
                {
                    for(int o = 1; o <str1.length; o++)
                    {
                        if (str1[o].equals(str2[o])==false) {  e=1; }
                    }
                }else {e=1;}
                if (e==0)
                {
                    str[i]="";
                    str[j]="";

                    int r=Integer.parseInt(str1[0]);
                    int w=Integer.parseInt(str2[0]);
                    str2[0]=String.valueOf(w+r);
                    for (int o=0;o<str2.length;o++)
                    {
                        str[j]=str[j]+str2[o];
                        if (o<str2.length-1)
                        {  str[j]=str[j]+"*"; }
                    }
                }
            }
        }
        for(int i = 0; i <str.length; i++)
        {
            String[] str1 = str[i].split("\\*");
            str[i]="";
            String a="1";
            if (a.equals((str1[0]))==true)
            {
                str1[0]="";
            }else
            {
                if (str1.length!=1) {str[i]=str1[0]+"*"; }
                else {str[i]=str1[0];}
            }
            for(int j = 1; j <str1.length; j++)
            {
                str[i]=str[i]+str1[j];
                if (j <str1.length-1)
                {
                    str[i]=str[i]+"*";
                }
            }
        }
        //System.out.println(exp+"     "+str.length);         //110  {} 487*y  {} {} {}
        for(int i = 0; i <str.length; i++)
        {
            String a="0";
            //System.out.print(str[i]+"/");
            if (str[i]!="") {
                if (a.equals((str[i]))==false) {
                    //System.out.println(str[i] + "*" + i);
                    if (i > 0) {
                        if (i==1)
                        {
                            if (a.equals(str[0])==false){exp = exp + "+"; }
                        }else
                        {
                            exp = exp + "+";
                        }
                    }
                    exp = exp + str[i];
                }
            }

        }
        return exp;

    }

    public String der(String s,String exp)                  //4*x+y*x*x+y*2+y+x+z*x*y   !d/dx
    {
        String[] arr;
        arr = s.split("d");
        String x=arr[2];
        String[] str=exp.split("\\+");
        exp="";
        for(int i = 0; i <str.length; i++)
        {
            int q=0;
            String[] str1=str[i].split("\\*");
            str[i]="";
            for(int j = 0; j <str1.length; j++)
            {
                if (x.equals(str1[j])==true)
                {
                    q=q+1;
                }
            }
            if (q==0)
            {
                str[i]="";
            }else
            {
                int w=0;
                for(int j = 0; j <str1.length; j++)
                {
                    if ((x.equals(str1[j])==true)&&(w==0))
                    {
                        w=1;
                        str1[j]=String.valueOf(q);
                    }
                    str[i]=str[i]+str1[j];
                    if (j <str1.length-1)
                    {
                        str[i]=str[i]+"*";
                    }
                }
            }
            if (str[i]!="") {
                exp = exp + str[i];
                if (i < str.length - 1) {
                    exp = exp + "+";
                }
            }
        }
        return exp;
    }




    public static void main(String args[])
            throws IOException
    {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        String s;
        String exp="";
        String exp1=exp;
        while (true)
        {
            //System.out.println(s);
            s=br.readLine();
            Test test = new Test();
            //String exp = "4*x+y*x*x+y*2+y+x+z*x*y";
            //System.out.println(s);
            if (s.charAt(0) != '!') {
                exp = test.expression(s);
                exp = test.simplify(exp);
                exp1=exp;
            } else if (s.charAt(1) == 's') {
                exp = test.putin(s,exp1);
                //System.out.println(exp);
                exp = test.simplify(exp);
            } else if (s.charAt(1) == 'd') {
                exp = test.der(s,exp1);
                //System.out.println(exp);
                exp = test.simplify(exp);
            }else
            {
                exp="Error";
            }
            System.out.println(exp);
            //System.out.println("****");
            //4*x+y*x*x+y*2+y+x+z*x*y   !simplify x=22 y=1     !d/dx
        }
    }
}
