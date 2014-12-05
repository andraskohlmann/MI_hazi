package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;
import matlabcontrol.MatlabProxy;
import matlabcontrol.MatlabProxyFactory;
import matlabcontrol.MatlabProxyFactoryOptions;
import matlabcontrol.extensions.MatlabNumericArray;
import matlabcontrol.extensions.MatlabTypeConverter;

public class Test {

	/**
	 * @param args
	 * @throws MatlabInvocationException
	 * @throws MatlabConnectionException
	 * @throws IOException
	 */
	public static void main(String[] args) throws MatlabConnectionException,
			IOException {
		// Create a proxy, which we will use to control MATLAB
		MatlabProxyFactoryOptions opts = new MatlabProxyFactoryOptions.Builder()
				.setHidden(true).setUsePreviouslyControlledSession(true)
				.build();

		MatlabProxyFactory factory = new MatlabProxyFactory(opts);
		MatlabProxy proxy = factory.getProxy();

		// Create and print a 2D double array
		double[][] array = new double[][] { { 1, 2, 3 }, { 1, 1, -1 },
				{ 1, 2, 1 } };
		double[][] array2 = new double[][] { { 2 }, { 4 }, { 4 } };
		System.out.println("Original: ");
		for (int i = 0; i < array.length; i++) {
			StringBuilder sdf = new StringBuilder();
			for (int j = 0; j < array[0].length; j++) {
				sdf.append(array[i][j]);
				sdf.append(" * ");
				sdf.append((char) ('a' + j));
				if (j == array[0].length - 1) {
					sdf.append(" = ");
				} else {
					sdf.append(" + ");
				}
			}
			sdf.append(array2[i][0]);
			System.out.println(sdf.toString());
		}

		// Send the array to MATLAB, transpose it, then retrieve it and convert
		// it to a 2D double array
		MatlabTypeConverter processor = new MatlabTypeConverter(proxy);
		try {
			processor.setNumericArray("A", new MatlabNumericArray(array, null));
			processor
					.setNumericArray("b", new MatlabNumericArray(array2, null));
		} catch (MatlabInvocationException e) {
			e.printStackTrace();
		}

		FileReader fr = new FileReader("res/test.m");
		BufferedReader br = new BufferedReader(fr);

		StringBuffer sb = new StringBuffer();
		String s;
		while ((s = br.readLine()) != null) {
			sb.append(s);
		}

		br.close();

		try {
			proxy.eval(sb.toString());
			double[][] processedArray = processor.getNumericArray("v")
					.getRealArray2D();

			// Print the returned array, now transposed
			System.out.println("\nSolved:");
			for (int i = 0; i < processedArray.length; i++) {
				System.out.println((char) ('a' + i) + " = "
						+ processedArray[i][0]);
			}
		} catch (MatlabInvocationException e) {
			System.out.println(e.getMessage());
		}

		// Disconnect the proxy from MATLAB
		proxy.disconnect();
	}

}
