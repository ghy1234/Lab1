/**
 * Created by think on 2016/9/28.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test {
	
	/**
	 * 查看输入字符串是否合法 
	 * <br>规则：1）变量名为单个字符 2）只包含数字、字母、+和*的标准多项式
	 * @param line 输入字符串
	 * @return 如果合法,则原样输出，否则输出error
	 */
    public String checkSyntax(String line){
        boolean error = false;
        for(int i = 0; i < line.length(); i++){
            if  ((line.charAt(i) != '+')&&(line.charAt(i) != '*')){
                if (Character.isLetterOrDigit(line.charAt(i)) == false) {
                    error = true;
                }
            }
            if (i > 0){
                if (((line.charAt(i) == '+')||(line.charAt(i) == '*'))
                		&& ((line.charAt(i-1) == '+')||(line.charAt(i-1) == '*')))
                {
                    error = true;
                }
                if ((Character.isLetterOrDigit(line.charAt(i)) == true)
                		&& (Character.isLetterOrDigit(line.charAt(i - 1)) == true))
                {
                    error = true;
                    if ((Character.isDigit(line.charAt(i)))
                    		&& (Character.isDigit(line.charAt(i - 1)) == true)){
                        error = false;
                    }
                }
            }
        }
        if (error){
        	line = "Error";
        }
        return line;
    }
    
	/**
	 * 将变量的值代入多项式
	 * @param line 输入的用于代入的表达式，   polynomial 多项式
	 * @return 如果合法,返回代入后的多项式，否则返回Error
	 */
    public String substitute(String line,String polynomial) {
        String[] substitutionArr;
        substitutionArr = line.split(" ");
        if (substitutionArr[0] != "!simplify")
        {
            return "Error";
        }
        //System.out.println(exp+"+++");
        for(int i = 1; i <substitutionArr.length; i++)
        {
            //System.out.println(exp);
            String[] substitution = substitutionArr[i].split("=");
            String variable = substitution[0];
            String value = substitution[1];
            String[] monomialArr = polynomial.split("\\+");
            polynomial = "";
            for(int j = 0; j <monomialArr.length; j++)
            {
                //System.out.println(str[j]);
                String[] monomial = monomialArr[j].split("\\*");
                monomialArr[j] = "";
                for(int o = 0; o < monomial.length; o++)
                {
                    if (monomial[o].equals(variable))
                    {
                    	monomialArr[j] = monomialArr[j] + value;
                    }
                    else
                    {
                    	monomialArr[j] = monomialArr[j] + monomial[o];
                    }
                    if (o < monomial.length - 1)
                    {  
                    	monomialArr[j] = monomialArr[j] + "*"; 
                    }
                }
                polynomial = polynomial + monomialArr[j];
                if (j < monomialArr.length - 1)
                {  
                	polynomial = polynomial + "+"; 
                }
            }
        }
        //System.out.println(exp);
        return polynomial;
    }

    public String simplify(String exp)
    {
        String[] polynomial = exp.split("\\+");
        exp = "";//?
        int q = 1;
        String exp1 = "";//?
        for(int i = 0; i < polynomial.length; i++)
        {
            q = 1;
            String[] str1 = polynomial[i].split("\\*");
            exp1 = "";
            for(int j = 0; j <str1.length; j++)
            {
                if (Character.isDigit( str1[j].charAt(0) )==true)
                {
                    int w=  Integer.parseInt(str1[j]);
                    q = q * w;
                }else
                {
                    exp1 = exp1+"*"+str1[j];
                }
            }
            exp1 = String.valueOf(q)+exp1;
            exp = exp+exp1;
            if (i < polynomial.length-1)
            {  exp = exp+"+"; }
        }
//////////////////////////////////////////////////////////////////////////////////////////////////
        exp = "0+" + exp;
        //System.out.println(exp); ////////////////////////////////////
        polynomial = exp.split("\\+");
        exp = "";
        for(int i = 0; i <polynomial.length; i++)
        {
            String[] str1=polynomial[i].split("\\*");
            //int r = 0;
            for(int j = 0; j <i; j++)
            {
                String[] str2 = polynomial[j].split("\\*");
                int e = 0;
                if (polynomial[j] == ""){e=1;}
                if (str1.length==str2.length)
                {
                    for(int o = 1; o <str1.length; o++)
                    {
                        if (str1[o].equals(str2[o])==false) {  e=1; }
                    }
                }else {e=1;}
                if (e==0)
                {
                    polynomial[i] = "";
                    polynomial[j] = "";

                    int r=Integer.parseInt(str1[0]);
                    int w=Integer.parseInt(str2[0]);
                    str2[0]=String.valueOf(w+r);
                    for (int o=0;o<str2.length;o++)
                    {
                        polynomial[j]=polynomial[j]+str2[o];
                        if (o<str2.length-1)
                        {  polynomial[j]=polynomial[j]+"*"; }
                    }
                }
            }
        }
        for(int i = 0; i <polynomial.length; i++)
        {
            String[] str1 = polynomial[i].split("\\*");
            polynomial[i]="";
            String a="1";
            if (a.equals((str1[0]))==true)
            {
                str1[0]="";
            }else
            {
                if (str1.length!=1) {polynomial[i]=str1[0]+"*"; }
                else {polynomial[i]=str1[0];}
            }
            for(int j = 1; j <str1.length; j++)
            {
                polynomial[i]=polynomial[i]+str1[j];
                if (j <str1.length-1)
                {
                    polynomial[i]=polynomial[i]+"*";
                }
            }
        }
        //System.out.println(exp+"     "+str.length);         //110  {} 487*y  {} {} {}
        for(int i = 0; i <polynomial.length; i++)
        {
            String a="0";
            //System.out.print(str[i]+"/");
            if (polynomial[i]!="") {
                if (a.equals((polynomial[i]))==false) {
                    //System.out.println(str[i] + "*" + i);
                    if (i > 0) {
                        if (i==1)
                        {
                            if (a.equals(polynomial[0])==false){exp = exp + "+"; }
                        }else
                        {
                            exp = exp + "+";
                        }
                    }
                    exp = exp + polynomial[i];
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
            long start=System.nanoTime();
            System.out.println("算法开始时间： "+start+"ns");
            if (s.charAt(0) != '!') {
                exp = test.checkSyntax(s);
                exp = test.simplify(exp);
                exp1=exp;
            } else if (s.charAt(1) == 's') {
                exp = test.substitute(s,exp1);
                //System.out.println(exp);
                exp = test.simplify(exp);
            } else if (s.charAt(1) == 'd') {
                exp = test.der(s,exp1);
                //System.out.println(exp);
                exp = test.simplify(exp);
            }
            else
            {
                exp = "error";
            }
            if(s.charAt(0) == '-')
            {
                break;
            }
            System.out.println(exp);
            long end = System.nanoTime();
            System.out.println("算法结束时间： "+end+"ns");
            System.out.println("算法运行时间： "+(end-start)+"ns");
            //System.out.println("****");
            //4*x+y*x*x+y*2+y+x+z*x*y   !simplify x=22 y=1     !d/dx
        }

    }


}