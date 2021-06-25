/*
M1. find all subarrays. For each subarray, check if it's sum is k. Count the number of such subarray.
    TC:O(n^2 * n)
    SC:O(1)
M2. find all subarrays. use prefix sum array to check if each subarray sum is k.
    TC:O(n^2)
    SC:(n): prefix Sum array，空间换时间
M3. prefixSum + map
    for each right index in prefixSum array, check how many left value before right index satisfy prefixSum[right] - prefixSum[left] == k.
    accumulate each sub result at each right index to get the total count.
    check the number of qualified left values is a lookup operation, will use a map to record all unique left values and its frequencies to implement an O(1) look up operation
   Time: O(n)
   Space: O(n)
*/
class Solution {
    public int subarraySum(int[] nums, int k) {
        if(nums == null || nums.length == 0) {
            return 0;
        }    
        int[] prefixSum = getPreSum(nums);
        Map<Integer,Integer> map = new HashMap<>();
        int right = 0;
        int count = 0;
        map.put(0,1);
        while(right < nums.length ) {
            int key = prefixSum[right] - k;
            Integer freq = map.get(key);
            if(freq != null) {
                int curCount = freq;
                count += curCount;
            }
            map.put(prefixSum[right], map.getOrDefault(prefixSum[right], 0) + 1);
            right++;
        }
        return count;
    }
    private int[] getPreSum(int[] nums){
        int[] prefixSum = new int[nums.length];
        prefixSum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            prefixSum[i] = prefixSum[i - 1] + nums[i];
        }
        return prefixSum;
    }
}
/*
M4. prefixSum + map+时空优化
   Time: O(n)
   Space: O(n)
*/
class Solution {
    public int subarraySum(int[] nums, int k) {
        if(nums == null || nums.length == 0) {
            return 0;
        }    
        Map<Integer,Integer> map = new HashMap<>();
        int right = 0;
        int count = 0;
        map.put(0,1);
        int sum = 0;
        while(right < nums.length ) {
            sum += nums[right];
            int key = sum - k;
            Integer freq = map.get(key);
            if(freq != null) {
                int curCount = freq;
                count += curCount;
            }
            map.put(sum, map.getOrDefault(sum, 0) + 1);
            right++;
        }
        return count;
    }
}

