#include "Item.h"

using namespace std;

/**
 * Builds children.
 * @return The children.
 */
set<Item>* Item::makeChildren() const {
	if(children) {
		return children;
	}
	return children = new set<Item>;
}

/**
 * Deletes the last computed children.
 * @return The number of children deleted.
 */
int Item::deleteChildren() const {
	int deleted = 0;

	if(children) {
		for(set<Item>::iterator it=children->begin(); it!=children->end(); it++) {
			deleted += it->deleteChildren();
		}
		delete children;
		children = 0;
		deleted++;
	}

	return deleted;
}
