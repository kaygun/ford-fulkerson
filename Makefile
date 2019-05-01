all: $(*.dot) $(*.mclj)
	mlisp clojure ford-fulkerson "Ford-Fulkerson Algorithm for Maximal Flow"
	for x in 1 2 3 4 5 6 7 8 9; do \
	   neato -Tpng -ofigures/graph$$x.png figures/graph$$x.dot; \
	done
	for x in 9 a b c; do \
	   dot -Tpng -ofigures/graph$$x.png figures/graph$$x.dot; \
	done
