package test;
/**
 * Created by think on 2016/9/28.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 输入多项式，进行化简。按输入命令进行求导或带入求值，输入错误时提示错误信息.
 * @author abgnwl
 * 
 */
public class Laboratory4 {

	/**
	 * 查看输入字符串是否合法. <br>
	 * 规则：1）变量名为单个字符 2）只包含数字、字母、+和*的标准多项式.
	 * @param line 输入字符串
	 * @return 如果合法,则原样输出，否则输出error
	 */
	public final String checkSyntax(final String line) {
		boolean error = false;
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) != '+' && line.charAt(i) != '*'
					&& !Character.isLetterOrDigit(line.charAt(i))) {
				error = true;
			}
			if (i > 0) {
				if ((line.charAt(i) == '+'
							|| line.charAt(i) == '*')
						&& (line.charAt(i - 1) == '+' 
							|| line.charAt(i - 1) == '*')) {
					error = true;
				}
				if (Character.isLetterOrDigit(line.charAt(i))
						&& Character.isLetterOrDigit(line.charAt(i - 1))) {
					error = true;
					if (Character.isDigit(line.charAt(i)) 
							&& Character.isDigit(line.charAt(i - 1))) {
						error = false;
					}
				}
			}
		}
		String result;
		if (error) {
			result = "Error";
		}else{
			result =  line;
		}
		return result;
	}

	/**
	 * 将变量的值代入多项式.
	 * 
	 * @param line 带入命令
	 * @param polynomial 原多项式
	 * @return 如果合法,返回代入后的多项式，否则返回Error
	 */
	public final String substitute(final String line, final String polynomial) {
		String[] substitutionArr;
		substitutionArr = line.split(" ");
		if (!"!simplify".equals(substitutionArr[0])) {
			return "Error";
		}		
		String afterPolynomial = polynomial;
		for (int i = 1; i < substitutionArr.length; i++) {
			final String[] substitution = substitutionArr[i].split("=");
			final String variable = substitution[0];
			final String value = substitution[1];
			String[] monomialArr = afterPolynomial.split("\\+");
			afterPolynomial = "";
			for (int j = 0; j < monomialArr.length; j++) {
				final String[] monomial = monomialArr[j].split("\\*");
				monomialArr[j] = "";
				for (int o = 0; o < monomial.length; o++) {
					if (monomial[o].equals(variable)) {
						monomialArr[j] = monomialArr[j] + value;
					} else {
						monomialArr[j] = monomialArr[j] + monomial[o];
					}
					if (o < monomial.length - 1) {
						monomialArr[j] = monomialArr[j] + "*";
					}
				}
				afterPolynomial = afterPolynomial + monomialArr[j];
				if (j < monomialArr.length - 1) {
					afterPolynomial = afterPolynomial + "+";
				}
			}
		}
		return afterPolynomial;
	}

	/**
	 * 将表达式进行化简和格式化.
	 * 
	 * @param exp
	 *            表达式的字符串
	 * @return 化简并格式化后的字符串
	 */
	public final String simplify(final String exp) {
		String[] monomialList = exp.split("\\+");
		StringBuffer afterExp = new StringBuffer(); // 化简后的表达式
		StringBuffer monoBuffer;
		for (int i = 0; i < monomialList.length; i++) {
			int constant = 1;
			final String monomial = monomialList[i];
			final String[] factorList = monomial.split("\\*");
			monoBuffer = new StringBuffer();
			for (int j = 0; j < factorList.length; j++) {
				final String factor = factorList[j];
				if (factor.length() == 0) {
					continue;
				}
				final char firstChar = factor.charAt(0);
				if (Character.isDigit(firstChar)) {
					final int numFactor = Integer.parseInt(factor);
					constant = constant * numFactor;
				} else {
					monoBuffer.append('*');
					monoBuffer.append(factor);
				}
			}
			afterExp = afterExp.append(String.valueOf(constant)).append(monoBuffer);
			if (i < monomialList.length - 1) {
				afterExp = afterExp.append("+");
			}
		}

		final StringBuffer tempBuffer = new StringBuffer();
		tempBuffer.append("0+").append(afterExp);
		// Step1: 将所有的系数相乘

		monomialList = tempBuffer.toString().split("\\+");
		afterExp = new StringBuffer();
		for (int i = 0; i < monomialList.length; i++) {
			if ("".equals(monomialList[i])) {
				continue;
			}
			final String[] factorList = monomialList[i].split("\\*");
			for (int j = 0; j < i; j++) {
				String[] factorList2 = monomialList[j].split("\\*");
				if (factorList.length != factorList2.length || monomialList[j].length() == 0) {
					continue;
				}
				
				boolean isSame = true;
				for (int k = 1; k < factorList.length; k++) {
					if (!factorList[k].equals(factorList2[k])) {
						isSame = false;
						break;
					}
				}
				if (isSame) {
					StringBuffer buffer = new StringBuffer();
					final int factor = Integer.parseInt(factorList[0]);
					final int factor2 = Integer.parseInt(factorList2[0]);
					factorList2[0] = String.valueOf(factor + factor2);
					for (int k = 0; k < factorList2.length; k++) {
						buffer.append(factorList2[k]);
						if (k < factorList2.length - 1) {
							buffer.append('*');
						}
						// 由factorList重构monomial
					}
					monomialList[i] = "";
					monomialList[j] = buffer.toString();
				}
			}
		}
		// Step2：合并同类项

		for (int i = 0; i < monomialList.length; i++) {
			if (monomialList[i].length() > 2 
					&& monomialList[i].charAt(0) == '1' 
					&& monomialList[i].charAt(1) == '*') {
				monomialList[i] = monomialList[i].substring(2, monomialList[i].length());
			}
			
			/*
			String[] factorList = monomialList[i].split("\\*");
			monomialList[i] = "";
			if ("1".equals(factorList[0]) && factorList.length > 1) {
				factorList[0] = "";
			} else {
				if (factorList.length == 1) {
					monomialList[i] = factorList[0];
				} else {
					monomialList[i] = factorList[0] + "*";
				}
			}
			for (int j = 1; j < factorList.length; j++) {
				monomialList[i] = monomialList[i] + factorList[j];
				if (j < factorList.length - 1) {
					monomialList[i] = monomialList[i] + "*";
				}
			}
			*/
		}
		// Step3：将系数为1项删除系数

		for (int i = 0; i < monomialList.length; i++) {
			if (!"".equals(monomialList[i]) && !"0".equals((monomialList[i]))) {
				afterExp.append(monomialList[i]);
				afterExp.append('+');
			}
		}
		if (afterExp.length() == 0) {
			afterExp.append("0+");
		}
		afterExp.deleteCharAt(afterExp.length()-1);
		// Step4：将项为0的情况删除
		return afterExp.toString();

	}

	/**
	 * 	对变量求导.
	 * @param line 求导命令字符串
	 * @param polynomial  原表达式
	 * @return 若输入正确，返回求导后的字符串，否则返回错误信息
	 */
	public final String derivation(final String line, final String polynomial) {
		String afterPolynomial = polynomial;
		final String varable = line.split("d")[2];
		String[] monomialArr = afterPolynomial.split("\\+");
		afterPolynomial = "";
		for (int i = 0; i < monomialArr.length; i++) {
			int power = 0;
			final String[] monomial = monomialArr[i].split("\\*");
			monomialArr[i] = "";
			for (int j = 0; j < monomial.length; j++) {
				if (varable.equals(monomial[j])) {
					power++;
				}
			}
			if (power == 0) {
				monomialArr[i] = "";
			} else {
				boolean changed = false;
				for (int j = 0; j < monomial.length; j++) {
					if (varable.equals(monomial[j]) && !changed) {
						changed = true;
						monomial[j] = String.valueOf(power);
					}
					monomialArr[i] = monomialArr[i] + monomial[j];
					if (j < monomial.length - 1) {
						monomialArr[i] = monomialArr[i] + "*";
					}
				}
			}
			final String mono = monomialArr[i];
			if (!"".equals(mono)) {
				afterPolynomial = afterPolynomial + monomialArr[i];
				if (i < monomialArr.length - 1) {
					afterPolynomial = afterPolynomial + "+";
				}
			}
		}
		return afterPolynomial;
	}

	/**
	 * 程序主入口.
	 * @param args 参数
	 * @throws IOException IO异常
	 */
	public static void main(final String[] args) throws IOException {
		BufferedReader buffer = new BufferedReader(
				new InputStreamReader(System.in));
		String line;
		String polynomial = "";
		String originalPoly = polynomial;
		while (true) {
			line = buffer.readLine();
			if("".equals(line)) {
				continue;
			}
			Laboratory4 test = new Laboratory4();
			final long start = System.nanoTime();
			System.out.println("算法开始时间： " + start + "ns");
			if (line.charAt(0) != '!') {
				polynomial = test.checkSyntax(line);
				polynomial = test.simplify(polynomial);
				originalPoly = polynomial;
			} else if (line.charAt(1) == 's') {
				polynomial = test.substitute(line, originalPoly);
				polynomial = test.simplify(polynomial);
			} else if (line.charAt(1) == 'd') {
				polynomial = test.derivation(line, originalPoly);
				polynomial = test.simplify(polynomial);
			} else {
				polynomial = "error";
			}
			
			if (line.charAt(0) == '-') {
				break;
			}

			System.out.println(polynomial);
			final long end = System.nanoTime();
			System.out.println("算法结束时间： " + end + "ns");
			System.out.println("算法运行时间： " + (end - start) + "ns");
		}

	}
}