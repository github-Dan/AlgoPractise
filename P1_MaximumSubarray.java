//Maximum Subarray
/*
题意：
    找一个数组里的，最大subarray和是多少。返回这个max subarray sum。
分析：
M1.暴力解（TLE）：
        Two Pointer + O(n)check sum: 得到所有的subarray，O(n)check每个subarray的和。记录下最大的sum。
Time：O(n^3)
Space:O(1)
*/
class Solution {
    public int maxSubArray(int[] nums) {
        int maxSum = Integer.MIN_VALUE;
        for(int i = 0; i < nums.length; i++) {
            for(int j = 0; j <= i; j++) {
                int sum = checkSum(nums,j,i);
                maxSum = Math.max(sum,maxSum);
            }
        } 
        return maxSum;
    }
    private int checkSum(int[] nums, int left, int right){
        int sum = 0;
        for(int i = left; i <= right; i++){
            sum += nums[i];
        }
        return sum;
    }
}

/*
M2.优化暴力解：
        Two Pointer + O(1)check sum: 得到所有的subarray，O(1)check每个subarray的和。记录下最大的sum。
    需要预处理得到prefixSum array。
Time：O(n^2)
Space:O(n)
*/
class Solution {
    public int maxSubArray(int[] nums) {
        int maxSum = Integer.MIN_VALUE;
        int[] preSum = getPreSum(nums);
        for(int i = 0; i < nums.length; i++) {
            for(int j = 0; j <= i; j++) {
                int sum = 0;
                if(j == 0) {
                    sum = preSum[i];
                } else {
                    sum = preSum[i] - preSum[j - 1];
                }
                maxSum = Math.max(sum,maxSum);
            }
        } 
        return maxSum;
    }
    private int[] getPreSum(int[] nums){
        int[] preSum = new int[nums.length];
        preSum[0] = nums[0];
        for(int i = 1; i < nums.length; i++) {
            preSum[i] = preSum[i - 1] + nums[i];
        }
        return preSum;
    }
}

/*
M3.在prefixSum arrays上，用sliding window 优化prefixSum里的2 different问题。
        求最大的subarray sum => 求最大的range sum: prefixSum[end] - prefixSum[start] (0 < start < end)
        =>对每个end，只需要知道,end左边的 minLeft = min(prefixSum[start])是多少，并不关心，是哪个left index产生了这个minLeft，所以可以让start跟随end往右移动，就直接跟随者end对数组的遍历，同时记下了minLeft。对于从左往右的end，leftMin可能的取值，会形成一个单调递减的数列。
        prefixSum[start]会是一个单调递减的数。
        Time:O(n)
        Space:O(n)
*/
class Solution {
    public int maxSubArray(int[] nums) {
        if(nums == null || nums.length == 0) {
            return Integer.MIN_VALUE;
        } 
        //计算prefixSum
        int[] prefixSum = new int[nums.length];
        prefixSum[0] = nums[0];
        for(int i = 1; i < nums.length; i++) {
            prefixSum[i] = prefixSum[i - 1] + nums[i];
        }
        int max = Integer.MIN_VALUE;//必须赋值成Integer.MIN_VALUE，不能是0

        int start = 0;
        int end = 0;
        int leftMin = 0;
        while(end < nums.length) {
            while(start < end) {
                leftMin = Math.min(prefixSum[start], leftMin);
                start++;
            }
            int curMax = prefixSum[end] - leftMin;//每个end更新curMax
            max = Math.max(max,curMax) ; 
            end++;
        }
        return max;
    }
}
/*
 M4.时空优化prefixSum + sliding window：
        只care 遍历到right位置的和preFast，和遍历到left位置的和preSlow，所以可以不必记下来所有的prefixSum，而是可以边遍历，边记录：
        到当前right位置为止的总和：preFast， 
        到left位置为止的总和：preSlow。
        时间优化了O(n)
        空间优化了O(n)
Time：O(n)
Space:O(1)
*/
class Solution {
    public int maxSubArray(int[] nums) {
        int maxSum = Integer.MIN_VALUE;//必须赋值成Integer.MIN_VALUE，不能是0
        int preFast = 0;
        int preSlow = 0;
        int leftMin = 0;
        int left = 0;
        int right = 0;
        int sum = 0;
        while(right < nums.length) {
            preFast += nums[right];
            while(left < right){
                preSlow += nums[left];
                leftMin = Math.min(preSlow,leftMin);
                left++;
            }
            int curMax = preFast - leftMin;
            maxSum = Math.max(curMax, maxSum);
            right++;
        } 
        return maxSum;
    }
}
/*
M5.dp
        1.defination:
            dp[i] is the maximun sum ending at i, must include i
        2.base case:
            i = 0: dp[i] = nums[i]
        3.induction rule:
            dp[i] = nums[i] + dp[i - 1] (if dp[i-1] > 0)
            else
            dp[i] = nums[i];
        4.fill in order:
            from left to right
        5.return: global max of dp[i]
        6.Time:O(n)
        Space:O(n)
*/
class Solution {
    public int maxSubArray(int[] nums) {
        int[] dp = new int[nums.length];
        int maxSum = nums[0];//不能赋值成Integer.MIN_VALUE
        dp[0] = nums[0];
        for(int i = 1; i < nums.length; i++){
            if(dp[i - 1] > 0) {
                dp[i] = dp[i - 1] + nums[i];
            } else {
                dp[i] = nums[i];
            }
            maxSum = Math.max(maxSum, dp[i]);
        }
        return maxSum;
    }
}

/*
M6.dp空间优化
Time: O(n)
Space: O(1)
*/
class Solution {
    public int maxSubArray(int[] nums) {
        int maxSum = nums[0];//不能赋值成Integer.MIN_VALUE
        int cur = nums[0];//cur就是dp[i]
        for(int i = 1; i < nums.length; i++){
            if(cur > 0) {
                cur += nums[i];
            } else {
                cur = nums[i];
            }
            maxSum = Math.max(maxSum, cur);
        }
        return maxSum;
    }
}


