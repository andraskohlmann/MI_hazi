package top;

import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;
import matlabcontrol.MatlabProxy;
import matlabcontrol.MatlabProxyFactory;
import matlabcontrol.MatlabProxyFactoryOptions;
import matlabcontrol.extensions.MatlabNumericArray;
import matlabcontrol.extensions.MatlabTypeConverter;

public class MathLabGraph {

	private static MatlabProxy proxy = null;
	private static MatlabTypeConverter processor;
	
	private static int figurenum = 1;
	private static int datanum = 0;

	public static void connect() {
		// Create a proxy, which we will use to control MATLAB
		MatlabProxyFactoryOptions opts = new MatlabProxyFactoryOptions.Builder()
				.setHidden(true).setUsePreviouslyControlledSession(true)
				.build();
		MatlabProxyFactory factory = new MatlabProxyFactory(opts);
		
		processor = new MatlabTypeConverter(proxy);

		try {
			proxy = factory.getProxy();
		} catch (MatlabConnectionException e) {
			System.err.println(e.getMessage());
		}
		
		try {
			proxy.eval("figure(1)");
		} catch (MatlabInvocationException e) {
			System.err.println(e.getMessage());
		}
	}

	public static void disconnect() {
		// Disconnect the proxy from MATLAB
		if (proxy != null) {
			proxy.disconnect();
		}
	}
	
	public static void nextFigure() {
		figurenum++;
		try {
			proxy.eval("figure(" + figurenum + ")");
		} catch (MatlabInvocationException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public static void drawData(double[] data) {
		double[][] data2D = new double[1][];
		data2D[0] = data;
		
		
		try {
			// Send the array to MATLAB
			processor.setNumericArray("data" + datanum, new MatlabNumericArray(data2D, null));
			
			proxy.eval("plot(data" + datanum + ")");
		} catch (MatlabInvocationException e) {
			System.err.println(e.getMessage());
		}
		
		datanum++;
	}

}
