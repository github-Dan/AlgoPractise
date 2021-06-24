
/*
题意：
    找到nums里，最长的连续1有多长。
M1.暴力解：
     找到所有subarray，然后用O(n)的时间check是否是只包含连续1的subarray，并记录下来最长的。
    Time：O(n^3)
    Space：O(1)
    码：略
M2.non-fixsize sliding window
    window定义：[left,right]记录了每一段，是连续1的subarray
    left：每一段连续1的subarray，可能的开始位置
    right：每一段连续1的subarray，结束的位置。right也负责遍历
    case1:如果nums[right] == 1,window合法，更新maxLen。
    case2:如果nums[right] == 0,window不合法，left = right + 1 （left条跳到下一个可能是新一段连续1 的index作为新一段的起点）
    Time：O(n)
    Space:O(1)
*/
class Solution {
    public int findMaxConsecutiveOnes(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int right = 0;
        int left = 0;
        int maxLen = 0;
        while (right < nums.length) {
            if (nums[right] == 1) {
                int curLen = right - left + 1;
                maxLen = Math.max(curLen, maxLen);
            } else {
                left = right + 1;
            }
            right++;
        }
        return maxLen;
    }
}

/*
 * M3 DP: 1：defination dp[i]: the longest length of consecutive ones ending at
 * i, including i 2: base case: dp[0] = nums[0]; 3: induction rule: dp[i] = dp[i
 * - 1] + 1 (if nums[i] == 1) else dp[i] = 0 4: fill in order: from left to
 * right 5: return: global max of dp[i] 6: Time:O(n) Space: O(n) 7: Optimize
 * space to O(1)
 */
class Solution {
    public int findMaxConsecutiveOnes(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int maxLen = nums[0];// 不能初始化成0
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == 1) {
                dp[i] = dp[i - 1] + 1;
                maxLen = Math.max(maxLen, dp[i]);
            } else {
                dp[i] = 0;
            }
        }
        return maxLen;
    }
}

/*
 * M4 DP空间优化：
 */
class Solution {
    public int findMaxConsecutiveOnes(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int cur = nums[0];
        int maxLen = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == 1) {
                cur += 1;
                maxLen = Math.max(maxLen, cur);
            } else {
                cur = 0;
            }
        }
        return maxLen;
    }
}
