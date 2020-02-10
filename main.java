

public class main {
    final static private String temp="102345678";
    final static private String EASY = "123045678";
    final static private String MEDIUM = "281043765";
    final static private String HARD = "567408321";
    final static private String GOAL_STATE = "012345678";
   // final static private String GOAL_STATE = "123804765";



    public static void main(String[] args) {
        String Initial_state = EASY;
        long startTime = System.currentTimeMillis();

        SearchTree search = new SearchTree(new Node(Initial_state), GOAL_STATE);
       
        search.AStar();

        long finishTime = System.currentTimeMillis();
        long totalTime = finishTime - startTime;
        System.out.println("Time  :" + totalTime);


    }
}
