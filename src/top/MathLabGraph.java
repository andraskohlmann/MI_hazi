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
		StringBuilder sb = new StringBuilder();
		sb.append("data");
		sb.append(datanum);
		sb.append(" = [");
		for (int i = 0; i < data.length; i++) {
			sb.append(data[i]);
			if (i < data.length - 1) {
				sb.append(", ");
			}
		}
		sb.append("];\nplot(data");
		sb.append(datanum);
		sb.append(");");
		
		
//		double[][] data2D = new double[data.length][1];
//		for (int i = 0; i < data.length; i++) {
//			data2D[i][0] = data[i];
//		}	
		
		try {
			// Send the array to MATLAB
//			processor.setNumericArray("data" + datanum + "x", new MatlabNumericArray(data2D, null));
			
			//proxy.eval("plot(data" + datanum + "x)");
			proxy.eval(sb.toString());
		} catch (MatlabInvocationException e) {
			System.err.println(e.getMessage());
		}
		
		datanum++;
	}

}
