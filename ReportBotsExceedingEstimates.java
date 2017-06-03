import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description:
 * 
 * This program takes in a list of transactions, and reports whether those
 * transactions completed within the time that we expect for a bot of that size.
 * 
 * Example:
 * 
 * 	 	Transaction ID:		1
 * 	 	start time:			11/1/2016:12:00
 *		end time:			11/1/2016:13:01
 * 		site:				google.com
 * 		bot:				MEDIUM
 *
 * Since this is a MEDIUM-sized bot, it should have completed in 1 hour or less. 
 * Looking at its start and end time, we can see that it took 1 hour and 1 minute.
 * This was above our expectation, so we report "Yes" saying the average time was 
 * exceeded.
 */
public class ReportBotsExceedingEstimates {
	
	public static void main(String[] args) {
		File file = new File(args.length > 0 ? args[0] : "transactions.txt");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {

				//
				// Step 1) Parse the line
				//

				Matcher matcher = Pattern.compile("(\\d+)\\s+.\\s+" + // ID
						"(\\d+/\\d+/\\d+:\\d+:\\d\\d)" + // start time
						"\\s+.\\s+" +
						"(\\d+/\\d+/\\d+:\\d+:\\d\\d)" + // end time
						"\\s+(.*)\\s+" + // site
						"(small|medium|large|xtralarge|ultimate)", // bot
						Pattern.CASE_INSENSITIVE).matcher(line);

				if (matcher.find()) {

					//
					// Calculate how much time each task took
					//
					String startTime = matcher.group(2);
					SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY:HH:mm");
					long startDate;
					try {
						startDate = dateFormat.parse(startTime).getTime();
					} catch (ParseException e) {
						System.err.println("Unexpected date format: " + startTime);
						continue;
					}

					String endTime = matcher.group(3);
					long endDate;
					try {
						endDate = dateFormat.parse(endTime).getTime();
					} catch (ParseException e) {
						System.err.println("Unexpected date format: " + endTime);
						continue;
					}

					//
					// Step 2) Check if this bot is performing as expected
					//
					long millisecondsTaken = endDate - startDate;
					BotSize botType = BotSize.valueOf(matcher.group(5)
							.toUpperCase());
					long expectedMillisecondsTaken = getEstimatedMilliseconds()
							.get(botType);
					String outcome;
					if (millisecondsTaken > expectedMillisecondsTaken) {
						outcome = "Yes"; // above average
					} else {
						outcome = "No";
					}
					System.out.println(line + " - " + outcome);
				} else {
					System.err.println("Problem parsing line, skipping: " + line);
					continue;
				}
		    }
			
		} catch (IOException e) {
			System.err.println("Problem reading from transactions.txt: " + e.getMessage());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private static enum BotSize {
		SMALL, MEDIUM, LARGE, XTRALARGE, ULTIMATE;
	}

	private static Map<BotSize, Long> getEstimatedMilliseconds() {
		Map<BotSize, Long> estimatedMilliseconds = new HashMap<BotSize, Long>();
		estimatedMilliseconds.put(BotSize.SMALL, 2 * 60 * 60 * 1000L); // 2 hours
		estimatedMilliseconds.put(BotSize.MEDIUM, 1 * 60 * 60 * 1000L); // 1 hour
		estimatedMilliseconds.put(BotSize.LARGE, 30 * 60 * 1000L); // 30 minutes
		estimatedMilliseconds.put(BotSize.XTRALARGE, 5 * 60 * 1000L); // 5 minutes
		estimatedMilliseconds.put(BotSize.ULTIMATE, 2 * 60 * 1000L); // 2 minutes
		return estimatedMilliseconds;
	}
}
