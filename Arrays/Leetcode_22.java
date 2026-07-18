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

Approach 2 : Using DP

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



 Let's do this in the cleanest possible way — pure approach first, completely separate from code, using a story-like build-up.

 ---

 ## THE APPROACH — no code, no formulas

 ## The big question

 You want every valid way to arrange `n` pairs of parentheses. How do you even begin thinking about this?

 ## Idea: what if you already had the answers for SMALLER numbers of pairs?

 **Suppose someone already handed you: every valid arrangement using 0 pairs, every valid arrangement using 1 pair, and every valid arrangement using 2 pairs.** Could you use THAT information to figure out every valid arrangement using 3 pairs, WITHOUT starting from scratch?

 ## Let's discover the answer by looking at ONE valid 3-pair string closely

 Take `"(())()"`.

 **Look at the very first character.** It's `(`. Every valid string MUST start with `(` — there's no other option (starting with `)` is instantly invalid).

 **Now find the closing bracket that matches THIS specific first `(`.** Walk through the string: `( ( ) ) ( )` — the very first `(` is at position 0. Its match is the `)` at position 3 (they pair up because everything between them, `"()"`, is itself balanced).

 **This matching pair divides the whole string into two zones:**
 - **Zone 1 (INSIDE the first pair):** whatever sits between position 0 and position 3 → that's `"()"`
 - **Zone 2 (AFTER the first pair closes):** whatever comes after position 3 → that's `"()"`

 ```
 (   ()   )   ()
 ↑ zone1 ↑  zone2
 ```

 ## The key discovery

 **Zone 1 (`"()"`) is, all by itself, a perfectly valid parentheses string.** **Zone 2 (`"()"`) is ALSO, all by itself, a perfectly valid parentheses string.** Neither zone needs the other to "make sense" — each one is independently balanced and correct.

 ## Why this discovery is powerful

 **This means EVERY valid string, no matter how big, is secretly just: an opening bracket, then some SMALLER valid string (Zone 1), then a closing bracket, then ANOTHER smaller valid string (Zone 2).**

 **So — if you already have a complete list of every valid smaller string, you can build every valid BIGGER string by trying every possible combination: pick something for Zone 1, pick something for Zone 2, glue them together with a bracket pair around Zone 1.**

 ## The strategy, stated as a plan

 1. **Start small.** Figure out every valid arrangement for 0 pairs (trivially, just the empty string).
 2. **Use that to build 1 pair's answers**, by trying every Zone 1 / Zone 2 combination using only 0-pair answers.
 3. **Use 0-pair AND 1-pair answers to build 2-pair's answers.**
 4. **Use 0, 1, AND 2-pair answers to build 3-pair's answers.**
 5. **Keep climbing up, one pair-count at a time, until you reach `n`.**

 **Each step only ever uses answers you've ALREADY fully figured out in earlier steps — you never have to guess or re-verify something from scratch.**

 ---

 ## Check-in before moving to code

 **Does this — "any valid string splits into (bracket + smaller valid piece + bracket) + (another smaller valid piece), so build up from small answers to bigger ones" — make sense as the core strategy now, completely separate from any code?**

 If yes, tell me and I'll walk through the code next, showing EXACTLY how each line implements this exact plan — Step 1 becomes one line, Step 2-5 becomes a loop, etc.

 */
    }

}
