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


}
