h2. Quick Start

To compile and run the program, simply execute:

	sh run.sh

h2. How it works

The input is stored in a plain text file (rather than a database) for ease of running. This file gets read line-by-line and the time taken for each task is calculated by parsing the dates and performing basic arithmetic. More detailed steps are explained in the code comments.
	
h2. Test Cases

Tests have been run on the tests/*_input.txt and the output is stored in the corresponding tests/*_output.txt files. You can rerun them by invoking `sh run_tests.sh`

Note that lines that don't contain data won't get parsed, whether it is because they are invalid syntax, impossible data or heading rows.