package com.lpc.cache.localcache;

import net.sf.cglib.core.Local;

import java.util.concurrent.*;
import java.util.concurrent.atomic.LongAdder;

public class LocalCache<K, V> {

    /**
     * 启动开启后延时5秒执行时效策略
     */
    private static final int INITIAL_DELAY_TIME = 5;

    /**
     * 执行时效过期策略的时间间隔
     */
    private static final int PERIOD_TIME = 5;

    /**
     * 本地缓存数据
     */
    private ConcurrentHashMap<K, Cache<V>> store = new ConcurrentHashMap<>();

    /**
     * key 过期监听
     */
    private RemoveListener<K, V> onRemovedListener = null;

    /**
     * 执行时效策略的定时任务线程
     */
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    LocalCache(RemoveListener<K, V> onRemovedListener) {
        Task task = new Task(this);
        executor.scheduleAtFixedRate(new Task(this), INITIAL_DELAY_TIME, PERIOD_TIME, TimeUnit.SECONDS);
        this.onRemovedListener = onRemovedListener;
    }

    public void put(K k, V v, TimeUnit timeUnit, long expire) {
        if (expire > 0) {
            store.put(k, new Cache<>(v, timeUnit, expire));
        } else {
            store.put(k, new Cache<>(v));
        }
    }

    public V get(K k) {
        return store.get(k) == null ? null : store.get(k).value;
    }

    private int removeAll() {
        LongAdder count = new LongAdder();
        for (K key : store.keySet()) {
            Cache<V> cache = store.get(key);
            if (cache != null && cache.expire > 0 && cache.expire < System.nanoTime()) {
                store.remove(key);
                onRemovedListener.onRemoved(key, cache.value);
                count.increment();
            }
        }
        return count.intValue();
    }

    public static void main(String[] args) {
        LocalCache<Long, String> cache = new LocalCache<>(new RemoveListener<Long, String>() {
            @Override
            public void onRemoved(Long aLong, String s) {
                System.out.println("过期监听器 key:" + aLong + ", value:" + s);
            }
        });
        for (Long i=0L; i<100; i++) {
            cache.put(i, "value" + i, TimeUnit.SECONDS, 10);
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (Long i=0L; i<100; i++) {
            if (cache.get(i) != null) {
                System.out.println(" 尚未过期的缓存, key:" + i + ", value:" + cache.get(i));
            }
        }
    }

    private static final class Task implements Runnable {

        private LocalCache cache = null;

        Task(LocalCache cache) {
            this.cache = cache;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            System.out.println("定时删除 开始");
            int count = cache.removeAll();
            System.out.println("定时删除 结束, 耗时:" + (System.currentTimeMillis()-start) + ", 删除数量:" + count);
        }
    }

    /**
     * 本地缓存
     */
    class Cache<V> {
        private V value;
        private long expire = 0;

        Cache(V value, TimeUnit timeUnit, long expire) {
            this.value = value;
            this.expire = System.nanoTime()+timeUnit.toNanos(expire);
        }

        Cache(V value) {
            this.value = value;
        }
    }

    /**
     * 过期监听器
     * @param <K>
     * @param <V>
     */
    interface RemoveListener<K, V> {
        void onRemoved(K k, V v);
    }
}
