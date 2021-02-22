package com.lpc.jvm.gcroots;

import java.util.*;

/**
 * 实现一个GCRoots算法
 * https://blog.csdn.net/dingshuo168/article/details/100148457
 */
public class GCRootsTest {

    private static TraceTable traceTable = new TraceTable();
    private static StackFrame stackFrame = new StackFrame();
    private static Generation newGeneration = new Generation();
    private static Generation oldGeneration = new Generation();

    public static void main(String[] args) {
        /**
         * 在年轻代放入10000个对象 ，存活100个
         */
        final int totalNewObjAmt = 10_000;
        for (int i = 0; i < totalNewObjAmt; i++) {
            StorageStructure objSt = new StorageStructure("#A000-"+i, "A000"+i);
            newGeneration.add(objSt);
        }
        /**
         * 在老年代代放入10000个对象 ，存活9900个
         */
        final int totalOldObjAmt = 10_000;
        for (int i = 0; i < totalOldObjAmt; i++) {
            StorageStructure objSt = new StorageStructure("#B000-"+i, "A000"+i);
            oldGeneration.add(objSt);
        }


        createRealation(newGeneration, 10);
        createRealation(oldGeneration, 99);
        System.out.print("Minor gc cost:");
        newGeneration.gc();
        System.out.println("==================================================");
        System.out.print("Major gc cost:");
        oldGeneration.gc();


    }

    /**
     * 随机建立对象的引用关系
     */
    private static void createRealation(Generation generation, int childrenPerObj) {
        for (int i = 0; i < 10; i++) {
            StorageStructure rootObj = getRandomObjFromGen(generation);
            stackFrame.addAddr(rootObj.addr);
            for (int j = 0; j < childrenPerObj; j++) {
                StorageStructure childObj = getRandomObjFromGen(generation);
                rootObj.addRefObj(childObj);
                addTraceTable(rootObj, childObj);
            }
        }
    }

    private static void addTraceTable(StorageStructure objStRoot, StorageStructure objStChild) {
        List<String> headList = traceTable.headMap.get(objStRoot.addr);
        if (headList == null) {
            headList = new ArrayList<>();
            traceTable.headMap.put(objStRoot.addr, headList);
        }
        headList.add(objStChild.addr);
    }

    private static StorageStructure getRandomObjFromGen(Generation generation) {
        Random random = new Random();
        List<StorageStructure> objStList = generation.getObjStList();
        int randomIndex = random.nextInt(objStList.size());
        StorageStructure objSt = objStList.get(randomIndex);
        return objSt;
    }

    static class Generation {
        private List<StorageStructure> objStList = new ArrayList<>();
        private Map<String, StorageStructure> map = new HashMap<>();

        public void gc() {
            long start = System.currentTimeMillis();
            //复制对象，这里用sleep(1)表示复制的时间消耗
            int i = 1;
            for (String rootAddr : stackFrame.getVarAddrList()) {
                List<String> addrList = traceTable.headMap.get(rootAddr);
                for (String childAddr : addrList) {
                    if (map.get(childAddr) != null) {
                        try {
//                            Thread.sleep(0,1);
                            Thread.sleep(10);
//
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
//                        System.out.println((i++) + "-" + map.get(childAddr));
                    }
                }
            }
            System.out.println(System.currentTimeMillis()-start);
        }

        public void add(StorageStructure objSt) {
            this.objStList.add(objSt);
            this.map.put(objSt.addr, objSt);
        }

        public List<StorageStructure> getObjStList() {
            return objStList;
        }
    }


    /**
     * 追踪表
     * headMap中存放的key是 stackFrame中的对象地址，value里是存放的是key指定的对象所关联对象的地址列表
     */
    static class TraceTable {
        private Map<String, List<String>> headMap = new HashMap();
    }

    static class StackFrame {
        private List<String> varAddrList = new ArrayList<>();//存放对象引用地址

        /**
         * 在栈帧中放入对象引用地址
         *
         * @param addr
         */
        public void addAddr(String addr) {
            this.varAddrList.add(addr);
        }

        public List<String> getVarAddrList() {
            return varAddrList;
        }
    }

    static class StorageStructure {
        private String addr;
        private Object obj;
        private List<StorageStructure> refObjList = new ArrayList<>(); //引用对象

        public StorageStructure(String addr, Object obj) {
            this.addr = addr;
            this.obj = obj;
        }

        public void addRefObj(StorageStructure objSt) {
            refObjList.add(objSt);
        }

        @Override
        public String toString() {
            return "ObjectStorageStructure{" +
                    "addr='" + addr + '\'' +
                    ", obj=" + obj +
                    '}';
        }
    }
}
