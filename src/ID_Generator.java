
    import java.util.ArrayDeque;

    final public class ID_Generator {
        private static int count = 0;
        private static ArrayDeque<Integer> usedIDs = new ArrayDeque<>();

        public static synchronized int nextID(){
            int id;
            if (usedIDs.isEmpty()){
                id = count;
                count ++;
            } else {
                id = usedIDs.pop();
                if (!(id < count)){
                    throw new IllegalStateException();
                }
            }
            return id;
        }

        public static synchronized void addKilled(int killed){
            usedIDs.add(killed);
        }

    }
