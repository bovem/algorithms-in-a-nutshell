import java.util.Arrays;
//import java.util.Collections;

class PriorityQueue{
    int[] values;
    int[] priorities;
    int size;

    PriorityQueue(int n){
        size = n;
        values = new int[size];
        priorities = new int[size];
        for(int i=0;i<n;i++){
            values[i] = (-1);
            priorities[i] = (-1);
        }
    }

    void enqueue(int x, int p){
        // Adds element to end
        for(int i=0;i<size;i++){
            if(values[i]==(-1)){
                values[i] = x;
                priorities[i] = p;
                break;
            }
        }
    }

    void dequeue(){
        // Removes element from start
        if(isEmpty()!=1){

            for(int i=0;i<size-1;i++){
                values[i] = values[i+1];
            }

            values[size-1] = (-1);
        }
    }

    int isEmpty(){
        // Checks if queue is empty
        if(values[0]==(-1)){
            return 1;
        }
        return (-1);
    }

    int head(){
        // Returns first element of queue
        if(isEmpty()!=1){
            return values[0];
        }
        return -1;
    }

    int minPriority(){
        if (priorities.length == 0)
            return -1;

        int index = 0;
        int min = priorities[index];

        for (int i = 1; i < priorities.length; i++){
            if ((priorities[i]!=(-1))&&(priorities[i]<min)){
                min = priorities[i];
                index = i;
            }
        }
        return index;
    }

    void changePriority(int x, int p){
        priorities[x] = p;
    }

}

class Graph{
    // Undirected Graph
    int[][] adjMatrix;
    int size;
    int[][] weightMatrix;

    Graph(int n){
        // Initializes graph of n vertices
        size = n;

        // Initializing Adjacent Matrix and Weights Matrix (with all 0s)
        // 1 if vertices are connected
        // 0 if vertices are disconnected
        adjMatrix = new int[size][size];
        weightMatrix = new int[size][size];
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                adjMatrix[i][j] = 0;
                weightMatrix[i][j] = 0;
            }
        }
    }

    public void showAdjMatrix(){
        // Displays Adjacent Matrix
        for(int[] r: adjMatrix){
            System.out.println(Arrays.toString(r));
        }
    }

    public void showWeightsMatrix(){
        // Displays Weights Matrix
        for(int[] r: weightMatrix){
            System.out.println(Arrays.toString(r));
        }
    }

    public void connect(int a, int b, int weight){
        // Connects vertices bidirectionally
        adjMatrix[a][b] = 1;
        adjMatrix[b][a] = 1;
        weightMatrix[a][b] = weight;
        weightMatrix[b][a] = weight;
    }

    public int[] neighbors(int a){
        // Returns list of neighbors
        // (-1) is subtitute for null values
        int[] neighbors = new int[size];
        
        for(int i=0;i<size;i++){
            neighbors[i] = (-1);
        }

        int iter =0, i=0;
        while(i<size){
            if(adjMatrix[a][i]==1){
                neighbors[iter] = i;
                iter++;
            }
            i++;
        }
        return neighbors;
    }
}

class DijkstraAlgorithm{
    // Predecessor array
    // Contains predecessor vertex of the element
    static int[] pred; 

    // Distance array
    // Contains distance of individual vertex from source
    static int[] dist;
    
    // Number of vertices
    int n;

    DijkstraAlgorithm(int n){
        // Initialized predecessor and distance array
        pred = new int[n];
        dist = new int[n];
        for(int i=0;i<n;i++){
            pred[i] = (-1);
            dist[i] = (-1);
        }
    }

    public static int[] shortestPath(Graph g, PriorityQueue pq, int source){
    
        dist[source] = 0;
        
        for(int i=0;i<g.size;i++){
            pq.enqueue(i, dist[i]);
        }

        while(pq.isEmpty()!=(1)){
            int u = pq.minPriority();
            System.out.println(u);
            for(int n: g.neighbors(u)){
                if(n!=(-1)){
                    int w = g.weightMatrix[u][n];
                    int newlength = dist[u] + w;

                    if(newlength<dist[n]){
                        pq.changePriority(n, newlength);
                        dist[n] = newlength;
                        pred[n] = u;
                    }
                }
            }
        }
        return dist;
    }

    public static void main(String[] args) {

        Graph g = new Graph(6);
        PriorityQueue pq = new PriorityQueue(6);
        DijkstraAlgorithm djks = new DijkstraAlgorithm(6);
        // Connecting vertices
        g.connect(0, 2, 8);
        g.connect(0, 1, 6);
        g.connect(0, 3, 18);
        g.connect(1, 4, 11);
        g.connect(2, 3, 9);
        g.connect(2, 5, 7);
        g.connect(3, 5, 4);
        g.connect(4, 5, 3);
    
        int[] dist = djks.shortestPath(g, pq, 0);
        System.out.println("Shortest distance from source to other vertices: "+Arrays.toString(dist));
        
    }
}