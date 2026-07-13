package DATA_STRUCTURE_AND_ALGORITHM.Arrays;

public class Leetcode_242
{
/**
 * Character Type	Range (Decimal)	             Key Examples
 * Numbers	         48 – 57	           '0'=48, '1'=49, ..., '9'=57
 * Uppercase	     65 – 90	           'A'=65, 'B'=66, ..., 'Z'=90
 * Lowercase	     97 – 122	           'a'=97, 'b'=98, ..., 'z'=122

  */
public static void main(String[] args)
{
     String s = "abdc";
     String t = "dbac";
     boolean x = isAnagram(s,t);
    System.out.println(x);
}

        public static boolean isAnagram(String s, String t)
        {
            if ( s.length() != t.length() ) return false;
            // int xor1 ;
            // int xor2 ;
            // Not valid ---> for the given two strings if the two strings are same such as s ="aa", t="bb"
            // then answer will not be same not valid for the edge case
            // but true for the same case

            // if( s.length() == t.length() )
            // {
            //     xor1 = s.charAt(0);
            //     xor2 = t.charAt(0);

            //     for ( int i = 1; i<s.length() ; i++ )
            //     {
            //         xor1^=s.charAt(i);
            //         xor2^=t.charAt(i);
            //     }
            //     return ( xor1 == xor2 );
            // }
            // else return false;

    /*
    Approach 1 : I will use Sorting algorithm called merge sort so the time complexity
    will be o( N LOG N + N LOG N ) and cheking the string after sorting it  O ( 2N LOG N + N )=> O( N( 2LOGN + 1 ))
    */
            StringBuilder x = new StringBuilder(s);
            StringBuilder y = new StringBuilder(t);
            mergeSort( x, 0 , s.length()-1 );
            mergeSort( y, 0 , t.length()-1 );
            System.out.println(" x "+x+ " y "+y );
            return check( x , y );

        }
        public static  void mergeSort( StringBuilder s , int left , int right )
        {
            if ( left >= right ) return;
            int mid = left + ( right - left )/2;
            mergeSort( s , left , mid );
            mergeSort( s , mid+1, right);
            merge( s , left, mid , right  );
        }
        public static void merge( StringBuilder s , int left , int mid , int right )
        {
            int [] arr = new int [ right - left + 1 ];
            int i = left ;
            int j = mid + 1 ;
            int k = 0;
            while ( i <= mid && j<= right )
            {
                if ( s.charAt( i ) >= s.charAt( j ) ) arr[ k++ ]=s.charAt( j++ );
                else arr[ k++ ]= s.charAt( i++ );
                System.out.println("1"+ (char)arr [ k-1 ]+" ");
            }

            while( i <= mid ){
                arr[ k++ ] = s.charAt( i++ );
                System.out.println("2"+(char)arr[k-1]+" ");
            }
            while( j <= right ) {
                arr[ k++ ] = s.charAt( j++ );
                System.out.print("3"+(char)arr[k-1]+" ");
            }

            k = 0;
            while ( k < arr.length ) s.setCharAt( left+k , (char)arr[k++]) ;
            // i forgot the left + k line
            System.out.println("StringBuilder "+s);
            System.out.println();
        }
        public static boolean check ( StringBuilder x , StringBuilder y )
        {

            for ( int i = 0; i<x.length() ; i++ )if ( x.charAt(i) != y.charAt(i)) return false;

            return true;
        }
    }


