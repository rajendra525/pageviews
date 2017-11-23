This is a spark application which can parallel process the data.

Dependencies - All dependencies all included in build.sbt and packaged into a executable jar

Please Note :  The cluster should have updated spark version installed preferbly 2.X
Job Execution:

spark-submit --master yarn-client \
--class com.mycompany.pageviews.Transformation \
--driver-memory 5g \
--executor-memory 2g \
--num-executors 2 \
--executor-cores 1 \
<Jar file location> \
<input file/folder location> \
<output file/folder location> 



Example:

spark-submit --master yarn-client \
--class com.mycompany.pageviews.Transformation \
--driver-memory 5g \
--executor-memory 2g \
--num-executors 2 \
--executor-cores 1 \
sparkJars/pageviews_linklisted_2.10-1.0.jar \
/tmp/test/pageViews.csv \
/tmp/ouput/pageViewsLinkListed/ 

 