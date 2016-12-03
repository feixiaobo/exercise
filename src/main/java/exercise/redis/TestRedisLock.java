package exercise.redis;

import org.redisson.RedissonClient;
import org.springframework.stereotype.Component;

/**
 * Created by feixiaobo on 2016/10/25.
 */
@Component
public class TestRedisLock {
    private RedissonClient redissonClient;

    class Bank {
        private  int account = 100;

        public int getAccount() {
            return account;
        }

        /**
         * 用同步方法实现
         * @param money
         */
        @RedisLock(lockKey = "redisLock")
        public void save(@RedisLockKey String key ,int money) {
            System.out.println("lock key:"+key);
            //this.account += money;
            try {
                Thread.sleep(1000);
            }catch (Exception e){
                //
            }
            this.account += money;
            System.out.println("current account:"+account);
        }

    }


    class NewThread implements Runnable {
        private Bank bank;

        public NewThread(Bank bank) {
            this.bank = bank;
        }

        @Override public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    bank.save("lock", 10);
                    System.out.println(i + "账户余额为：" + bank.getAccount());
                }
            }catch (Exception e){
                //
            }
        }
    }

    /**
     * 建立线程，调用内部类
     */
    public void useThread() {
        Bank bank = new Bank();
        NewThread new_thread = new NewThread(bank);
        System.out.println("线程1");
        Thread thread1 = new Thread(new_thread);
        thread1.start();
        System.out.println("线程2");
        Thread thread2 = new Thread(new_thread);
        thread2.start();
        System.out.println("线程3");
        Thread thread3 = new Thread(new_thread);
        thread3.start();
        System.out.println("线程4");
        Thread thread4 = new Thread(new_thread);
        thread4.start();
    }

    public static void main(String[] args) {
        TestRedisLock st = new TestRedisLock();
        st.useThread();
    }
}
