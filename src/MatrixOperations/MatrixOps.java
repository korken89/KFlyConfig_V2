package MatrixOperations;

public class MatrixOps {
	public static void LUFactorize(float[][] a, float[][] L, float[][] U) {
		int k, i, j, n;
		n = a.length;
		
		for(k = 0; k < n; k++)
		{
			L[k][k] = 1;

			for(i = k + 1; i < n; i++)
			{
				L[i][k]=a[i][k]/a[k][k] ;

				for(j = k + 1; j < n; j++)
					a[i][j] = a[i][j]-L[i][k]*a[k][j] ;
			}
			for(j = k; j < n; j++)
				U[k][j] = a[k][j];
		}
	}
	
	public static void InvertLMatrix(float l[][], int n) {
		float sum;
		int i,j,k;

		int cnt1 = 0, cnt2 = 0;
		
		for (j = 0; j < n; j++) {
			l[j][j] = 1.0f/l[j][j];
			
			for (i = j + 1; i < n; i++) {
				sum = 0.0f;
				for (k = j; k < i; k++)
				{
					sum -= l[i][k]*l[k][j];
					cnt1++;
				}
				
				l[i][j] = sum/l[i][i];
				cnt2++;
			}
		}
		
		System.out.print("Muls: " + cnt1 + ", Divs: " + cnt2 + "\n");
	}
	
	public static void InvertUMatrix(float u[][], int n) {
		float sum;
		int i,j,k;
		
		for (j = n - 1; j > -1; j--) {
			u[j][j] = 1.0f/u[j][j];
			
			for (i = j - 1; i > -1; i--) {
				sum = 0.0f;
				
				for (k = j; k > i; k--)
					sum -= u[i][k]*u[k][j];
				
				u[i][j] = sum/u[i][i];
			}
		}
	}
	
	public static float[][] MatrixMultiply(float a[][], float b[][]) { 
		int aRows = a.length;
		int aColumns = a[0].length;
		int bColumns = b[0].length;
		   
		float[][] resultant = new float[aRows][bColumns];
		   
		for(int i = 0; i < aRows; i++)
			for(int j = 0; j < bColumns; j++)
				for(int k = 0; k < aColumns; k++)
					resultant[i][j] += a[i][k] * b[k][j];
		   
		return resultant;
	}
}