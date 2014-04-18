#include <stdio.h>
#include <vector>
#include <set>

class Transaction {
public:
	int length; // Number of elements
	int *t; // Array of elements

public:
	Transaction(int l): length(l) {
		t = new int[l];
	}
	Transaction(const Transaction &tr);
	~Transaction() {
		delete[] t;
	}
};

class Database {
private:
	FILE *in; // File handler
	int current;

public:
	Database(char *filename);
	~Database();
	Transaction *getNext();

private:
	Transaction* getNextAsFlat();
	Transaction* getNextAs();
	Transaction* getNextBin();
	Transaction* getNextAsQuest();
};
