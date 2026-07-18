package Arrays;

import java.util.ArrayList;
import java.util.List;

public class Leetcode_22
{

    class Solution {
        public List<String> generateParenthesis(int n)
        {
            List<String> ans = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            backtrack(sb,0,0,n,ans);
            return ans;
        }



        void backtrack(StringBuilder sb, int opens, int closes, int n, List<String> result)
        {
            if (sb.length() == 2 * n) {
                result.add(sb.toString());
                return;
            }

            if (opens < n) {
                sb.append('(');
                backtrack(sb, opens + 1, closes, n, result);
                sb.deleteCharAt(sb.length() - 1);  // undo the '(' we just tried
            }

            if (closes < opens) {
                sb.append(')');
                backtrack(sb, opens, closes + 1, n, result);
                sb.deleteCharAt(sb.length() - 1);  // undo the ')' we just tried
            }
        }
/**
 No problem — let's completely restart this, no formulas at all, just counting things by hand.

 ## Forget the formula. Let's just COUNT things for a tiny example.

 ```
 n = 2 (we want all valid combinations using 2 pairs of parentheses)
 ```

 ## Question 1 — how many valid answers are there for n=2?

 Let's just list them out, like we did before:
 ```
 "(())"
 "()()"
 ```
 **That's it — exactly 2 valid answers for n=2.**

 ## Question 2 — how long does it take to WRITE OUT one answer?

 Look at `"(())"` — it has **4 characters**. To build this string character by character (which is what your code does — appending one bracket at a time), you need **4 steps**.

 **In general: for `n` pairs, each valid answer has length `2n`** (n opening brackets + n closing brackets). So writing out ONE answer takes about `2n` steps.

 ## Question 3 — if there are 2 valid answers, and each takes 4 steps to write, roughly how much total work is that?

 ```
 2 answers × 4 steps each = 8 total steps (roughly)
 ```

 **This is the ENTIRE idea behind the time complexity — nothing more mysterious than this:**

 ```
 Total work ≈ (HOW MANY valid answers exist) × (HOW LONG it takes to write out each one)
 ```

 ## Now — the only hard part: HOW MANY valid answers exist, for a general `n` (not just n=2)?

 **This is where it gets genuinely complicated, and this is the ONE piece that mathematicians have already figured out for us — we don't need to derive it.** For `n` pairs of parentheses, the **number of valid combinations** follows a specific counting pattern:

 ```
 n=1: 1 valid answer   → "()"
 n=2: 2 valid answers  → "(())", "()()"
 n=3: 5 valid answers  → "((()))","(()())","(())()","()(())","()()()"
 n=4: 14 valid answers
 n=5: 42 valid answers
 ```

 **Notice this grows FAST — 1, 2, 5, 14, 42... This specific sequence of numbers has a name: Catalan numbers.** Mathematicians have already studied this sequence extensively and figured out that it grows at roughly the rate `4^n / n^1.5` — **this is just a fact you can look up and use, like knowing that `2^10 = 1024`, not something you need to figure out yourself.**

 ## Putting it together — the whole idea, restated simply

 ```
 Total work = (number of valid answers, which follows the Catalan number pattern: 1,2,5,14,42...)
 ×
 (length of each answer, which is 2n)
 ```

 Since the number of valid answers grows like `4^n / n^1.5` (a known fact), and each one takes `2n` (which simplifies to just `n`, dropping constants) to write out:

 ```
 Total work ≈ (4^n / n^1.5) × n = 4^n / n^0.5 = 4^n / √n
 ```

 **That's it. That's the whole derivation.** `4^n / √n` is just "(number of valid answers) times (length of each answer)," where the "number of valid answers" part is a known mathematical fact (Catalan numbers) that you use directly, without needing to prove it yourself.

 ## Check-in

 **Does this — "count the answers (a known fact), multiply by how long each answer takes to write (which you CAN figure out yourself, it's just 2n)" — make sense as the whole idea, without worrying about WHERE the Catalan number formula itself comes from?**

 If yes, that's genuinely the complete picture — the only thing you're "trusting" rather than deriving is that specific sequence 1,2,5,14,42... and its growth rate, which is a standard fact you can just know, the same way you know `n!` grows really fast without re-deriving why every time.


 Time  Complexity = O ( 4 ^ N / ROOT OVER N )
 SPACE COMPLEXITY = O ( 2N ) TO BUILD AN ANSWER WE NEED 2N RECURSIVE CALLS  SO THE RECURSIVE STACK SPACE IS 2N

 */


/**
 List<List<String>> dp = new ArrayList<>();
 dp.add(Arrays.asList("")); // dp[0]

 for (int i = 1; i <= n; i++) {
 List<String> current = new ArrayList<>();
 for (int j = 0; j < i; j++) {
 for (String left : dp.get(j)) {
 for (String right : dp.get(i - 1 - j)) {
 current.add("(" + left + ")" + right);
 }
 }
 }
 dp.add(current);
 }

 return dp.get(n);
 */
    }

}
