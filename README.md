<!-- #######  YAY, I AM THE SOURCE EDITOR! #########-->
<p># Order Parser</p>
<p><strong>Test task</strong></p>
<p>Console application that parses input .csv and .json files. Multi-threading parsing mechanism was implemented based on Spring-Batch job. <br />Number of threads is 10 and can be changed in application.properties. <br />Number of step chunks is 10 too.</p>
<p><br />Implementation details:</p>
<p>Basic logic is implemented with following components</p>
<ul>
<li><em>FlatFileItemReader</em> was used as default file reader. It is used by spring batch job as ItemReader</li>
<li><em>LineNumberMapper</em> - custom line mapper that maps each line to the line number to create instance of Line class.</li>
<li><em>LineProcessor</em> - class implementing ItemProcessor to parse and transform the lines to required format.</li>
<li><em>ConsoleWriter</em> - stands for ItemWriter. Component that writes to console.</li>
<li><em>Extensions</em> - extendable enum containg all supported file extensions. Extension of input file is checked against Extension enum when parsing.</li>
<li><em>OrderProcessorConfiguration - </em>Spring application context where Spring Batch job is configured.&nbsp;</li>
</ul>
<p>Automatic tests run the two jobs for parsing sample.csv and sample.json files consequently. Tests then check that correpsonding jobs complete successfully and compares the output with files expected_json.txt and expected_csv.txt.&nbsp;&nbsp;</p>
