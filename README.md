# Code for paraphrase identification

1. To execute in command prompt with a jar file  

	a. Download [stanford-corenlp-2012-07-06-models.jar](https://github.com/evandrix/stanford-corenlp/raw/master/stanford-corenlp-2012-07-06-models.jar) in **/lib** folder

	b. Run the command to obtain 4 clause based features

		java –jar ParId.jar data/in_sample.txt result/out_sample.txt
		
		(**Note 1:** out_sample.txt is in the form of **1 1:0.6666667 2:1.0 3:1.0 4:0.7647059**, where the first field is class label and fields 2 to 5 are 1 to 4 clause based features namely concept score, relation score, proposition score and word score)

2. Evaluate 8 machine translation (MT) metrics to obtain 15 MT based features
	
	[BLEU](https://github.com/stanfordnlp/phrasal/) (4 features (5-8) bleu-1 to bleu-4) 

	[NIST](https://github.com/stanfordnlp/phrasal/) (5 features (9-13) nist-1 to nist-5) 

	[TER](http://www.cs.umd.edu/~snover/tercom/) (1 feature (14)) 

	[BADGER](http://babblequest.org/badger/index.html) (1 feature (15)) 

	[TERp (TER-Plus)](http://www.cs.umd.edu/~snover/tercom/)  (1 feature (16)) 
	
	[METEOR](http://www.cs.cmu.edu/~alavie/METEOR/) (1 feature (17)) 
	
	SEPIA (1 feature (18)) package is obtained from the author

	MAXSIM (1 feature (19)) package is obtained from the author

3. Construct feature vectors for the training data in the form of 

	1 1:0.6666667 2:1.0 3:1.0 4:0.7647059 5:0.7895 6:0.6667 7:0.5294 8:0.3750 9:3.1662 10:0.2198 11:0.0000 12:0.0000 13:0.0000 14:0.5 15:0.500 16:0.5909787104331845 17:0.5586206912994385 18:0.49838517 19:0.567995548
	
4. Construct feature vectors for the test data using the same procedure

	(**Note 2:** To evaluate paraphrase identification for MSR corpus, download data set from http://research.microsoft.com/en-us/downloads/607d14d9-20cd-47e3-85bc-a2f65cd28042/)

	(**Note 3:** For the sake of convenience, we have prepared feature vectors for MSR training and test data that are available in **data/feature_set_train.txt** and **data/feature_set_test.txt** respectively)

5. Download LIBSVM tool from the link https://www.csie.ntu.edu.tw/~cjlin/libsvm/

6. Train SVM with a set of feature vectors of training data with optimized c and g parameter values and build a model using the command 

		java svm_train -c 32768.0 -g 0.0078125 data/feature_set_train.txt data/model.txt

	(**Note 4:** For the sake of convenience, we have trained MSR training data and the model file is available in  **data/model.txt**)

7. Predict test data whether paraphrases or not using the command

		java svm_predict data/feature_set_test.txt data/model.txt out.txt
