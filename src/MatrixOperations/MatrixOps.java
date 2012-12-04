package MatrixOperations;

import java.util.Random;

public class MatrixOps {
	private static int cnt1 = 0, cnt2 = 0, cnt3 = 0;
	
	/* *
	 * 
	 * This computed on a STM32F4 need this many clock cycles:
	 * 2.78 * n^2.975 where n is the dimension of the matrix.
	 * Notice exponential growth!!!
	 * 
	 * */
	public static float[][] InvertMatrix(float[][] M) {
		if (M.length != M[0].length)
			throw new IllegalArgumentException("Matrix must be square!");
		
		cnt1 = 0;
		cnt2 = 0;
		cnt3 = 0;
		
		float[][] L = new float[M.length][M.length];
		float[][] U = new float[M.length][M.length];
		float[][] R = new float[M.length][M.length];
		
		LUFactorize(M, L, U);
		
		//ChopInvertL(L, 0, M.length-1);
		//ChopInvertU(U, 0, M.length-1);
		
		InvertLMatrix(L, M.length);
		InvertUMatrix(U, M.length);
		R = MultiplyULMatrix(U, L);
		System.out.println("Muls:" + cnt1 + ", Sums: " + cnt2 + ", Divs: " + cnt3);
		return R;
	}
	
	
	public static void LUFactorize(float[][] a, float[][] L, float[][] U) {
		int k, i, j, n;
		n = a.length;
		
		for(k = 0; k < n; k++)
		{
			L[k][k] = 1;

			for(i = k + 1; i < n; i++)
			{
				L[i][k]=a[i][k]/a[k][k];

				for(j = k + 1; j < n; j++) 
					a[i][j] = a[i][j]-L[i][k]*a[k][j];

			}
			for(j = k; j < n; j++)
				U[k][j] = a[k][j];
		}
	}
	
	public static void InvertLMatrix(float l[][], int n) {
		float sum;
		int i,j,k;
		
		for (j = 0; j < n; j++) {
			for (i = j + 1; i < n; i++) {
				sum = 0.0f;
				for (k = j; k < i; k++)
					sum -= l[i][k]*l[k][j];
				
				l[i][j] = sum;
			}
		}
	}
	
	public static void InvertUMatrix(float u[][], int n) {
		float sum;
		int i,j,k;
		
		for (j = n - 1; j > -1; j--)
			u[j][j] = 1.0f/u[j][j];
		
		for (j = n - 1; j > -1; j--) {
			for (i = j - 1; i > -1; i--) {
				sum = 0.0f;
				
				for (k = j; k > i; k--)
					sum -= u[i][k]*u[k][j];
				
				u[i][j] = sum*u[i][i];

			}
		}
	}
	
	private static int ops;
	
	public static float[][] MatrixMultiply(float a[][], float b[][]) { 
		int aRows = a.length;
		int aColumns = a[0].length;
		int bColumns = b[0].length;
		
		ops = 0;
		
		float[][] resultant = new float[aRows][bColumns];
		   
		for(int i = 0; i < aRows; i++)
			for(int j = 0; j < bColumns; j++)
				for(int k = 0; k < aColumns; k++) {
					resultant[i][j] += a[i][k] * b[k][j];
					ops++;
				}
		   
		System.out.println("Ops: " + ops);
		
		return resultant;
	}
	
	/* This function multiplies a Upper triangular matrix with a Lower triangular matrix */
	public static float[][] MultiplyULMatrix(float a[][], float b[][]) { 
		int n = a.length;
		   
		float[][] resultant = new float[n][n];
		   
		
		/* *
		 * 
		 * Illustration why we don't have to multiply and add every element:
		 * x is some number in the matrix, but the x:es are not necessarily the same.
		 * 
		 * | x x x x x |   | x 0 0 0 0 |
		 * | 0 x x x x |   | x x 0 0 0 |
		 * | 0 0 x x x | * | x x x 0 0 | = ... 
		 * | 0 0 0 x x |   | x x x x 0 |
		 * | 0 0 0 0 x |   | x x x x x |
		 * 
		 * Many zeroes that don't have to be taken into account when multiplying!
		 * 
		 * */
		
		
		
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				resultant[i][j] = 0.0f;
				int k;
				
				if (j < i)
					k = i;
				else
					k = j;
				
				for(; k < n; k++)
					resultant[i][j] += a[i][k] * b[k][j];
			}
		}
		   
		return resultant;
	}
	
	public static void printMat(float[][] mat) {

		for (float[] row: mat) {
			for (float col: row) {
				System.out.printf("%.4f", col);
				System.out.print("\t");
			}
			System.out.println();
		}		
	}
	
	public static float[][] RandomMatrix(int dim) {
		float[][] ret = new float[dim][dim];
		Random r = new Random();
		
		for(int i = 0; i < dim; i++)
			for(int j = 0; j < dim; j++)
				ret[i][j] = r.nextFloat()*5.0f;
		
		return ret;
	}
	
	public static void ChopInvertU(float[][] M, int start, int end) {
		int offset = (end-start)/2;
		cnt2++;
		
		/* Recursive killer */
		if (end - start == 0) {
			M[end][end] = 1/M[end][end];
			return;
		}
		
		
		ChopInvertU(M, start, start+offset);
		cnt2++;
		
		ChopInvertU(M, start+offset+1, end);
		cnt2++;
		UMatMul(M, start, offset, end);
	}
	
	public static void ChopInvertL(float[][] M, int start, int end) {
		int offset = (end-start)/2;
		cnt2++;
		/* Recursive killer */
		if (end - start == 0) {
			//M[end][end] = 1/M[end][end];;
			return;
		}
		
		
		ChopInvertL(M, start+offset+1, end);
		cnt2++;
		ChopInvertL(M, start, start+offset);
		cnt2++;
		
		LMatMul(M, start, offset, end);
	}
	
	public static void LMatMul(float[][] M, int start, int offset, int end) {
		int aRows = end-start-offset;
		int aColumns = aRows;
		int bColumns = offset+1;
		float[][] resultant = new float[aRows][bColumns];
		
		for(int i = 0; i < aRows; i++)
			for(int j = 0; j < bColumns; j++) {
				aColumns = i+1;
				for(int k = 0; k < aColumns; k++) {
					cnt2++;
					cnt1++;
					resultant[i][j] += M[start+bColumns+i][start+bColumns+k] * M[start+bColumns+k][start+j];
				}
			}
		
		for(int i = 0; i < aRows; i++)
			for(int j = 0; j < bColumns; j++) {
				M[start+bColumns+i][start+j] = 0.0f;
				int k = j;
				for(; k < bColumns; k++) {
					cnt2++;
					cnt1++;
					M[start+bColumns+i][start+j] -= resultant[i][k] * M[start+k][start+j];
				}
			}
	}
	
	public static void UMatMul(float[][] M, int start, int offset, int end) {
		int aRows = offset+1;
		int aColumns = aRows;
		int bColumns = end-start-offset;
		float[][] resultant = new float[aRows][bColumns];
		
		for(int i = 0; i < aRows; i++)
			for(int j = 0; j < bColumns; j++) {
				resultant[i][j] = 0.0f;
				for(int k = i; k < aColumns; k++) {
					resultant[i][j] += M[start+i][start+k] * M[start+k][start+aRows+j];
					cnt2++;
					cnt1++;
				}
			}

		for(int i = 0; i < aRows; i++)
			for(int j = 0; j < bColumns; j++) {
				aColumns = j+1;
				M[i+start][start+aRows+j] = 0.0f;
				for(int k = 0; k < aColumns; k++) {
					M[i+start][start+aRows+j] -= resultant[i][k] * M[start+aRows+k][start+aRows+j];
					cnt2++;
					cnt1++;
				}
			}		
	}
}
