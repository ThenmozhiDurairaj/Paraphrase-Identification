# Code for paraphrase identification



1. To execute in command prompt with a jar file  

	

	a. Download [stanford-corenlp-2012-07-06-models.jar](https://github.com/evandrix/stanford-corenlp/raw/master/stanford-corenlp-2012-07-06-models.jar) in *\lib* folder



	b. Run the command 
	
		

		java –jar ParId.jar data/in.txt result/out.txt
	


2. To use the source

	
	
	a. Software Required: WordNet-2.1 package
			     
			     

		     	      edu.mit.jwi_2.1.4.jar


 
			      edu.sussex.nlp.jws.beta.11.jar
	

    
			      jaws-bin.jar
			    
	

			      stanford-corenlp-2012-07-09.jar


      
			      stanford-corenlp-2012-07-06-models.jar
	

		    
	      xom.jar

	

	b. Input: **/data/in.txt**

	

	c. Output: **/result/out.txt** (4 clause based features)

	

	d. Run the source file **ParId.java**



3. Evaluate 8 machine translation metrics
	
	

	[BLEU](https://github.com/stanfordnlp/phrasal/) (4 features (5-8) bleu-1 to bleu-4) 
		
	

	[NIST](https://github.com/stanfordnlp/phrasal/) (5 features (9-13) nist-1 to nist-5) 
	
	

	[TER](http://www.cs.umd.edu/~snover/tercom/) (1 feature (14)) 
	


	[BADGER](http://babblequest.org/badger/index.html) (1 feature (15)) 
	


	[TERp (TER-Plus)](http://www.cs.umd.edu/~snover/tercom/)  (1 feature (16)) 
	
	

	[METEOR](http://www.cs.cmu.edu/~alavie/METEOR/) (1 feature (17)) 
	
	

	SEPIA (1 feature (18)) package is obtained from the author
	
	

	MAXSIM (1 feature (19)) package is obtained from the author



4. Construct feature vectors for the training data in the form of 

	
	
	1 1:0.6666667 2:1.0 3:1.0 4:0.7647059 5:0.7895 6:0.6667 7:0.5294 8:0.3750 9:3.1662 10:0.2198 11:0.0000 12:0.0000 13:0.0000 14:0.5 15:0.500 16:0.5909787104331845 17:0.5586206912994385 18:0.49838517 19:0.567995548



5.	Train SVM with a set of feature vectors of training data with optimized c and g parameter values and build a model (feature vectors for MSR training data are available in **data/feature_set_train.txt**, model file is in **data/model.txt**)




6.	Construct feature vectors for the test data using the same procedure (feature vectors for MSR test data are available in **data/feature_set_test.txt**)


7.	Predict whether paraphrase or not 
