package work.sihai.common.filter;

public class StartTimeHolder {
        private static final ThreadLocal<Long> startTimeThreadLocal = new ThreadLocal<Long>();

        public static void set(Long startTime){
            startTimeThreadLocal.set(startTime);
        }

        public static Long get(){
            return startTimeThreadLocal.get();
        }

        public static void remove(){
            startTimeThreadLocal.remove();
        }
    }