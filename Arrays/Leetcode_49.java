package DATA_STRUCTURE_AND_ALGORITHM.Arrays;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Leetcode_49
{
    /*
    Approach 1: Time Limit exceeded error i am doing redundant work
    class Solution {
    public List<List<String>> groupAnagrams(String[] strs)
    {




        List<String> ls ;
        List<List<String>> ans  = new ArrayList<>();

        if ( strs.length == 1 )
        {

          ls = new ArrayList<>();
          ls.add(strs[0]);
          ans.add(ls);
          return ans;
        }

        boolean [] check = new boolean[strs.length];

        for ( int i = 0 ; i < strs.length-1 ; i++ )
        {

            ls = new ArrayList<>();

            if(!check[i])
            {

                ls.add(strs[i]);

                 for ( int j = i + 1 ; j<strs.length ; j++ )
                 {
                         if ( !check[ j ] )
                            {
                                if( isValidAnagram( strs[i], strs[j] ) )
                                   {
                                      ls.add( strs[j] );
                                      check[j] = true;
                                    }

                             }


                   }

             ans.add(ls);

            }


        }

        if ( ! check [ strs.length - 1 ] )
         {
            ls = new ArrayList<>();
            ls.add( strs[strs.length - 1 ]);
            ans .add(ls);

         }

        return ans;

    }




       public static boolean isValidAnagram(String s, String t)
        {
            if ( s.length() != t.length() ) return false;

            StringBuilder x = new StringBuilder(s);
            StringBuilder y = new StringBuilder(t);

            mergeSort( x, 0 , s.length() - 1 );
            mergeSort( y, 0 , t.length() - 1 );

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

            }

            while( i <= mid )arr[ k++ ] = s.charAt( i++ );

            while( j <= right )  arr[ k++ ] = s.charAt( j++ );



            k = 0;
            while ( k < arr.length ) s.setCharAt( left+k , (char)arr[k++]) ;


        }


        public static boolean check ( StringBuilder x , StringBuilder y )
        {

            for ( int i = 0; i<x.length() ; i++ )if ( x.charAt(i) != y.charAt(i)) return false;

            return true;
        }
    }


     */


    /* Approach 2 removing the redundant work i.e. sorting for a particular i do it at first on traversing
    * the other elements of the array i sort that plus i compare with that current element in previous approach
    * i used to sort both elements again and again
    *
    *
    *
    *
    * class Solution {
    public List<List<String>> groupAnagrams(String[] strs)
    {




        List<String> ls ;
        List<List<String>> ans  = new ArrayList<>();

        if ( strs.length == 1 )
        {

          ls = new ArrayList<>();
          ls.add(strs[0]);
          ans.add(ls);
          return ans;
        }

        boolean [] check = new boolean[strs.length];

       StringBuilder sb = new StringBuilder("");

        for ( int i = 0 ; i < strs.length-1 ; i++ )
        {

            ls = new ArrayList<>();

            if(!check[i])
            {
                sb.setLength(0);
                String o = strs[i];
                sb.append(sort(o));



                ls.add(strs[i]);

                 for ( int j = i + 1 ; j<strs.length ; j++ )
                 {
                         if ( !check[ j ] )
                            {
                                if( isValidAnagram( sb, strs[j] ) )
                                   {
                                      ls.add( strs[j] );
                                      check[j] = true;
                                    }

                             }


                   }

             ans.add(ls);

            }


        }

        if ( ! check [ strs.length - 1 ] )
         {
            ls = new ArrayList<>();
            ls.add( strs[strs.length - 1 ]);
            ans .add(ls);

         }

        return ans;

    }

*/

public static String  sort( String y )
{
    StringBuilder s = new StringBuilder(y);

     mergeSort( s, 0 , s.length() - 1 );
     return s.toString();
}
       public static boolean isValidAnagram(StringBuilder s, String t)
        {

            if ( s.length() != t.length() ) return false;

            StringBuilder y = new StringBuilder(t);

            mergeSort( y, 0 , t.length() - 1 );
            return check( s , y );

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

            }

            while( i <= mid )arr[ k++ ] = s.charAt( i++ );

            while( j <= right )  arr[ k++ ] = s.charAt( j++ );



            k = 0;
            while ( k < arr.length ) s.setCharAt( left+k , (char)arr[k++]) ;


        }


        public static boolean check ( StringBuilder x , StringBuilder y )
        {

            for ( int i = 0; i<x.length() ; i++ )if ( x.charAt(i) != y.charAt(i)) return false;

            return true;
        }



    /* Approach : 3*/
    public static List<List<String>> groupAnagrams(String[] strs)
    {
     /*
     Apparoach 3 :
     Using Hashmap : I will traverse each element in the array and for each element in the array i will
     check two things if we sorted the current element does that sorted thing is there inside the hasmap if it is already there then we will put in the same list with that element and if the element is not present in that hashamp i will put that sorted thing insinde the hasmap and i will also create a list for that particular element
     */

    int count = 0;
    StringBuilder sb = new StringBuilder( sort(strs[0]) );
    HashMap<String,Integer> map = new HashMap<>();
      map.put( sb.toString(), count++ );
    List<String>ls = new ArrayList<>();
    List<List<String>> ans = new ArrayList<>();
      ls.add(strs[0]);
      ans.add(ls);


      for( int i = 1 ; i<strs.length; i++ )
    {
        sb.setLength(0);
        String s = sort(strs[i]);
        if ( map.containsKey(s) )ans.get(map.get(s)).add(strs[i]);
        else
        {
            map.put(s,count++);
            ls = new ArrayList<>();
            ls.add(strs[i]);
            ans.add(ls);
        }

    }

     return ans;

/*
T.C = O ( N * L LOG L )
S.C = O(N + LOG L + N + L )
*/
}









}
