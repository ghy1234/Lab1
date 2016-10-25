
/**
 * Created by think on 2016/9/28.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test {

	/**
	 * 查看输入字符串是否合法 <br>
	 * 规则：1）变量名为单个字符 2）只包含数字、字母、+和*的标准多项式
	 * 
	 * @param line
	 *            输入字符串
	 * @return 如果合法,则原样输出，否则输出error
	 */
	public String checkSyntax(String line) {
		boolean error = false;
		for (int i = 0; i < line.length(); i++) {
			if ((line.charAt(i) != '+') && (line.charAt(i) != '*')) {
				if (Character.isLetterOrDigit(line.charAt(i)) == false) {
					error = true;
				}
			}
			if (i > 0) {
				if (((line.charAt(i) == '+') || (line.charAt(i) == '*'))
						&& ((line.charAt(i - 1) == '+') || (line.charAt(i - 1) == '*'))) {
					error = true;
				}
				if ((Character.isLetterOrDigit(line.charAt(i)) == true)
						&& (Character.isLetterOrDigit(line.charAt(i - 1)) == true)) {
					error = true;
					if ((Character.isDigit(line.charAt(i))) && (Character.isDigit(line.charAt(i - 1)) == true)) {
						error = false;
					}
				}
			}
		}
		if (error) {
			line = "Error";
		}
		return line;
	}

	/**
	 * 将变量的值代入多项式
	 * 
	 * @param line
	 *            输入的用于代入的表达式， polynomial 多项式
	 * @return 如果合法,返回代入后的多项式，否则返回Error
	 */
	public String substitute(String line, String polynomial) {
		String[] substitutionArr;
		substitutionArr = line.split(" ");
		if (!"!simplify".equals(substitutionArr[0])) {
			return "Error";
		}
		for (int i = 1; i < substitutionArr.length; i++) {
			String[] substitution = substitutionArr[i].split("=");
			String variable = substitution[0];
			String value = substitution[1];
			String[] monomialArr = polynomial.split("\\+");
			polynomial = "";
			for (int j = 0; j < monomialArr.length; j++) {
				String[] monomial = monomialArr[j].split("\\*");
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
				polynomial = polynomial + monomialArr[j];
				if (j < monomialArr.length - 1) {
					polynomial = polynomial + "+";
				}
			}
		}
		return polynomial;
	}

	/**
	 * 将表达式进行化简和格式化
	 * 
	 * @param exp
	 *            表达式的字符串
	 * @return 化简并格式化后的字符串
	 */
	public String simplify(String exp) {
		String[] monomialList = exp.split("\\+");
		String afterExp = ""; // 化简后的表达式
		String monomial = "";
		for (int i = 0; i < monomialList.length; i++) {
			int constant = 1;
			String[] factorList = monomialList[i].split("\\*");
			monomial = "";
			for (int j = 0; j < factorList.length; j++) {
				if (Character.isDigit(factorList[j].charAt(0))) {
					int factor = Integer.parseInt(factorList[j]);
					constant = constant * factor;
				} else {
					monomial = monomial + "*" + factorList[j];
				}
			}
			monomial = String.valueOf(constant) + monomial;
			afterExp = afterExp + monomial;
			if (i < monomialList.length - 1) {
				afterExp = afterExp + "+";
			}
		}

		afterExp = "0+" + afterExp;
		// Step1: 将所有的系数相乘

		monomialList = afterExp.split("\\+");
		afterExp = "";
		for (int i = 0; i < monomialList.length; i++) {
			String[] factorList = monomialList[i].split("\\*");
			for (int j = 0; j < i; j++) {
				String[] factorList2 = monomialList[j].split("\\*");
				boolean isSame = true;
				if ("".equals(monomialList[j])) {
					isSame = false;
				}
				if (factorList.length == factorList2.length) {
					for (int k = 1; k < factorList.length; k++) {
						if (!factorList[k].equals(factorList2[k])) {
							isSame = false;
						}
					}
				} else {
					isSame = false;
				}
				if (isSame) {
					monomialList[i] = "";
					monomialList[j] = "";

					int factor = Integer.parseInt(factorList[0]);
					int factor2 = Integer.parseInt(factorList2[0]);
					factorList2[0] = String.valueOf(factor + factor2);
					for (int k = 0; k < factorList2.length; k++) {
						monomialList[j] = monomialList[j] + factorList2[k];
						if (k < factorList2.length - 1) {
							monomialList[j] = monomialList[j] + "*";
						}
						// 由factorList重构monomial
					}
				}
			}
		}
		// Step2：合并同类项

		for (int i = 0; i < monomialList.length; i++) {
			String[] factorList = monomialList[i].split("\\*");
			monomialList[i] = "";
			if ("1".equals((factorList[0])) && factorList.length > 1) {
				factorList[0] = "";
			} else {
				if (factorList.length != 1) {
					monomialList[i] = factorList[0] + "*";
				} else {
					monomialList[i] = factorList[0];
				}
			}
			for (int j = 1; j < factorList.length; j++) {
				monomialList[i] = monomialList[i] + factorList[j];
				if (j < factorList.length - 1) {
					monomialList[i] = monomialList[i] + "*";
				}
			}
		}
		// Step3：将系数为1项删除系数

		for (int i = 0; i < monomialList.length; i++) {
			if (!"".equals(monomialList[i]) && !"0".equals((monomialList[i]))) {
				afterExp = afterExp + monomialList[i];
				afterExp = afterExp + "+";
			}
		}
		if ("".equals(afterExp)) {
			afterExp = "0+";
		}
		afterExp = afterExp.substring(0, afterExp.length() - 1);
		// Step4：将项为0的情况删除
		return afterExp;

	}

	/**
	 * 对变量求导
	 * 
	 * @param line
	 *            求导命令字符串 polynomial 原表达式
	 * @return 返回求导后的多项式
	 */
	public String derivation(String line, String polynomial) // 4*x+y*x*x+y*2+y+x+z*x*y
																// !d/dx
	{
		String varable = line.split("d")[2];
		String[] monomialArr = polynomial.split("\\+");
		polynomial = "";
		for (int i = 0; i < monomialArr.length; i++) {
			int power = 0;
			String[] monomial = monomialArr[i].split("\\*");
			monomialArr[i] = "";
			for (int j = 0; j < monomial.length; j++) {
				if (varable.equals(monomial[j]) == true) {
					power++;
				}
			}
			if (power == 0) {
				monomialArr[i] = "";
			} else {
				boolean changed = false;
				for (int j = 0; j < monomial.length; j++) {
					if ((varable.equals(monomial[j]) == true) && !changed) {
						changed = true;
						monomial[j] = String.valueOf(power);
					}
					monomialArr[i] = monomialArr[i] + monomial[j];
					if (j < monomial.length - 1) {
						monomialArr[i] = monomialArr[i] + "*";
					}
				}
			}
			if (monomialArr[i].equals("") == false) {
				polynomial = polynomial + monomialArr[i];
				if (i < monomialArr.length - 1) {
					polynomial = polynomial + "+";
				}
			}
		}
		return polynomial;
	}

	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line;
		String polynomial = "";
		String originalPoly = polynomial;
		while (true) {
			line = br.readLine();
			Test test = new Test();
			long start = System.nanoTime();
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
			long end = System.nanoTime();
			System.out.println("算法结束时间： " + end + "ns");
			System.out.println("算法运行时间： " + (end - start) + "ns");
		}

	}
}