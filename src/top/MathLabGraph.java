package top;

import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;
import matlabcontrol.MatlabProxy;
import matlabcontrol.MatlabProxyFactory;
import matlabcontrol.MatlabProxyFactoryOptions;
import matlabcontrol.extensions.MatlabTypeConverter;
import environment.CarState;

public class MathLabGraph {

	private static MatlabProxy proxy = null;
	private static int figurenum = 1;
	private static int datanum = 0;

	public static void connect() {
		// Create a proxy, which we will use to control MATLAB
		MatlabProxyFactoryOptions opts = new MatlabProxyFactoryOptions.Builder()
				.setHidden(true).setUsePreviouslyControlledSession(true)
				.build();
		MatlabProxyFactory factory = new MatlabProxyFactory(opts);
		
		new MatlabTypeConverter(proxy);

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
	
	public static int drawData(double[] data) {
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
		sb.append("];");
		
		try {
			proxy.eval(sb.toString());
		} catch (MatlabInvocationException e) {
			System.err.println(e.getMessage());
		}
		
		return datanum++;
	}
	
	public static void drawUs(double[][] Us) {
		drawUs(Us, -1);
	}
	
	public static void drawUs(double[][] Us, int n) {
		int length = n == -1 ? Us.length : n;
		int num = 0;
		for (int i = 0; i < CarState.maxState(); i++) {	
			double[] values = new double[length];
			for (int j = 0; j < length; j++) {
				values[j] = Us[j][i];
			}
			
			if (i % CarState.velocityResolution == 0) {
				num = drawData(values);
			}
			else {
				drawData(values);
			}
			
			if ((i + 1) % CarState.velocityResolution == 0) {
				StringBuilder sb = new StringBuilder();
				sb.append("plot(");
				for (int k = 0; k < CarState.velocityResolution; k++) {
					sb.append("1:1:");
					sb.append(length);
					sb.append(", data");
					sb.append(num + k);
					if (k != CarState.velocityResolution - 1) {
						sb.append(", ");
					}
				}
				sb.append(");");
				try {
					proxy.eval(sb.toString());
				} catch (MatlabInvocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				nextFigure();
			}
		}
	}

}
