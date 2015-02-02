package totallisten;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Reducer;

public class ReducerRadio extends
		Reducer<LongWritable, LongWritable, LongWritable, LongWritable> {

	private LongWritable totalCount = new LongWritable();

	@Override
	public void reduce(final LongWritable key,
			final Iterable<LongWritable> values, final Context context)
			throws IOException, InterruptedException {
		try {
			int sum = 0;
			Iterator<LongWritable> iterator = values.iterator();

			while (iterator.hasNext()) {
				sum += iterator.next().get();
			}

			totalCount.set(sum);
			// context.write(key, new IntWritable(sum));
			context.write(key, totalCount);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
