package BitManipulation;

public class Leetcode191
{
    class Solution {
        public int hammingWeight(int n)
        {
        /*
        Brut force I can use the The method to convert the number into binary and whenever i got the reminder 1 I
        can increase the count to one
        */
            return optimize(n);
        }

        public int brutforce(int n )
        {

            int a = 2;
            int b = n;
            int count =0;
            while(b!=1)
            {
                if(b%a==1)count++;
                b=b/a;

            }
            return count+1;
        }
    /*
    Time Complexity => Log N

    Setting up the GP
    Your series:
    N,N/2,N/4,…,1
    This is a GP with:
    first term a = N
    ratio r = 1/2
    last term an = 1


    Space Complexity => O(1)


The 6 steps, summarized as a checklist for next time

Define T(n) — what does it count?
Write the recurrence — cost of one step + T(smaller input)
Find the base case — where does it stop, what's T there?
Unroll — substitute the rule into itself 2-3 times, watch a pattern form
Generalize with k — write the pattern after k substitutions
Solve for 𝑘 using the base case, substitute back to get final 𝑇(𝑛)

    */
    /*
    Important: this is natural log, not log base 2. Java doesn't have a direct log2 function, so you need the change-of-base formula:
    */

        public int optimize( int n )
        {
            int noOfBits = (int) (Math.floor(Math.log(n) / Math.log(2))) + 1;
            System.out.println(noOfBits);
            int count = 0;
            for( int i = 0; i < noOfBits ; i++ )
                if( ( n & (1<<i))!=0) count++;


            return count;

        }
    /*
    Time Complexity = O(32)IN WORST CASE
    Space Complexity = o(1)
    */

        public int moreoptimize(int n )
        {
            int count = 0;
            while (n != 0) {
                n = n & (n - 1);   // clears the lowest set bit
                count++;
            }
            return count;
        }
/*
O(Number of 1 bits )
On each step we remove one bit
O(K)
Space Complexity = O(1)


Brian Kernighan's Algorithm
Kernighan's bit trick
n & (n-1) trick
*/
        // Integer.bitCount() => Learn these later on
    }
}
