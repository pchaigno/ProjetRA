package ra.data;

public class Item implements Comparable<Item> {
	public int id;
	
	/**
	 * Constructor
	 * @param id The id for this itemset.
	 */
	public Item(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Item)) {
			return false;
		}
		Item other = (Item) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(Item item) {
		if(this.id == item.id) {
			return 0;
		}
		if(this.id > item.id) {
			return 1;
		}
		return -1;
	}

	@Override
	public String toString() {
		return String.valueOf(this.id);
	}
}
