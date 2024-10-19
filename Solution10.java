/**
 * @description:
 * 给定一个表示分数加减运算的字符串 expression ，你需要返回一个字符串形式的计算结果。
 *
 * 这个结果应该是不可约分的分数，即最简分数。 如果最终结果是一个整数，例如 2，你需要将它转换成分数形式，其分母为 1。所以在上述例子中, 2 应该被转换为 2/1。
 *
 *
 *
 * 示例 1:
 *
 * 输入: expression = "-1/2+1/2"
 * 输出: "0/1"
 *  示例 2:
 *
 * 输入: expression = "-1/2+1/2+1/3"
 * 输出: "1/3"
 * 示例 3:
 *
 * 输入: expression = "1/3-1/2"
 * 输出: "-1/6"
 *
 *
 * 提示:
 *
 * 输入和输出字符串只包含 '0' 到 '9' 的数字，以及 '/', '+' 和 '-'。
 * 输入和输出分数格式均为 ±分子/分母。如果输入的第一个分数或者输出的分数是正数，则 '+' 会被省略掉。
 * 输入只包含合法的最简分数，每个分数的分子与分母的范围是  [1,10]。 如果分母是1，意味着这个分数实际上是一个整数。
 * 输入的分数个数范围是 [1,10]。
 * 最终结果的分子与分母保证是 32 位整数范围内的有效整数。
 */
class Solution10 {
    public String fractionAddition(String expression) {
        long numerator = 0, denominator = 1; // 分子，分母
        int index = 0, n = expression.length();
        while (index < n) {
            // 读取符号
            int sign = 1;
            if (expression.charAt(index) == '-' || expression.charAt(index) == '+') {
                sign = expression.charAt(index) == '-' ? -1 : 1;
                index++;
            }

            long numerator1 = 0;
            while (index < n && Character.isDigit(expression.charAt(index))) {
                numerator1 = numerator1 * 10 + expression.charAt(index) - '0';
                index++;
            }
            numerator1 = sign * numerator1;
            index++;

            long denominator1 = 0;
            while (index < n && Character.isDigit(expression.charAt(index))) {
                denominator1 = denominator1 * 10 + expression.charAt(index) - '0';
                index++;
            }

            numerator = numerator * denominator1 + numerator1 * denominator;
            denominator *= denominator1;

            long g = gcd(Math.abs(numerator), Math.abs(denominator));
            numerator /= g;
            denominator /= g;
        }

        if (numerator == 0) {
            return "0/1";
        }

        if (denominator < 0) {
            numerator = -numerator;
            denominator = -denominator;
        }
        return numerator + "/" + denominator;
    }

    private long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public static void main(String[] args) {
        Solution10 solution = new Solution10();
        String[] expressions = {
                "-1/2+1/2",
                "-1/2+1/2+1/3",
                "1/3-1/2",
                "5/3+1/3",
                "-2/3+1/3",
                "7/10-2/5+1/2"
        };
        for (String expression : expressions) {
            String result = solution.fractionAddition(expression);
            System.out.println("表达式: " + expression + " = " + result);
        }
    }
}