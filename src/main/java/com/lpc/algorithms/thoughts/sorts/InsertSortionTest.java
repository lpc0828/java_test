package com.lpc.algorithms.thoughts.sorts;

/**
 * 后续的数据，往一个容器中添加数据，经常采用插入排序；
 * 方法： 遍历已经存在的有序数据，找到新数据的位置，插入即可
 * 具体步骤如下：
 * (1). 将数组分成已排序段和未排序段，初始化时，已排序段只有一个数据；
 * (2). 到未排序段取出元素插入到已排序段，并保证插入后仍然有序；
 * (3). 重复执行上述操作，直到未排序段元素全部加完；
 * 实现的数据结构： 数组，链表；
 * 时间复杂度：
 *    最优： O(n)
 *    最坏： O(n^2)
 *
 * 改进： 希尔和归并排序
 * 稳定性： 插入和归并排序稳定，希尔排序不稳定
 */
public class InsertSortionTest {

    /**
     * 从小到大排序，使用插入排序
     * @param nums
     */
    public static void sort(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return;
        }
        int n = nums.length;
        for (int i=1; i<n; i++) { // 第0个为参照，从第一个开始
            int data = nums[i];
            // 拿到数据之后，向前比较，有两个好处，一个是边比较边移动，另外一个是发现不满足条件的直接break；降低时间复杂度
            int j=i-1;
            for (; j>=0; j--) {
                if (nums[j] > data) {
                    nums[j+1] = nums[j];
                } else {
                    break;
                }
            }
            nums[j+1] = data;
            System.out.print("第" + i + "次排序之后的结果为: ");
            for (int k=0; k<n; k++) {
                System.out.print(nums[k] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[] a= new int[] {8,7,3,6,9,5,1,4,2};
        sort(a);
    }
}
