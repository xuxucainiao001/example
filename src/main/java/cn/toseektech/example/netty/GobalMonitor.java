package cn.toseektech.example.netty;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;

public class GobalMonitor {

	private GobalMonitor() {
	}

	public static final void start() throws FileNotFoundException {

		MetricRegistry metricRegistry = new MetricRegistry();
		metricRegistry.register("所有Netty连接", new Gauge<Integer>() {

			@Override
			public Integer getValue() {
				return GlobalContext.totalLinks.get();
			}
		});

		metricRegistry.register("合法Netty连接", new Gauge<Integer>() {

			@Override
			public Integer getValue() {
				return GlobalContext.channelMaps.size();
			}
		});

		ConsoleReporter consoleReporter = ConsoleReporter.forRegistry(metricRegistry)
				.outputTo(new PrintStream("netty-metric.log")).build();
		consoleReporter.start(5, TimeUnit.SECONDS);
	}

}
