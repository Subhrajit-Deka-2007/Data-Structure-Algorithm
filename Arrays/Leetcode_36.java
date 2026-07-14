package Arrays;

import java.util.HashSet;
import java.util.Set;

public class Leetcode_36
{
    public boolean isValidSudoku(char[][] board)
    {
    /*
     We need to create three types of HashSet one for the rows , columns and boxes and all
    array of hashset each of 9 size
    */
        Set<Character>[] rows  = new HashSet[9];
        Set<Character>[] cols  = new HashSet[9];
        Set<Character>[] boxes = new HashSet[9];

        /* First we are traversing the array to fill the boxes. */
        char c ;
        for ( int i = 0 ; i< 9 ; i++ )
        {
            rows[i] = new HashSet<>();
            cols[i]= new HashSet<>();
            boxes[i]= new HashSet<>();
        }
        for( int i = 0 ; i < board.length ; i++ )
        {
            for ( int j = 0 ; j < board[0].length ; j++  )
            {          c = board[i][j];
                if( rows[i].contains(c)||cols[j].contains(c) || boxes[ ( (i/3)*3 + (j/3) ) ].contains(c)  )return false;
                if( c!='.')
                {
                    rows[i].add(c);
                    cols[j].add(c);
                    boxes[ ( (i/3)*3 + (j/3) ) ].add(c);
                }
            }
        }

/*
T.C = O ( N * M )
S.C = O ( N +  M + 9 )
*/

        return true;
    }



    static {
        for (int index = 0; index < 100; index++)
            isValidSudok(new char[9][9]);
    }
    public static boolean isValidSudok(char[][] board) {
        int[] rowMask = new int[9];
        // each cell has 9 bits and each bit represent position and initially each bit was turn off
        // on looking for the first time we turn it off since we are doing 0th based indexing we do
        // turn on the bit one less
        /*
         position: 8 7 6 5 4 3 2 1 0
         value:    0 0 0 0 0 0 0 0 1
         */
        int[] columnMask = new int[9];
        int[] boxMask = new int[9];

        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                if (board[row][column] == '.') continue;
                int bit = 1 << (board[row][column] - '1');
                int box = (row / 3) * 3 + column / 3;

                if ((rowMask[row] & bit) != 0 || (columnMask[column] & bit) != 0 || (boxMask[box] & bit) != 0) return false;
                // In above, we are checking is the bit was already turn on
                rowMask[row] |= bit;// turn on the bits if it was turn-off
                columnMask[column] |= bit;
                boxMask[box] |= bit;
            }
        }

        return true;
    }
}
