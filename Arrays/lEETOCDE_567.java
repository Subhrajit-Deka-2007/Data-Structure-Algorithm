package Arrays;
import java.util.Arrays;
public class lEETOCDE_567
{

    public boolean checkInclusion(String s1, String s2)
    {
        if (s1.length() > s2.length()) return false;

        int[] arr1Freq = new int[26];
        int[] arr2Freq = new int[26];

        // Build s1's frequency array
        for (int i = 0; i < s1.length(); i++) {
            arr1Freq[s1.charAt(i) - 97]++;
        }

        int i = 0;
        int j = 0;

        for (; j < s2.length(); ) {

            // Build the initial window (your original logic — kept as-is)
            if (j - i + 1 <= s1.length()) {
                arr2Freq[s2.charAt(j) - 97]++;

                // check right here, once the window first reaches full size
                if (j - i + 1 == s1.length() && Arrays.equals(arr1Freq, arr2Freq)) {
                    return true;
                }

                j++;
                continue;
            }

            // Slide: remove the character leaving from the left, add the new one entering at j
            arr2Freq[s2.charAt(i) - 97]--;
            i++;

            arr2Freq[s2.charAt(j) - 97]++;
            j++;

            // Check after sliding
            if (Arrays.equals(arr1Freq, arr2Freq)) {
                return true;
            }
        }
        /*
        Time Complexity = O (N)
        Space Complexity = O (26 + 26 )
        */

        return false;
    }
/**
 * Instead of using Arrays.equal() we are maintaining a variable that represents how many characters are matching

 class Solution {
 public boolean checkInclusion(String s1, String s2) {
 if (s1.length() > s2.length()) return false;

 int[] arr1Freq = new int[26];
 int[] arr2Freq = new int[26];
 int matches = 0;

 // Build s1's frequency array
 for (int i = 0; i < s1.length(); i++) {
 arr1Freq[s1.charAt(i) - 'a']++;
 }

 int i = 0, j = 0;

 while (j < s2.length()) {

 // Phase 1: build the initial window
 if (j - i + 1 <= s1.length()) {
 int idx = s2.charAt(j) - 'a';

 boolean wasEqual = (arr2Freq[idx] == arr1Freq[idx]);
 arr2Freq[idx]++;
 boolean isEqualNow = (arr2Freq[idx] == arr1Freq[idx]);

 if (!wasEqual && isEqualNow) matches++;
 if (wasEqual && !isEqualNow) matches--;

 if (j - i + 1 == s1.length() && matches == 26) {
 return true;
 }

 j++;
 continue;
 }

 // Phase 2: slide — remove the character leaving (at i)
 int leaveIdx = s2.charAt(i) - 'a';
 boolean wasEqualLeave = (arr2Freq[leaveIdx] == arr1Freq[leaveIdx]);
 arr2Freq[leaveIdx]--;
 boolean isEqualNowLeave = (arr2Freq[leaveIdx] == arr1Freq[leaveIdx]);
 if (!wasEqualLeave && isEqualNowLeave) matches++;
 if (wasEqualLeave && !isEqualNowLeave) matches--;
 i++;

 // Phase 2: slide — add the character entering (at j)
 int enterIdx = s2.charAt(j) - 'a';
 boolean wasEqualEnter = (arr2Freq[enterIdx] == arr1Freq[enterIdx]);
 arr2Freq[enterIdx]++;
 boolean isEqualNowEnter = (arr2Freq[enterIdx] == arr1Freq[enterIdx]);
 if (!wasEqualEnter && isEqualNowEnter) matches++;
 if (wasEqualEnter && !isEqualNowEnter) matches--;
 j++;// to stop checking the thing if ( matches == 26 ) we need to stop these but right after sliding we check again

 if (matches == 26) {
 return true;
 }
 }

 return false;
 }
 }
 */

}
