#include <set>
#include <vector>
#include <fstream>
#include "Database.h"
#include "Item.h"
#include <ctime>
#include <algorithm>
#include <iostream>

class Element {
public:
	int oldid;
	int id;

public:
	Element(int iold, int inew = 0): oldid(iold), id(inew) {

	}
	bool operator<(const Element &e) const {
		return oldid < e.oldid;
	}
};

class Apriori {
private:
	Item* trie;
	int minSupport; // Minimum support
	std::ofstream setsout;
	Database* data;
	int* remap;
	std::set<Element>* relist;
	bool verbose; // Verbose mode
	int countType;

public:
	Apriori();
	~Apriori();
	void setData(char* fn);
	bool setOutputSets(char* fn);
	void setMinSupport(int ms) {
		this->minSupport = ms;
	}
	int generateItemsets();
	void setVerbose() {
		this->verbose = true;
	}
	void setCountType(int t) {
		this->countType = t;
	}

protected:
	int generateCandidates(int level);
	int generateCandidates(int level, std::set<Item>* items, int depth, int* current);
	bool checkSubsets(int sl, int* iset, std::set<Item>* items, int spos, int depth);
	int countCandidates(int level);
	int processTransaction(int level, Transaction* t, std::set<Item>* items, int spos, int depth);
	int processTransaction2(int level, Transaction* t, std::set<Item>* items, int spos, int depth);
	void reOrder();
	int pruneNodes(int level);
	int pruneNodes(int level, std::set<Item>* items, int depth);
	int pruneCandidates(int level);
	int pruneCandidates(int level, std::set<Item>* items, int depth, int* itemset);
	void printSet(const Item& item, int* itemset, int length);
};
