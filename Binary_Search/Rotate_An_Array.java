package Binary_Search;

public class Rotate_An_Array
{
/**
     In-Place Rotation — The Reversal Trick

## Right Rotation by k

**Three steps, in this order:**
        1. **Reverse the ENTIRE array.**
        2. **Reverse the FIRST `k` elements** (of the now-reversed array).
        3. **Reverse the REMAINING elements** (from index `k` to the end).

        ## Left Rotation by k

**Three steps, in the OPPOSITE order:**
        1. **Reverse the FIRST `k` elements.**
        2. **Reverse the REMAINING elements** (from index `k` to the end).
        3. **Reverse the ENTIRE array.**

        ## The core intuition, one more time

**Reversing the whole array flips the two "chunks" (the part that moves, and the part that stays) so they swap positions — but their internal order gets scrambled too. Reversing each chunk individually afterward un-scrambles their internal order, while keeping their swapped positions intact.**

        ## The pattern to remember

**Right rotation: whole-first, then the two pieces.**
        **Left rotation: the two pieces first, then whole-last.**

    They're mirror images of each other in terms of step ordering.
 **/
}
