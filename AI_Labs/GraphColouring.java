public class mcolouring {
   static int V = 4;
   static int graph[][] = {
      {0, 1, 1, 0},
      {1, 0, 1, 1},
      {1, 1, 0, 1},
      {0, 1, 1, 0},
   };
   static boolean isValid(int v,int color[], int c) { //check whether putting a color valid for v
      for (int i = 0; i < V; i++)
         if (graph[v][i] != 0 && c == color[i])
            return false;
      return true;
   }
   static boolean mColoring(int colors, int color[], int vertex) {
      if (vertex == V) //when all vertices are considered
         return true;
      for (int col = 1; col <= colors; col++) {
         if (isValid(vertex,color, col)) { //check whether color col is valid or not
            color[vertex] = col;
            if (mColoring (colors, color, vertex+1) == true) //go for additional vertices
               return true;
            color[vertex] = 0;
         }
      }
      return false; //when no colors can be assigned
   }
   public static void main(String args[]) {
      int colors = 3; // Number of colors
      int color[] = new int[V]; //make color matrix for each vertex
      for (int i = 0; i < V; i++)
         color[i] = 0; //initially set to 0
      if (mColoring(colors, color, 0) == false) { //for vertex 0 check graph coloring
         System.out.println("Solution does not exist.");
      }
      System.out.println("Assigned Colors are: \n");
      for (int i = 0; i < V; i++)
         System.out.print(color[i] + " ");
   }
}
