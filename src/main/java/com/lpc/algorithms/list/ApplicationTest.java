package com.lpc.algorithms.list;

import java.util.LinkedList;
import java.util.List;

public class ApplicationTest {

    /**
     * 约瑟夫（环）问题：已知N个人，从编号为1的开始报数，数到M的那个人出列；他的下一个人又从一开始数，数到M那个人又出列；依此规律，计算最后出列的那个人
     * @param
     * 1->2->3->4->5->6  --5
     * 6->1->2->3->4     --4
     * 6->1->2->3->6     --6
     * 1->2->3->1->2     --2
     * 3->1->3->1->3     --3
     * 1                 --1
     */

    public static void remainingOne(int n, int m) {
        List<Integer> list = new LinkedList<>();
        for (int i=1; i<=n; i++) {
            list.add(i);
        }
        int removedIndex = 0;
        while (list.size() != 1) {
            removedIndex = (removedIndex+m-1)%list.size();
            System.out.println(list.get(removedIndex));
            list.remove(removedIndex);
        }
        System.out.println(list.get(0));
    }

    /**
     * 单项循环链表(尾指向头)数环，剩一个
     * @param n
     * @param m
     */
    public static void remainTwo(int n, int m) {

    }

    public static int maxArea(int[] height) {
        int l=0;
        int r=(height.length-1);
        int max = 0;
        while (l < r) {
            int sq = Math.min(height[l], height[r]) * (r-l);
            if (max < sq) {
                max = sq;
            }
            if (height[l] <= height[r]) {
                l++;
            } else {
                r--;
            }
        }
        return max;
    }


    public static void main(String[] args) {
        System.out.println(maxArea(new int[] {1,8,6,2,5,4,8,3,7}));
        /*ApplicationTest.remainingOne(6, 5);
        System.out.println("=====================================");
        ApplicationTest.remainTwo(6,5);*/
    }
}
