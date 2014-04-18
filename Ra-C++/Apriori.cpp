#include "Apriori.h"

using namespace std;

/**
 * Constructor
 */
Apriori::Apriori() {
	this->data = 0;
	this->minSupport = 0;
	this->remap = 0;
	this->relist = 0;
	this->trie = new Item(0);
	this->verbose = false;
	this->countType = 1;
}

/**
 * Destructor
 */
Apriori::~Apriori() {
	if(this->data) {
		delete this->data;
	}
	if(this->trie) {
		this->trie->deleteChildren();
		delete this->trie;
	}
	if(this->remap) {
		delete this->remap;
	}
	if(this->relist) {
		delete this->relist;
	}
}

/**
 * Builds the data object from the input file.
 * @param inputFile The input file.
 */
void Apriori::setData(char* inputFile) {
	this->data = new Database(inputFile);
}

/**
 * Opens an ouput file.
 * @param fn The name of the ouput file.
 * @return True if all went well.
 */
bool Apriori::setOutputSets(char* fn) {
	this->setsout.open(fn);
	if(!this->setsout.is_open()) {
		cerr << "error: could not open " << fn << endl;
		return false;
	}
	return true;
}

/**
 * Generates the itemsets.
 * @return The number of itemsets generated.
 */
int Apriori::generateItemsets() {
	int total = 0; // The number of itemsets generated.
	int numPass = 0;
	bool running = true;

	while(running) {
		clock_t start;
		int generated = 0, tnr = 0, nbAfterPrune;

		numPass++;
		cout << numPass << ") " << flush;

		if(numPass > 2) {
			start = clock();
			generated = generateCandidates(numPass);
			if(this->verbose) {
				cout << "Generated: " << generated << " [" << (clock() - start) / double(CLOCKS_PER_SEC) << "s] " << flush;
			}
		}

		start = clock();
		tnr = countCandidates(numPass);
		if(this->verbose) {
			if(numPass == 1) {
				cout << this->trie->getChildren()->size() << " ";
			}
			cout << "Candidates: " << tnr << " [" << (clock() - start) / double(CLOCKS_PER_SEC) << "s] " << flush;
		}

		if(numPass==1 && this->setsout.is_open()) {
			printSet(*this->trie, 0, 0);
		}

		start = clock();
		nbAfterPrune = pruneCandidates(numPass);
		if(this->verbose) {
			cout << "Itemsets: " << nbAfterPrune << " [" << (clock() - start) / double(CLOCKS_PER_SEC) << "s]\n" << flush;
		}

		if(numPass == 1) {
			reOrder(); // Reorder all items
		}

		total += nbAfterPrune;
		if(nbAfterPrune <= numPass) {
			running = false;
		}
	}

	cout << endl;

	return total;
}

/**
 * Generates candidates.
 * @param level The level of itemsets.
 * @return The number of candidates generated.
 */
int Apriori::generateCandidates(int level) {
	int* tmp = new int[level];
	int generated = generateCandidates(level, this->trie->getChildren(), 1, tmp);
	delete[] tmp;

	return generated;
}

/**
 * Generates candidates.
 * @param level The level of itemsets.
 * @param items
 * @param depth
 * @param current
 * @return 
 */
int Apriori::generateCandidates(int level, set<Item>* items, int depth, int* current) {
	if(items == 0) {
		return 0;
	}

	int generated = 0;
	set<Item>::reverse_iterator runner;

	if(depth == level - 1) {
		for(runner=items->rbegin(); runner!=items->rend(); runner++) {
			current[depth - 1] = runner->getId();
			set<Item>* children = runner->makeChildren();

			for(set<Item>::reverse_iterator it=items->rbegin(); it!=runner; it++) {
				current[depth] = it->getId();
				if(level <= 2 || checkSubsets(level, current, this->trie->getChildren(), 0, 1)) {
					children->insert(Item(it->getId()));
					generated++;
				}
			}
		}
	} else {
		for(runner=items->rbegin(); runner!=items->rend(); runner++) {
			current[depth - 1] = runner->getId();
			generated += generateCandidates(level, runner->getChildren(), depth + 1, current);
		}
	}
	return generated;
}

/**
 * 
 * @param sl
 * @param iset
 * @param items
 * @param spos
 * @param depth
 * @return 
 */
bool Apriori::checkSubsets(int sl, int* iset, set<Item>* items, int spos, int depth) {
	if(items == 0) {
		return 0;
	}

	bool ok = true;
	set<Item>::iterator runner;
	int loper = spos;
	spos = depth + 1;

	while(ok && --spos>=loper) {
		runner = items->find(Item(iset[spos]));
		if(runner != items->end()) {
			if(depth < sl - 1) {
				ok = checkSubsets(sl, iset, runner->getChildren(), spos + 1, depth + 1);
			}
		}
		else ok = false;
	}

	return ok;
}

/**
 * 
 * @param level The level of itemsets.
 * @return 
 */
int Apriori::pruneNodes(int level) {
	return pruneNodes(level, this->trie->getChildren(), 1);
}

/**
 * 
 * @param level The level of itemsets.
 * @param items
 * @param depth
 * @return 
 */
int Apriori::pruneNodes(int level, set<Item>* items, int depth) {
	if(items == 0) {
		return 0;
	}

	int nodes = 0;

	if(depth == level) {
		nodes = items->size();
	} else {
		for(set<Item>::iterator runner=items->begin(); runner!=items->end();) {
			int now = pruneNodes(level, runner->getChildren(), depth + 1);
			if(now) {
				nodes += now;
				nodes++;
				runner++;
			} else {
				runner->deleteChildren();
				set<Item>::iterator tmp = runner++;
				items->erase(tmp);
			}
		}
	}

	return nodes;
}

/**
 * 
 * @param level The level of itemsets.
 * @return 
 */
int Apriori::countCandidates(int level) {
	int trans = 0;

	// count all single items
	if(level == 1) {
		while(Transaction* t = this->data->getNext()) {
			this->trie->incrementSupport();

			int* iset = t->t;
			int sl = t->length;
			set<Item>* items = this->trie->makeChildren();
			for(int i=0; i<sl; i++) {
				Item item(iset[i]);
				set<Item>::iterator runner = items->find(item);
				if(runner == items->end()) {
					runner = (items->insert(item)).first;
				}
				runner->incrementSupport();
			}
			trans++;
			delete t;
		}
	} else {
		while(Transaction* t = this->data->getNext()) {
			if(t->length >= level) {
				// Reorder transaction
				int i;
				vector<int> list;
				for(i=0; i<t->length; i++) {
					set<Element>::iterator it = this->relist->find(Element(t->t[i]));
					if(it != this->relist->end()) {
						list.push_back(it->id);
					}
				}
				int size = list.size();
				sort(list.begin(), list.end());
				delete t;
				t = new Transaction(size);
				for(i=0; i<size; i++) {
					t->t[i] = list[i];
				}

				if(this->countType == 1 || level <= 2) {
					if(processTransaction(level, t, this->trie->getChildren(), 0, 1)) {
						trans++;
					}
				} else {
					if(processTransaction2(level, t, this->trie->getChildren(), 0, 1)) {
						trans++;
					}
				}

				delete t;
			}
		}
	}

	return trans;
}

/**
 * Tests all candidates contained in transaction.
 * @param level The level of itemsets.
 * @param t
 * @param items
 * @param spos
 * @param depth
 * @return 
 */
int Apriori::processTransaction2(int level, Transaction* t, set<Item>* items, int spos, int depth) {
	if (items == 0) {
		return 0;
	}
	int used = 0, max = t->length - level + depth;

	for(set<Item>::iterator it = items->begin(); spos < max && it != items->end(); it++) {
		while(spos<max && t->t[spos]<it->getId()) {
			spos++;
		}
		if(spos<max && (t->t[spos] == it->getId())) {
			if(depth == level) {
				it->incrementSupport();
				used++;
			} else {
				used += processTransaction2(level, t, it->getChildren(), spos + 1, depth + 1);
			}
		}
	}

	return used;
}

/**
 * Tests k-subsets of transaction that are candidate itemsets.
 * @param level The level of itemsets.
 * @param t
 * @param spos
 * @param depth
 * @return 
 */
int Apriori::processTransaction(int level, Transaction* t, set<Item>* items, int spos, int depth) {
	if(items == 0) {
		return 0;
	}

	int used = 0,* iset = t->t, sl = t->length, loper = spos;
	set<Item>::iterator runner;

	spos = sl - (level - depth);
	while(--spos >= loper) {
		runner = items->find(Item(iset[spos]));
		if(runner != items->end()) {
			if(depth == level) {
				runner->incrementSupport();
				used++;
			} else {
				if(depth == 1 && level == 2) {
					runner->makeChildren();
				}
				used += processTransaction(level, t, runner->getChildren(), spos + 1, depth + 1);
			}
		} else if(depth == 2 && level == 2) {
			set<Item>* singles = this->trie->getChildren();
			if(singles->find(Item(iset[spos])) != singles->end()) {
				runner = items->insert(Item(iset[spos])).first;
				runner->incrementSupport();
				used++;
			}
		}
	}

	return used;
}

/**
 * 
 * @param level The level of itemsets.
 * @return The number of itemsets remaining.
 */
int Apriori::pruneCandidates(int level) {
	int pruned;
	int* tmp = new int[level];
	pruned = pruneCandidates(level, this->trie->getChildren(), 1, tmp);
	delete[] tmp;
	return pruned;
}

/**
 * 
 * @param level The level of itemsets.
 * @param items
 * @param depth
 * @param itemset
 * @return 
 */
int Apriori::pruneCandidates(int level, set<Item>* items, int depth, int* itemset) {
	if(items == 0) {
		return 0;
	}
	int left = 0;

	for(set<Item>::iterator runner = items->begin(); runner != items->end();) {
		itemset[depth-1] = runner->getId();

		if(depth == level) {
			if(runner->getSupport() < this->minSupport) {
				runner->deleteChildren();
				set<Item>::iterator tmp = runner++;
				items->erase(tmp);
			} else {
				if(this->setsout.is_open()) {
					printSet(*runner, itemset, depth);
				}
				left++;
				runner++;
			}
		} else {
			int now = pruneCandidates(level, runner->getChildren(), depth + 1, itemset);
			if(now) {
				left += now;
				runner++;
			} else {
				runner->deleteChildren();
				set<Item>::iterator tmp = runner++;
				items->erase(tmp);
			}
		}
	}

	return left;
}

/**
 * Prints the itemset to the output file.
 * @param item
 * @param itemset The itemset to print.
 * @param length The size of the itemset.
 */
void Apriori::printSet(const Item& item, int* itemset, int length) {
	set<int> outset;

	for(int j=0; j<length; j++) {
		if(this->remap) {
			outset.insert(this->remap[itemset[j]]);
		} else {
			outset.insert(itemset[j]);
		}
	}
	for(set<int>::iterator k=outset.begin(); k!=outset.end(); k++) {
		this->setsout << *k << " ";
	}
	this->setsout << "(" << item.getSupport() << ")" << endl;
}

/**
 * 
 */
void Apriori::reOrder() {
	set<Item>* src = this->trie->getChildren();
	set<Item>::iterator itI;
	multiset<Element>::iterator itE;
	multiset<Element> list;

	for(itI=src->begin(); itI!=src->end(); itI++) {
		list.insert(Element(itI->getSupport(), itI->getId()));
	}

	this->remap = new int[list.size() + 1];
	this->relist = new set<Element>;
	src->clear();
	int i = 1;
	for(itE=list.begin(); itE!=list.end(); itE++) {
		if(itE->oldid >= this->minSupport) {
			this->remap[i] = itE->id;
			this->relist->insert(Element(itE->id, i));
			Item a(i);
			a.incrementSupport(itE->oldid);
			src->insert(a);
			i++;
		}
	}
}

