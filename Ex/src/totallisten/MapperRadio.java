package totallisten;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MapperRadio extends
		Mapper<LongWritable, Text, LongWritable, LongWritable> {

	private LongWritable trackIDKey = new LongWritable();
	private LongWritable totalCount = new LongWritable();

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
			// Retrieve radioalue,scrobbletring in String
			String radiotring = splitedvalues[3].trim();
			String scrobbletring = splitedvalues[2].trim();

			try {
				// Convert trackIDstring and radioalue,scrobbletring in Long
				// value
				Long trackIDlong = Long.parseLong(trackIDstring);
				Long radiolong = Long.parseLong(radiotring);
				Long scrobblelong = Long.parseLong(scrobbletring);
				Long total = radiolong + scrobblelong;

				// Convert trackIDlong and skiplong in LongWritable
				trackIDKey.set(trackIDlong);
				totalCount.set(total);

				context.write(trackIDKey, totalCount);
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
