README.txt

Performance Optimisations:

We used the SAX parser so that we do not have to read the entire file into memory and we create each element as we encounter them.
This serves to improve the performance of our inputs. We also verify that every insert transaction goes through  by counting the result set for each query
also controlling the behaviour of when the actual committing of data occurs. This simulates a sort of batch load to increase the speed and even on a machine
with normal hard drive we could load the data in within 20 seconds.

We also use prepared statements and close them to prevent SQL Injection, as well as take care of a lot of pre-processing like syntax checking so that mysql does not have
do this during inserts.

We also use a Connection Pool to not create new connections every time we need to execute a query and use the same one over and over so as not to generate any overhead
of creating a connection every time. 
