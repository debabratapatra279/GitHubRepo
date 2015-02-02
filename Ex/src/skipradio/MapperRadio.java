package skipradio;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MapperRadio extends
		Mapper<LongWritable, Text, LongWritable, LongWritable> {

	private LongWritable trackIDKey = new LongWritable();
	private LongWritable numberOfSkipValue = new LongWritable();

	@Override
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {

		// Convert Text To String
		String stringvalue = value.toString();
		String[] splitedvalues = stringvalue.split("\\s+");

		// Check length of Array
		if (splitedvalues.length == 5) {

			// Retrieve TrackID in String
			String trackIDstring = splitedvalues[1].trim();
			// Retrieve skipvalue in String
			String skipstring = splitedvalues[4].trim();

			try {
				// Convert trackIDstring and skipstring in Long value
				Long trackIDlong = Long.parseLong(trackIDstring);
				Long skiplong = Long.parseLong(skipstring);

				// Convert trackIDlong and skiplong in LongWritable
				trackIDKey.set(trackIDlong);
				numberOfSkipValue.set(skiplong);

				context.write(trackIDKey, numberOfSkipValue);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void run(Context context) throws IOException, InterruptedException {
		setup(context);
		while (context.nextKeyValue()) {
			map(context.getCurrentKey(), context.getCurrentValue(), context);
		}
		cleanup(context);
	}

}
