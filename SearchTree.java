import java.util.*;


public class SearchTree {
    private Node root;
    private String goalSate;

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public String getGoalSate() {
        return goalSate;
    }

    public void setGoalSate(String goalSate) {
        this.goalSate = goalSate;
    }

    public SearchTree(Node root, String goalSate) {
        this.root = root;
        this.goalSate = goalSate;
    }


    private int heuristicOne(String currentState, String goalSate) {
        int difference = 0;
        for (int i = 0; i < currentState.length(); i += 1)
            if (currentState.charAt(i) != goalSate.charAt(i))
                difference += 1;
        return difference;
    }

  
    private int Manhathan_Distance(String currentState, String goalSate) {
        int difference = 0;
        for (int i = 0; i < currentState.length(); i += 1)
            for (int j = 0; j < goalSate.length(); j += 1)
                if (currentState.charAt(i) == goalSate.charAt(j))
                    difference = difference + ((Math.abs(i % 3 - j % 3)) + Math.abs(i / 3 + j / 3));
        return difference;
    }
    public void BFS() {
        // stateSet is a set that contains node that are already visited
        Set<String> stateSets = new HashSet<String>();
        int totalCost = 0;
        int time = 0;
        Node node = new Node(root.getState());
        Queue<Node> queue = new LinkedList<Node>();
        Node currentNode = node;
        while (!currentNode.getState().equals(goalSate)) {
            stateSets.add(currentNode.getState());
            List<String> nodeSuccessors = NodeUtil.getSuccessors(currentNode.getState());
            for (String n : nodeSuccessors) {
                if (stateSets.contains(n))
                    continue;
                stateSets.add(n);
                Node child = new Node(n);
                currentNode.addChild(child);
                child.setParent(currentNode);
                queue.add(child);

            }
            currentNode = queue.poll();
            time += 1;
        }

        NodeUtil.printSolution(currentNode, stateSets, root, time);

    }
//**********************************************************************************************

    public void DFS() {
        // stateSet is a set that contains node that are already visited
        Set<String> stateSets = new HashSet<String>();
        int totalCost = 0;
        int time = 0;
        Node node = new Node(root.getState());
        //the queue that store nodes that we should expand
        MyQueue<Node> mainQueue = new MyQueue<>();
        //the queue that contains the successors of the expanded node
        MyQueue<Node> temp = new MyQueue<>();
        Node currentNode = node;
        while (!currentNode.getState().equals(goalSate)) {
            stateSets.add(currentNode.getState());
            List<String> nodeSuccessors = NodeUtil.getSuccessors(currentNode.getState());
            for (String n : nodeSuccessors) {
                if (stateSets.contains(n))
                    continue;
                stateSets.add(n);
                Node child = new Node(n);
                currentNode.addChild(child);
                child.setParent(currentNode);
                temp.enqueue(child);

            }
            //we add the queue that contains the successors of the visted node to the beginning of the main queue
            mainQueue.addQueue(temp);
            //successors queue should be cleared in order to store the next expaneded's successors
            temp.clear();
            currentNode = mainQueue.dequeue();
            time += 1;
            nodeSuccessors.clear();
        }
        NodeUtil.printSolution(currentNode, stateSets, root, time);

    }
    
    public void Greedy_BestFirst() {
        // stateSet is a set that contains node that are already visited
        Set<String> stateSets = new HashSet<String>();
        int totalCost = 0;
        int time = 0;
        Node node = new Node(root.getState());
        node.setCost(0);

        // the comparator compare the cost values and make the priority queue to be sorted based on cost values
        NodePriorityComparator nodePriorityComparator = new NodePriorityComparator();

        // a queue that contains nodes and their cost values sorted. 10 is the initial size
        PriorityQueue<Node> nodePriorityQueue = new PriorityQueue<Node>(10, nodePriorityComparator);
        Node currentNode = node;
        while (!currentNode.getState().equals(goalSate)) {
            stateSets.add(currentNode.getState());
            List<String> nodeSuccessors = NodeUtil.getSuccessors(currentNode.getState());
            for (String n : nodeSuccessors) {
                if (stateSets.contains(n))
                    continue;
                stateSets.add(n);
                Node child = new Node(n);
                currentNode.addChild(child);
                child.setParent(currentNode);

                child.setTotalCost(0, heuristicOne(child.getState(), goalSate));
                nodePriorityQueue.add(child);

            }
            currentNode = nodePriorityQueue.poll();
            time += 1;
        }
        // Here we try to navigate from the goal node to its parent( and its parent's parent and so on) to find the path
        NodeUtil.printSolution(currentNode, stateSets, root, time);

    }


   
    public void AStar() {
        // stateSet is a set that contains node that are already visited
        Set<String> stateSets = new HashSet<String>();
        int totalCost = 0;
        int time = 0;
        Node node = new Node(root.getState());
        node.setTotalCost(0);

        // the comparator compare the cost values and make the priority queue to be sorted based on cost values
        NodePriorityComparator nodePriorityComparator = new NodePriorityComparator();

        // a queue that contains nodes and their cost values sorted. 10 is the initial size
        PriorityQueue<Node> nodePriorityQueue = new PriorityQueue<Node>(10, nodePriorityComparator);
        Node currentNode = node;
        while (!currentNode.getState().equals(goalSate)) {
            stateSets.add(currentNode.getState());
            List<String> nodeSuccessors = NodeUtil.getSuccessors(currentNode.getState());
            for (String n : nodeSuccessors) {
                if (stateSets.contains(n))
                    continue;
                stateSets.add(n);
                Node child = new Node(n);
                currentNode.addChild(child);
                child.setParent(currentNode);

                    child.setTotalCost(currentNode.getTotalCost() + Character.getNumericValue(child.getState().charAt(child.getParent().getState().indexOf('0'))), Manhathan_Distance(child.getState(), goalSate));
              
            }
            currentNode = nodePriorityQueue.poll();
            time += 1;
        }
        NodeUtil.printSolution(currentNode, stateSets, root, time);
    }


  
    public void IDS(int depthLimit) {
        Node currentNode = new Node(root.getState());
        boolean solutionFound = false;
       
        Set<String> stateSets = new HashSet<String>();
        Set<String> totalVisitedStates = new HashSet<>();
        int time = 0;
        for (int maxDepth = 1; maxDepth < depthLimit; maxDepth++) {
            //we should clear the visited list in each iteration
            stateSets.clear();
            //the queue that store nodes that we should expand
            MyQueue<Node> mainQueue = new MyQueue<>();
            //the queue that stores the successors of the expanded node
            MyQueue<Node> successorsQueue = new MyQueue<>();
            Node node = new Node(root.getState());
            mainQueue.enqueue(node);
            currentNode = node;
            List<String> nodeSuccessors = null;
            stateSets.add(currentNode.getState());
            while (!mainQueue.isEmpty()) {
                currentNode = mainQueue.dequeue();
                time += 1;
                if (currentNode.getState().equals(goalSate)) {
                    solutionFound = true;
                    break;
                }
                if (currentNode.getDepth() < maxDepth) {
                    nodeSuccessors = NodeUtil.getSuccessors(currentNode.getState());
                    for (String n : nodeSuccessors) {
                        if (stateSets.contains(n))
                            continue;

                        stateSets.add(n);
                        Node child = new Node(n);
                        currentNode.addChild(child);
                        child.setParent(currentNode);
                        child.setVisited(true);
                        child.setDepth(currentNode.getDepth() + 1);
                        successorsQueue.enqueue(child);

                    }
                    //we add the queue that contains the successors of the visted node to the beginning of the main queue
                    mainQueue.addQueue(successorsQueue);
                    //successors queue should be cleared in order to store the next expaneded's successors
                    successorsQueue.clear();
                }
            }

            if (solutionFound)
                break;
            totalVisitedStates.addAll(stateSets);
        }
        if (!solutionFound)
            System.out.println("No solution Found! Increase the depth value");
        else {
            NodeUtil.printSolution(currentNode, totalVisitedStates, root, time);


        }


    }
   


    

}